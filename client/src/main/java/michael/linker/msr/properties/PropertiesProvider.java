package michael.linker.msr.properties;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provider of application properties.
 */
@Getter
public class PropertiesProvider {
    // Keys
    private static final String PROPERTIES_FILENAME = "application.properties";
    private static final String THREAD_COUNT_KEY = "client.threadCount";
    private static final String READ_QUOTA_KEY = "client.readQuota";
    private static final String WRITE_QUOTA_KEY = "client.writeQuota";
    private static final String BALANCE_READ_ID_LIST_KEY = "client.balance.readIdList";
    private static final String BALANCE_WRITE_ID_LIST_KEY = "client.balance.writeIdList";
    private static final String BALANCE_SYNCHRONIZE_READ_KEY = "client.balance.synchronize.read";
    private static final String BALANCE_SYNCHRONIZE_WRITE_KEY = "client.balance.synchronize.write";
    private static final String BALANCE_SYNCHRONIZE_START_AMOUNT_KEY = "client.balance.synchronize.startAmount";

    // Values
    private final Long threadCount, readQuota, writeQuota, balanceSyncStartAmount;
    private final List<Long> readIdList, writeIdList;
    private final Boolean readSync, writeSync;

    /**
     * Default constructor.
     *
     * @throws PropertiesNotAvailableException if .properties file was not available.
     * @throws PropertiesNotFoundException if any required property was not found.
     */
    public PropertiesProvider() throws PropertiesNotAvailableException, PropertiesNotFoundException {
        Properties properties = PropertiesLoader.loadProperties(PROPERTIES_FILENAME);
        threadCount = this.getLongProperty(properties, THREAD_COUNT_KEY);
        readQuota = this.getLongProperty(properties, READ_QUOTA_KEY);
        writeQuota = this.getLongProperty(properties, WRITE_QUOTA_KEY);
        balanceSyncStartAmount = this.getLongProperty(properties, BALANCE_SYNCHRONIZE_START_AMOUNT_KEY);
        readIdList = this.getLongListProperty(properties, BALANCE_READ_ID_LIST_KEY);
        writeIdList = this.getLongListProperty(properties, BALANCE_WRITE_ID_LIST_KEY);
        readSync = this.getBooleanProperty(properties, BALANCE_SYNCHRONIZE_READ_KEY);
        writeSync = this.getBooleanProperty(properties, BALANCE_SYNCHRONIZE_WRITE_KEY);
    }

    private Long getLongProperty(Properties properties, String key) throws PropertiesNotFoundException {
        try {
            return Long.valueOf(properties.getProperty(key));
        } catch (NumberFormatException e) {
            throw new PropertiesNotFoundException(key, e);
        }
    }

    private List<Long> getLongListProperty(Properties properties, String key) throws PropertiesNotFoundException {
        List<String> stringList = new ArrayList<>(Arrays.asList(properties.getProperty(key).split(",")));
        return stringList.stream()
                .map(id -> {
                    try {
                        return Long.valueOf(id);
                    } catch (NumberFormatException e) {
                        throw new PropertiesNotFoundException(key, e);
                    }
                })
                .collect(Collectors.toList());
    }

    private Boolean getBooleanProperty(Properties properties, String key) throws PropertiesNotFoundException {
        String stringBool = properties.getProperty(key).toLowerCase();
        if (!stringBool.equals(Boolean.FALSE.toString().toLowerCase()) &&
                !stringBool.equals(Boolean.TRUE.toString().toLowerCase())) {
            throw new PropertiesNotFoundException(key);
        }
        return Boolean.valueOf(stringBool);
    }
}
