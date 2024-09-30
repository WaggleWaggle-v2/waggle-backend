package unius.system_post_counter.component;

import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import unius.independent_kafka.component.KafkaConsumer;
import unius.schema.postCounter.PostCounter;
import unius.system_post_counter.service.UpdatePostCounterCallbackService;

@Component
@RequiredArgsConstructor
public class PostCounterConsumer extends KafkaConsumer<PostCounter> {

    private final UpdatePostCounterCallbackService updatePostCounterCallbackService;

    @Override
    @KafkaListener(topics = "post_counter", groupId = "UNIUS", containerFactory = "kafkaPostCounterListenerContainerFactory")
    protected void processMessage(GenericRecord message, ConsumerRecord<String, GenericRecord> record) {
        PostCounter postCounter = convertToSchema(message);
        updatePostCounterCallbackService.updatePostCounter(postCounter.getUserId(), postCounter.getAdditionValue());
    }

    @Override
    protected PostCounter convertToSchema(GenericRecord message) {
        String userId = (String) message.get("userId");
        Long additionValue = (Long) message.get("additionValue");

        return new PostCounter(userId, additionValue);
    }
}
