package michael.linker.msr.service;

/**
 * Main client service.
 */
public interface IRequestService {
    /**
     * Get ready and start the required number of threads that will execute requests to the server.
     * Blocks the called thread until all child threads are completed.
     *
     * @throws RequestServiceFailedException If an error occurred during the creation or work of threads.
     */
    void prepareAndRun() throws RequestServiceFailedException;
}
