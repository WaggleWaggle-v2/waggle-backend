package unius.system_book_counter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BookCounterTopicConfig {

    @Bean
    public NewTopic bookCounterTopic() {
        return TopicBuilder
                .name("book_counter")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
