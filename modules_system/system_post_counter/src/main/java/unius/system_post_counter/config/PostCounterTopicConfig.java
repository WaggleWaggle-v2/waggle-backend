package unius.system_post_counter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PostCounterTopicConfig {

    @Bean
    public NewTopic postCounterTopic() {
        return TopicBuilder
                .name("post_counter")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
