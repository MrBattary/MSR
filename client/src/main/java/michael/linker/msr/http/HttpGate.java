package michael.linker.msr.http;

import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class HttpGate implements IHttpGate {
    private final OkHttpClient mOkHttpClient;

    /**
     * Default constructor
     */
    public HttpGate() {
        mOkHttpClient = new OkHttpClient.Builder().build();
    }

    @Override
    public Response get(String url) throws HttpGateFailureException, HttpRequestFailedException {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response post(String url, String json) throws HttpGateFailureException, HttpRequestFailedException {
        RequestBody requestBody = RequestBody.create(json, HttpGateMediaType.JSON);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response put(String url, String json) throws HttpGateFailureException, HttpRequestFailedException {
        RequestBody requestBody = RequestBody.create(json, HttpGateMediaType.JSON);
        Request request = new Request.Builder()
                .put(requestBody)
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    @Override
    public Response delete(String url) throws HttpGateFailureException, HttpRequestFailedException {
        Request request = new Request.Builder()
                .delete()
                .url(url)
                .build();
        Response response = makeRequest(request);
        validateResponse(response);
        return response;
    }

    private Response makeRequest(Request request) throws HttpGateFailureException {
        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new HttpGateFailureException(e);
        }
    }

    private void validateResponse(Response response) throws HttpRequestFailedException {
        int responseCode = response.code();
        if (!HttpGateResponseStatusGroup.isStatusSuccess(responseCode)) {
            response.close();
            throw new HttpRequestFailedException(responseCode);
        }
    }
}
