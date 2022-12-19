package michael.linker.msr.http.exceptions;

/**
 * Internal error of the HttpGate.
 */
public class HttpGateFailureException extends RuntimeException {
    public HttpGateFailureException(Throwable cause) {
        super(cause);
    }
}
