package michael.linker.msr.http;

/**
 * Types of response codes for the HttpGate.
 *
 * @see HttpGate
 */
public class HttpGateResponseStatusGroup {
    public static final HttpGateResponseStatusGroup INFO;
    public static final HttpGateResponseStatusGroup SUCCESS;
    public static final HttpGateResponseStatusGroup REDIRECT;
    public static final HttpGateResponseStatusGroup CLIENT_ERROR;
    public static final HttpGateResponseStatusGroup SERVER_ERROR;

    static {
        INFO = new HttpGateResponseStatusGroup(100, 199);
        SUCCESS = new HttpGateResponseStatusGroup(200, 299);
        REDIRECT = new HttpGateResponseStatusGroup(300, 399);
        CLIENT_ERROR = new HttpGateResponseStatusGroup(400, 499);
        SERVER_ERROR = new HttpGateResponseStatusGroup(500, 599);
    }

    private final int lowerCode, higherCode;

    public HttpGateResponseStatusGroup(int lowerCode, int higherCode) {
        this.lowerCode = lowerCode;
        this.higherCode = higherCode;
    }


    public static boolean isStatusSuccess(int statusCode) {
        return isStatusBelongsToGroup(statusCode, SUCCESS);
    }

    public static boolean isStatusClientError(int statusCode) {
        return isStatusBelongsToGroup(statusCode, CLIENT_ERROR);
    }

    public static boolean isStatusServerError(int statusCode) {
        return isStatusBelongsToGroup(statusCode, SERVER_ERROR);
    }

    public static boolean isStatusBelongsToGroup(int statusCode,
                                                 HttpGateResponseStatusGroup group) {
        return group.lowerCode <= statusCode && statusCode <= group.higherCode;
    }
}
