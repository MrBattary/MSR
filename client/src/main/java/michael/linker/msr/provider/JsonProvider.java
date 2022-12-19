package michael.linker.msr.provider;

public class JsonProvider {
    private static final String CREATE_BALANCE_JSON_OBJECT = "{\"id\":%d,\"amount\":%d}";
    private static final String UPDATE_BALANCE_JSON_OBJECT = "{\"amount\":%d}";

    public static String buildCreateBalanceJson(Long id, Long amount) {
        return String.format(CREATE_BALANCE_JSON_OBJECT, id, amount);
    }

    public static String buildUpdateBalanceJson(Long amount) {
        return String.format(UPDATE_BALANCE_JSON_OBJECT, amount);
    }
}
