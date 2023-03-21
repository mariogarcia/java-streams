package lab.rabbitmq.streams.simple;

import com.rabbitmq.stream.*;
import lab.rabbitmq.streams.common.EnvironmentFactory;
import lab.rabbitmq.streams.common.Messages;

public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        String queueName = args[0];
        int numberOfItems = Integer.parseInt(args[1]);

        try(Environment environment = EnvironmentFactory.createLocal()) {
            Producer producer = environment.producerBuilder().stream(queueName).build();
            for (int i = 1; i <= numberOfItems; i++) {
                Message message = Messages.createPlainMessage(producer, String.format("%s", i));
                producer.send(message, SimpleProducer::handleConfirmation);
                Thread.sleep(1000);
            }
        }
    }

    private static void handleConfirmation(ConfirmationStatus status) {
        if (status.isConfirmed()) {
            System.out.println("message confirmed by broker");
        } else {
            System.out.println("message NOT confirmed by broker");
        }
    }
}
