package michael.linker.msr.runnable;

import michael.linker.msr.http.HttpGate;
import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import michael.linker.msr.runnable.model.RequestRunnableModel;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Custom runnable that makes requests to the server.
 */
public class RequestRunnable implements Runnable {
    private static final String UPDATE_BALANCE_JSON_OBJECT = "{\"amount\":%d}";
    private final String url, jsonAmount;
    private final Double getRequestProbability;
    private final List<String> getRequestIdList, updateRequestIdList;

    public RequestRunnable(RequestRunnableModel model) {
        url = model.url();
        getRequestProbability = model.getRequestProbability();
        getRequestIdList = Collections.unmodifiableList(model.getRequestIdList());
        updateRequestIdList = Collections.unmodifiableList(model.updateRequestIdList());
        jsonAmount = String.format(UPDATE_BALANCE_JSON_OBJECT, model.changeAmount());
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
