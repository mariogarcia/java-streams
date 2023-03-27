package lab.kafka.api.numbers;

import lab.kafka.common.PropertyUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class NumbersConsumer {
    public static void main(String[] args) throws IOException {
        // configuration
        Properties properties = PropertyUtils.loadProperties(Constants.RESOURCES_DIR, "numbers-consumer.properties");
        // create the consumer
        try(Consumer<Integer, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singleton(Constants.TOPIC));
            while (true) {
                ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<Integer, String> record : records) {
                    String message = String.format(
                        "offset: %s -- key: %s -- value: %s -- partition: %s",
                        record.offset(),
                        record.key(),
                        record.value(),
                        record.partition()
                    );
                    System.out.println(message);
                }
            }
        }
    }
}
