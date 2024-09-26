package unius.system_book_counter.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import unius.independent_kafka.component.KafkaProducer;
import unius.schema.bookCounter.BookCounter;

@Component
public class BookCounterProducer extends KafkaProducer<BookCounter> {

    public BookCounterProducer(@Qualifier("bookCounterKafkaTemplate") KafkaTemplate<String, BookCounter> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected void handleFailure(String topic, BookCounter message, Throwable ex) {

    }

    @Override
    protected void handleSuccess(SendResult<String, BookCounter> result) {

    }
}
