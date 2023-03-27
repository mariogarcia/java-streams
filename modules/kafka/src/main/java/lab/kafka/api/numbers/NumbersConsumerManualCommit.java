package lab.kafka.api.numbers;

import lab.kafka.common.PropertyUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class NumbersConsumerManualCommit {
    public static void main(String[] args) throws IOException {
        // configuration
        Properties properties = PropertyUtils.loadProperties(Constants.RESOURCES_DIR, "numbers-consumer-manual-commit.properties");
        // create the consumer
        try(Consumer<Integer, String> consumer = new KafkaConsumer<>(properties)) {
            //consumeFromAllPartitions(consumer);
            consumeFromPartitions(consumer, new TopicPartition(Constants.TOPIC, 0));
        }
    }

    static void consumeFromPartitions(Consumer<Integer, String> consumer, TopicPartition... partitions) throws IOException {
        // random file to store messages to
        File randomFile = NumbersUtils.createTempFile();
        // subscribing to specific partitions (ASSIGNING)
        consumer.assign(Arrays.asList(partitions));
        // creating record buffer
        List<ConsumerRecord<Integer, String>> bufferedRecords = new ArrayList<>();
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.printf("reading record from partition %s%n", record.partition());
                bufferedRecords.add(record);
                // when batch size reached then write to disk and commit
                if (bufferedRecords.size() > Constants.CONSUMER_BATCH_SIZE) {
                    NumbersUtils.appendRecordsToFile(randomFile, bufferedRecords);
                    consumer.commitSync();
                    bufferedRecords.clear();
                }
            }
        }
    }

    static void consumeFromAllPartitions(Consumer<Integer, String> consumer) throws IOException {
        // random file to store messages to
        File randomFile = NumbersUtils.createTempFile();
        // subscribing
        consumer.subscribe(Collections.singleton(Constants.TOPIC));
        // creating record buffer
        List<ConsumerRecord<Integer, String>> bufferedRecords = new ArrayList<>();
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<Integer, String> record : records) {
                bufferedRecords.add(record);
                // when batch size reached then write to disk and commit
                if (bufferedRecords.size() > Constants.CONSUMER_BATCH_SIZE) {
                    NumbersUtils.appendRecordsToFile(randomFile, bufferedRecords);
                    consumer.commitSync();
                    bufferedRecords.clear();
                }
            }
        }
    }
}
