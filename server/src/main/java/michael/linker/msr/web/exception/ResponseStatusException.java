package michael.linker.msr.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Parent exception for other status exceptions.
 */
abstract class ResponseStatusException extends RuntimeException {
    private final HttpStatus statusCode;

    public ResponseStatusException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
