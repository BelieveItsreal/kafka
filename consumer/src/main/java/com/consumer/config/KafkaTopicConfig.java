package com.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic creaNewTopic(){
        return new NewTopic("my-topic-1", 3, (short) 1);
    }
}
