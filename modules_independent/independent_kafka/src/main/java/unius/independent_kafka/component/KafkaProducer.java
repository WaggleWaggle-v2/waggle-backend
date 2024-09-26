package unius.independent_kafka.component;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public abstract class KafkaProducer<T> {

    protected final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T message) {
        CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(topic, message);

        future.whenComplete((result, ex) -> {
            if(ex != null) {
                handleFailure(topic, message, ex);
            } else {
                handleSuccess(result);
            }
        });
    }

    protected abstract void handleFailure(String topic, T message, Throwable ex);

    protected abstract void handleSuccess(SendResult<String, T> result);
}
