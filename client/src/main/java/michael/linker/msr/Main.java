package michael.linker.msr;

import michael.linker.msr.properties.PropertiesNotAvailableException;
import michael.linker.msr.properties.PropertiesNotFoundException;
import michael.linker.msr.properties.PropertiesProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application entry point.
 */
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    /**
     * Main function for app starts.
     *
     * @param args console arguments.
     */
    public static void main(String[] args) {
        try {
            PropertiesProvider properties = new PropertiesProvider();
            log.info(properties.getReadQuota());
        } catch (PropertiesNotFoundException | PropertiesNotAvailableException e) {
            log.error(e.getMessage());
        }
    }
}