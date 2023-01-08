package xyz.icefery.demo.dim;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import java.util.Properties;

public class DIM {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "vm101:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "dim");
        FlinkKafkaConsumer<String> sourceFunction = new FlinkKafkaConsumer<>("topic_db", new SimpleStringSchema(), props);

        env
            .addSource(sourceFunction)
            .print();

        env.execute();
    }
}
