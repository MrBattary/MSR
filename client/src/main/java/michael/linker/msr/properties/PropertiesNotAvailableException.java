package michael.linker.msr.properties;

public class PropertiesNotAvailableException extends RuntimeException {
    private static final String MSG = "The properties file is not available.";

    public PropertiesNotAvailableException(Throwable cause) {
        super(MSG, cause);
    }
}
