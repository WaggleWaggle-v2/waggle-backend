package unius.system_book_counter.component;

import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import unius.independent_kafka.component.KafkaConsumer;
import unius.schema.bookCounter.BookCounter;
import unius.system_book_counter.service.UpdateBookCounterCallbackService;

@Component
@RequiredArgsConstructor
public class BookCounterConsumer extends KafkaConsumer<BookCounter> {

    private final UpdateBookCounterCallbackService updateBookCounterCallbackService;

    @Override
    @KafkaListener(topics = "book_counter", groupId = "UNIUS", containerFactory = "kafkaBookCounterListenerContainerFactory")
    protected void processMessage(GenericRecord message, ConsumerRecord<String, GenericRecord> record) {
        BookCounter bookCounter = convertToSchema(message);
        updateBookCounterCallbackService.updateBookCounter(bookCounter.getBookshelfId(), bookCounter.getAdditionValue());
    }

    @Override
    protected BookCounter convertToSchema(GenericRecord message) {
        String bookshelfId = (String) message.get("bookshelfId");
        Long additionValue = (Long) message.get("additionValue");

        return new BookCounter(bookshelfId, additionValue);
    }
}
