package lab.rabbitmq.streams.common;

import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.Producer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class Messages {

    public static Message createPlainMessage(Producer producer, String message) {
        return producer.messageBuilder()
            .properties()
            .contentType("text/plain")
            .absoluteExpiryTime(inAMinute())
            .creationTime(new Date().getTime())
            .messageId(UUID.randomUUID())
            .messageBuilder()
            .addData(message.getBytes())
            .build();
    }

    static long inAMinute() {
        return LocalDateTime.now()
                .plusMinutes(1)
                .toEpochSecond(ZoneId.of("Europe/Madrid").getRules().getOffset(LocalDateTime.now()));
    }
}
