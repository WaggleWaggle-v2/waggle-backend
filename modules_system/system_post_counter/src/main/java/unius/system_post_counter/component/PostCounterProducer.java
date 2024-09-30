package unius.system_post_counter.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import unius.independent_kafka.component.KafkaProducer;
import unius.schema.postCounter.PostCounter;

@Component
public class PostCounterProducer extends KafkaProducer<PostCounter> {

    public PostCounterProducer(@Qualifier("postCounterKafkaTemplate") KafkaTemplate<String, PostCounter> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected void handleFailure(String topic, PostCounter message, Throwable ex) {

    }

    @Override
    protected void handleSuccess(SendResult<String, PostCounter> result) {

    }
}
