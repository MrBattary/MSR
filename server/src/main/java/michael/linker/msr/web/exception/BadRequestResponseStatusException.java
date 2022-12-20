package michael.linker.msr.web.exception;

import org.springframework.http.HttpStatus;

/**
 * 400
 * Bad request wrapper
 */
public class BadRequestResponseStatusException extends ResponseStatusException {
    private static final HttpStatus EXCEPTION_STATUS = HttpStatus.BAD_REQUEST;
    public static final String MSG_NULL = "Provided data is null.";

    /**
     * Constructor with message for the 'null' value.
     */
    public BadRequestResponseStatusException() {
        super(MSG_NULL, EXCEPTION_STATUS);
    }

    /**
     * Constructor with custom message.
     * @param message error message.
     */
    public BadRequestResponseStatusException(String message) {
        super(message, EXCEPTION_STATUS);
    }
}
