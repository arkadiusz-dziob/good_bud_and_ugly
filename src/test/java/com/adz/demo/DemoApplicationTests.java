package com.adz.demo;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.adz.demo.boundaries.kafka.KafkaConsumer;

@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class  DemoApplicationTests {

	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private KafkaConsumer kafkaConsumer;


    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void it_should_send_trace_event() throws Exception {
    	this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
    	
    	while(kafkaConsumer.getPayloads().isEmpty()) {}
    	
    	assertTrue(!kafkaConsumer.getPayloads().isEmpty());
    }

}