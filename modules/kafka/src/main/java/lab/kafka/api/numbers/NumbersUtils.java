package lab.kafka.api.numbers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;

public class NumbersUtils {
    static File createTempFile() throws IOException {
        return Files.createTempFile("numbers-", ".csv").toFile();
    }

    static void appendRecordsToFile(File file, List<ConsumerRecord<Integer, String>> records) {
        System.out.printf("Writing records to %s%n", file.getAbsolutePath());
        records.forEach(record -> appendStringLineToFile(file, record.value()));
    }

    static void appendStringLineToFile(File file, String line) {
        try {
            Files.writeString(file.toPath(), line  + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("error while writing to file: " + e.getMessage());
        }
    }
}
