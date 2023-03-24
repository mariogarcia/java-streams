package lab.kafka.api.numbers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class NumbersProducer {

    private static final Logger logger = LoggerFactory.getLogger(NumbersProducer.class);
    public static void main(String[] args) throws Exception{
        // create properties object for
        Properties properties = NumbersUtils.loadProperties(Constants.RESOURCES_DIR, "numbers-producer.properties");
        // create the producer
        try(Producer<Integer, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 100; i < 1000; i++) {
                // send data (asynchronously)
                logger.info("sending message " + i);
                producer.send(new ProducerRecord<>(Constants.TOPIC, i, Integer.toString(i)));
                Thread.sleep(Constants.PRODUCER_DELAY_MS);
            }
        }
    }
}
