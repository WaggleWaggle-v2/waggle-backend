package unius.system_post_counter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import unius.core_uuid.util.UuidUtils;
import unius.independent_kafka.config.KafkaProducerConfig;
import unius.schema.postCounter.PostCounter;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class PostCounterProducerConfig extends KafkaProducerConfig<PostCounter> {

    @Override
    protected void configureProducer(Map<String, Object> props) {
        props.put(ACKS_CONFIG, "all");
        props.put(ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(TRANSACTIONAL_ID_CONFIG, UuidUtils.generateUuid());
    }

    @Bean(name = "postCounterKafkaTemplate")
    public KafkaTemplate<String, PostCounter> postCounterkafkaTemplate() {
        return new KafkaTemplate<>(super.producerFactory());
    }
}
