package lab.kafka.streams.wordcount;

import lab.kafka.common.PropertyUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class WordCountApplication {
    public static void main(String[] args) throws IOException {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> stream = builder.stream("word-count-input");

        KTable<String,Long> table = stream
            .mapValues(value -> value.toLowerCase())
            .flatMapValues(value -> Arrays.asList(value.split(" ")))
            .selectKey((ignored, value) -> value)
            .groupByKey()
            .count();

        table.toStream().to("word-count-output", Produced.with(Serdes.String(), Serdes.Long()));

        // properties and topology
        Properties props = PropertyUtils.loadProperties("/lab/kafka/streams/", "wordcount.properties");
        Topology topology = builder.build();

        // create kafka stream
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

        // close at runtime shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
