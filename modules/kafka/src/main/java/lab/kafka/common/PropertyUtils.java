package lab.kafka.common;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static Properties loadProperties(String path, String file)  throws IOException {
        Properties properties = new Properties();
        properties.load(PropertyUtils.class.getResourceAsStream(path + file));
        return properties;
    }
}
