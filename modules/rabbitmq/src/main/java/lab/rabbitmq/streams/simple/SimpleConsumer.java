package lab.rabbitmq.streams.simple;

import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.MessageHandler;
import lab.rabbitmq.streams.common.EnvironmentFactory;

public class SimpleConsumer {
    public static void main(String[] args) throws Throwable {
        String queueName = args[0];
        String consumerName = args[2];

        try (Environment environment = EnvironmentFactory.createLocal()) {
            Consumer consumer = environment.consumerBuilder()
                .stream(queueName)
                .name(consumerName)
                .messageHandler(SimpleConsumer::printlnMessage)
                .build();

            System.out.println(consumer);
        }
    }

    private static void printlnMessage(MessageHandler.Context context, Message message) {
        System.out.println(new String(message.getBodyAsBinary()));
    }
}
