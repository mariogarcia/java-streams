package lab.rabbitmq.streams.common;

import com.rabbitmq.stream.Environment;

public class EnvironmentFactory {
    public static Environment createLocal() {
        return Environment.builder().host("localhost").port(5552).build();
    }
}
