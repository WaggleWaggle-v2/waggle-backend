package unius.system_post_counter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import unius.independent_kafka.config.KafkaConsumerConfig;
import unius.schema.postCounter.PostCounter;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.ISOLATION_LEVEL_CONFIG;

@Configuration
public class PostCounterConsumerConfig extends KafkaConsumerConfig<PostCounter> {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostCounter> kafkaPostCounterListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PostCounter> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(super.consumerFactory());

        return factory;
    }

    @Override
    protected void configureConsumer(Map<String, Object> props) {
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
    }
}
