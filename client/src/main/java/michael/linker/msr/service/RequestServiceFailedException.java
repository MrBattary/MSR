package michael.linker.msr.service;

public class RequestServiceFailedException extends RuntimeException {
    private static final String MSG = "An unexpected error occurred during the operation of the service.";

    public RequestServiceFailedException(Throwable cause) {
        super(MSG, cause);
    }
}
