package com.consumer.components;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.consumer.Entity.RiderLocation;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "my-topic", groupId = "consumer-group")
    public void listen1(RiderLocation message) {
        System.out.println("Received Message 1: " + message);
    }

    @KafkaListener(topics = "my-topic", groupId = "consumer-group")
    public void listen2(RiderLocation message) {
        System.out.println("Received Message 2: " + message);
    }

    @KafkaListener(topics = "my-topic", groupId = "consumer-group-rider")
    public void listenRiderLocation(RiderLocation location) {
        System.out.println("Rider Location: " + location.getRiderId() + " : " + location.getLongitude() + " : "
                + location.getLatitude());
    }
}
