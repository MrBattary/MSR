package michael.linker.msr.web.exception;

import org.springframework.http.HttpStatus;

public class BadRequestResponseStatusException extends ResponseStatusException {
    private static final HttpStatus EXCEPTION_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestResponseStatusException() {
        super(EXCEPTION_STATUS);
    }
}
