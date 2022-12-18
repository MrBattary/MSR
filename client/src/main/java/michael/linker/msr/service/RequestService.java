package michael.linker.msr.service;

import michael.linker.msr.http.HttpGate;
import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import michael.linker.msr.properties.PropertiesNotAvailableException;
import michael.linker.msr.properties.PropertiesNotFoundException;
import michael.linker.msr.properties.PropertiesProvider;
import michael.linker.msr.runnable.RequestRunnable;
import michael.linker.msr.runnable.model.RequestRunnableModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class RequestService implements IRequestService {
    private static final String CREATE_BALANCE_JSON_OBJECT = "{\"id\":%d,\"amount\":%d}";
    private final HttpGate httpGate;
    private final PropertiesProvider properties;
    private final ExecutorService executorService;

    /**
     * Default constructor.
     *
     * @throws RequestServiceFailedException if required dependency was not resolved.
     */
    public RequestService() throws RequestServiceFailedException {
        try {
            httpGate = new HttpGate();
            properties = new PropertiesProvider();
            executorService = Executors.newFixedThreadPool(properties.getThreadCount().intValue());
            this.repopulateServerBalance();
        } catch (PropertiesNotAvailableException
                 | PropertiesNotFoundException
                 | IllegalArgumentException e) {
            throw new RequestServiceFailedException(e);
        }
    }

    @Override
    public void prepareAndRun() throws RequestServiceFailedException {
        try {
            List<Future<?>> futureList = new ArrayList<>();
            for (int i = 0; i < properties.getThreadCount(); i++) {
                RequestRunnable requestRunnable = new RequestRunnable(new RequestRunnableModel(
                        properties.getServerEndpoint(),
                        properties.getReadQuota(),
                        properties.getWriteQuota(),
                        properties.getReadIdList(),
                        properties.getWriteIdList(),
                        properties.getBalanceChangeAmount()
                ));
                futureList.add(executorService.submit(requestRunnable));
            }
            // foreach blocks current thread until all child threads are finished.
            for (Future<?> future : futureList) {
                future.get();
            }
            executorService.shutdown();
        } catch (RejectedExecutionException |
                 NullPointerException |
                 InterruptedException |
                 SecurityException |
                 ExecutionException e) {
            throw new RequestServiceFailedException(e);
        }
    }

    private void repopulateServerBalance() throws RequestServiceFailedException {
        List<Long> idListForRepopulation = this.buildIdListForRepopulation();
        this.repopulateServer(idListForRepopulation);
    }

    private List<Long> buildIdListForRepopulation() {
        Set<Long> idList = new HashSet<>();
        if (properties.getReadSync()) {
            idList.addAll(properties.getReadIdList());
        }
        if (properties.getWriteSync()) {
            idList.addAll(properties.getWriteIdList());
        }
        return idList.stream().toList();
    }

    private void repopulateServer(List<Long> idList) throws RequestServiceFailedException {
        try {
            httpGate.delete(properties.getServerEndpoint()).close();
            for (Long id : idList) {
                httpGate.put(properties.getServerEndpoint(),
                        String.format(CREATE_BALANCE_JSON_OBJECT, id, properties.getBalanceSyncStartAmount())).close();
            }
        } catch (HttpGateFailureException | HttpRequestFailedException e) {
            throw new RequestServiceFailedException(e);
        }
    }
}
