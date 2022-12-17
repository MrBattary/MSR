package michael.linker.msr.web.controller.advice;

import lombok.extern.slf4j.Slf4j;
import michael.linker.msr.web.exception.BadRequestResponseStatusException;
import michael.linker.msr.web.exception.NotFoundResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is primary @ControllerAdvice.
 * All custom exceptions must be handled here.
 */
@Slf4j
@RestControllerAdvice
public class MainControllerAdvice {
    /**
     * If set to true, a true error message will be sent.
     * If set to false, the placeholderMessage will be provided.
     */
    @Value("${controller.advice.response.provideMessage}")
    private Boolean shouldResponseWithErrorMessage;
    @Value("${controller.advice.response.message.placeholder}")
    private String placeholderMessage;
    @Value("${controller.advice.response.message.default}")
    private String defaultMessage;

    @ExceptionHandler(NotFoundResponseStatusException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundResponseStatusException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(BadRequestResponseStatusException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestResponseStatusException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAllUncaughtException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(buildErrorMessage(defaultMessage));
    }

    private String buildErrorMessage(String errorMessage) {
        if (shouldResponseWithErrorMessage) {
            return errorMessage;
        }
        return placeholderMessage;
    }
}
