package michael.linker.msr.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Parent exception for other status exceptions.
 */
abstract class ResponseStatusException extends RuntimeException {
    private final HttpStatus statusCode;
    private String msg;

    public ResponseStatusException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseStatusException(String message, HttpStatus statusCode) {
        msg = message;
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
