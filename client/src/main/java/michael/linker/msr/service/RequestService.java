package michael.linker.msr.service;

import michael.linker.msr.http.HttpGate;
import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import michael.linker.msr.properties.PropertiesNotAvailableException;
import michael.linker.msr.properties.PropertiesNotFoundException;
import michael.linker.msr.properties.PropertiesProvider;
import michael.linker.msr.provider.JsonProvider;
import michael.linker.msr.provider.MsgProvider;
import michael.linker.msr.runnable.RequestRunnable;
import michael.linker.msr.runnable.model.RequestRunnableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class RequestService implements IRequestService {
    private static final Logger log = LogManager.getLogger(RequestService.class);
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
            final RequestRunnableModel runnableModel = new RequestRunnableModel(
                    properties.getServerEndpoint(),
                    properties.getReadQuota(),
                    properties.getWriteQuota(),
                    properties.getReadIdList(),
                    properties.getWriteIdList(),
                    properties.getBalanceChangeAmount()
            );
            for (int i = 0; i < properties.getThreadCount(); i++) {
                log.info(MsgProvider.buildThreadStartMessage(i));
                futureList.add(executorService.submit(new RequestRunnable(runnableModel)));
            }
            log.info(MsgProvider.HOW_TO_SHUTDOWN_MESSAGE);
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
            if (idList.size() > 0) {
                log.info(MsgProvider.REPOPULATE_MSG);
                httpGate.delete(properties.getServerEndpoint()).close();
                for (Long id : idList) {
                    httpGate.put(properties.getServerEndpoint(),
                                    JsonProvider.buildCreateBalanceJson(id, properties.getBalanceSyncStartAmount()))
                            .close();
                }
            }
        } catch (HttpGateFailureException | HttpRequestFailedException e) {
            throw new RequestServiceFailedException(e);
        }
    }
}
