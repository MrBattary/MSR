package michael.linker.msr.http.exceptions;

/**
 * If there is no 200-299 code in the response.
 */
public class HttpRequestFailedException extends RuntimeException {
    private static final String MSG = "The request ended with an unsatisfactory status %s.";
    public HttpRequestFailedException(int statusCode) {
        super(String.format(MSG, statusCode));
    }
}
