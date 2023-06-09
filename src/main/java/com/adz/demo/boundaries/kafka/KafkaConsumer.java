package com.adz.demo.boundaries.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    
    private volatile List<String> payloads = new ArrayList<>();

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(@Payload String payload) {
     	payloads.add(payload);
     	log.info("received payload='{}'", payload);
    }

    public List<String> getPayloads() {
    	return payloads;
    }
}
