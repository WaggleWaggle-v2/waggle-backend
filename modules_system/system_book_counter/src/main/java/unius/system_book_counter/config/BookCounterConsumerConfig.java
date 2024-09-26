package unius.system_book_counter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import unius.independent_kafka.config.KafkaConsumerConfig;
import unius.schema.bookCounter.BookCounter;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.ISOLATION_LEVEL_CONFIG;

@Configuration
public class BookCounterConsumerConfig extends KafkaConsumerConfig<BookCounter> {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookCounter> kafkaBookCounterListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookCounter> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(super.consumerFactory());

        return factory;
    }

    @Override
    protected void configureConsumer(Map<String, Object> props) {
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
    }
}
