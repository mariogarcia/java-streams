package lab.kafka.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) throws Exception{
        // create properties object for
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("compression.type", "snappy");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // create the producer
        try(Producer<Number, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 100; i < 1000; i++) {
                // create producer record
                String topic = "numbers";
                String value = Integer.toString(i);
                ProducerRecord<Number, String> record = new ProducerRecord<>(topic, i, value);

                // send data (asynchronously)
                System.out.println("sending message " + i);
                producer.send(record);
                Thread.sleep(300);
            }
        }
    }
}
