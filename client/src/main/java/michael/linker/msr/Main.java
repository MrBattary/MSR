package michael.linker.msr;

import michael.linker.msr.properties.PropertiesNotAvailableException;
import michael.linker.msr.properties.PropertiesNotFoundException;
import michael.linker.msr.properties.PropertiesProvider;
import michael.linker.msr.service.RequestService;
import michael.linker.msr.service.RequestServiceFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main application entry point.
 */
public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final String FINISH_MSG = "The server terminated the connection. " +
            "All threads making requests to the server are completed. The client app will be stopped.";

    /**
     * Main function for app starts.
     *
     * @param args console arguments.
     */
    public static void main(String[] args) {
        try {
            RequestService requestService = new RequestService();
            requestService.prepareAndRun();
            log.info(FINISH_MSG);
        } catch (RequestServiceFailedException e) {
            log.error(e.getMessage());
            log.error(e.getCause());
        }
    }
}