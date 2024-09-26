package unius.independent_kafka.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
public abstract class KafkaConsumerConfig<T> {

    private static final String SCHEMA_REGISTRY_URL = "schema.registry.url";
    private static final String GROUP_NAME = "UNIUS";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.schema-registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public ConsumerFactory<String, T> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(SCHEMA_REGISTRY_URL, schemaRegistryUrl);
        props.put(GROUP_ID_CONFIG, GROUP_NAME);

        configureConsumer(props);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    protected abstract void configureConsumer(Map<String, Object> props);
}
