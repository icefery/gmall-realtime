package xyz.icefery.demo.ods;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.Properties;

public class TopicConfig {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        MySqlSource<String> source = MySqlSource
            .<String>builder()
            .hostname("vm101")
            .port(3306)
            .username("root")
            .password("root")
            .databaseList("gmall_realtime")
            .tableList("gmall_realtime.table_process")
            .deserializer(new JsonDebeziumDeserializationSchema())
            .startupOptions(StartupOptions.initial())
            .build();

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "vm101:9092");
        FlinkKafkaProducer<String> sinkFunction = new FlinkKafkaProducer<>("topic_config", new SimpleStringSchema(), props);

        env
            .fromSource(source, WatermarkStrategy.noWatermarks(), "mysql")
            .addSink(sinkFunction);

        env.execute("topic_config");
    }
}
