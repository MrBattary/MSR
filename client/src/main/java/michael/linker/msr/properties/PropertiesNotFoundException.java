package michael.linker.msr.properties;

public class PropertiesNotFoundException extends RuntimeException {
    private static final String MSG = "Requested property %s was not found or incorrectly provided.";


    public PropertiesNotFoundException(String propertyKey) {
        super(String.format(MSG, propertyKey));
    }

    public PropertiesNotFoundException(String propertyKey, Throwable cause) {
        super(String.format(MSG, propertyKey), cause);
    }
}
