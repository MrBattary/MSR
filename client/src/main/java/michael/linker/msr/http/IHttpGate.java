package michael.linker.msr.http;

import michael.linker.msr.http.exceptions.HttpGateFailureException;
import michael.linker.msr.http.exceptions.HttpRequestFailedException;
import okhttp3.Response;

/**
 * OkHttp3 wrapper.
 */
public interface IHttpGate {
    /**
     * Makes HTTP GET request to the provided URL.
     *
     * @param url request URL.
     * @return response object, if response has code 200-299.
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection.
     * @throws HttpRequestFailedException If there is no 200-299 code in the response.
     */
    Response get(String url) throws HttpGateFailureException, HttpRequestFailedException;

    /**
     * Makes HTTP POST request to the provided URL with provided JSON body.
     *
     * @param url  request URL.
     * @param json Json object as String.
     * @return response object, if response has code 200-299.
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection.
     * @throws HttpRequestFailedException If there is no 200-299 code in the response.
     */
    Response post(String url, String json) throws HttpGateFailureException, HttpRequestFailedException;

    /**
     * Makes HTTP PUT request to the provided URL with provided JSON body.
     *
     * @param url  request URL.
     * @param json Json object as String.
     * @return response object, if response has code 200-299.
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection.
     * @throws HttpRequestFailedException If there is no 200-299 code in the response.
     */
    Response put(String url, String json) throws HttpGateFailureException, HttpRequestFailedException;

    /**
     * Makes HTTP DELETE request to the provided URL.
     *
     * @param url request URL.
     * @return response object, if response has code 200-299.
     * @throws HttpGateFailureException Internal error of the Gate, e.g. lost connection.
     * @throws HttpRequestFailedException If there is no 200-299 code in the response.
     */
    Response delete(String url) throws HttpGateFailureException, HttpRequestFailedException;
}
