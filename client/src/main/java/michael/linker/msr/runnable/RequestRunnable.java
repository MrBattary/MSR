package michael.linker.msr.runnable;

import michael.linker.msr.http.HttpGate;
import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import michael.linker.msr.provider.JsonProvider;
import michael.linker.msr.runnable.model.RequestRunnableModel;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Custom runnable that makes requests to the server.
 */
public class RequestRunnable implements Runnable {
    private final String url, jsonAmount;
    private final Double getRequestProbability;
    private final List<String> getRequestIdList, updateRequestIdList;

    public RequestRunnable(RequestRunnableModel model) {
        url = model.url();
        getRequestProbability = model.getRequestProbability();
        getRequestIdList = Collections.unmodifiableList(model.getRequestIdList());
        updateRequestIdList = Collections.unmodifiableList(model.updateRequestIdList());
        jsonAmount = JsonProvider.buildUpdateBalanceJson(model.changeAmount());
    }

    @Override
    public void run() {
        HttpGate threadGate = new HttpGate();
        while (true) {
            try {
                if (ThreadLocalRandom.current().nextDouble(1) < getRequestProbability) {
                    threadGate.get(url + "/" +
                            getRequestIdList.get(ThreadLocalRandom.current().nextInt(getRequestIdList.size()))
                    ).close();
                } else {
                    threadGate.post(url + "/" +
                            updateRequestIdList.get(ThreadLocalRandom.current().nextInt(updateRequestIdList.size())),
                            jsonAmount
                    ).close();
                }
            } catch (HttpGateFailureException e) {
                // Safely end thread
                break;
            } catch (HttpRequestFailedException ignored) {
            }
        }
    }
}
