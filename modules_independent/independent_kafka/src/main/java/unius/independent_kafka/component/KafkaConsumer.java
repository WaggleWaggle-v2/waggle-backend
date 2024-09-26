package unius.independent_kafka.component;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public abstract class KafkaConsumer<T> {

    protected abstract void processMessage(GenericRecord message, ConsumerRecord<String, GenericRecord> record);

    protected abstract T convertToSchema(GenericRecord message);
}
