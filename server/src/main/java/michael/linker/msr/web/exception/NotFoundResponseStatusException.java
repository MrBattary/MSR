package michael.linker.msr.web.exception;

import org.springframework.http.HttpStatus;

public class NotFoundResponseStatusException extends ResponseStatusException {
    private static final HttpStatus EXCEPTION_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundResponseStatusException() {
        super(EXCEPTION_STATUS);
    }
}
