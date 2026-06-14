package com.demo.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Entity.RiderLocation;


@RestController
@RequestMapping("/api")
public class Producer {

    private final KafkaTemplate <String, RiderLocation> kafkaTemplate;

    public Producer(KafkaTemplate<String, RiderLocation> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        RiderLocation location = new RiderLocation("rider123", 28.61, 77.23);
        kafkaTemplate.send("my-topic", location);
        return "Message: "+location.getRiderId();
    }
}
