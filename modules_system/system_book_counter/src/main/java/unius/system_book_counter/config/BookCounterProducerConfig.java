package unius.system_book_counter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import unius.core_uuid.util.UuidUtils;
import unius.independent_kafka.config.KafkaProducerConfig;
import unius.schema.bookCounter.BookCounter;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class BookCounterProducerConfig extends KafkaProducerConfig<BookCounter> {

    @Override
    protected void configureProducer(Map<String, Object> props) {
        props.put(ACKS_CONFIG, "all");
        props.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(TRANSACTIONAL_ID_CONFIG, UuidUtils.generateUuid());
    }

    @Bean
    public KafkaTemplate<String, BookCounter> bookCounterKafkaTemplate() {
        return new KafkaTemplate<>(super.producerFactory());
    }
}
