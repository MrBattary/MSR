package michael.linker.msr.provider;

public class MsgProvider {
    private static final String THREAD_START_MSG = "Thread %d has been started.";
    public static final String START_MSG = "Client app has been started.";
    public static final String REPOPULATE_MSG = "The client will delete all balances from the server and " +
            "create new ones with IDs from the readIdList and writeIdList";

    public static final String HOW_TO_SHUTDOWN_MESSAGE = "To correctly terminate all client threads and " +
            "shutdown the client app, shutdown the server. E.g. Ctrl+C in the server terminal.";
    public static final String FINISH_MSG = "The server terminated the connection. " +
            "All threads making requests to the server are completed. The client app will be stopped.";

    public static String buildThreadStartMessage(int threadNumber) {
        return String.format(THREAD_START_MSG, threadNumber);
    }
}
