package michael.linker.msr;

import michael.linker.msr.provider.MsgProvider;
import michael.linker.msr.service.RequestService;
import michael.linker.msr.service.RequestServiceFailedException;
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
            log.info(MsgProvider.START_MSG);
            RequestService requestService = new RequestService();
            requestService.prepareAndRun();
            log.info(MsgProvider.FINISH_MSG);
        } catch (RequestServiceFailedException e) {
            log.error(e.getMessage());
            log.error(e.getCause());
        }
    }
}