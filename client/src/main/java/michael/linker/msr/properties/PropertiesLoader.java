package michael.linker.msr.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loader of application properties.
 */
class PropertiesLoader {
    /**
     * Loading properties from a configuration file.
     *
     * @param propertiesFilename name of the configuration file.
     * @return Properties object.
     * @throws PropertiesNotAvailableException if the properties file could not be accessed.
     */
    public static Properties loadProperties(String propertiesFilename)
            throws PropertiesNotAvailableException {
        Properties properties = new Properties();

        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(propertiesFilename)
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesNotAvailableException(e);
        }

        return properties;
    }
}
