package com.adz.demo;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.json.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.adz.demo.boundaries.data.ItemDataService;
import com.adz.demo.boundaries.kafka.KafkaConsumer;
import com.adz.demo.boundaries.kafka.KafkaProducer;
import com.adz.demo.model.Item;

@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class  PaginationTest {

	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ItemDataService itemDataService;
	
	@MockBean
	KafkaConsumer kafkaConsumer;

	@MockBean
	KafkaProducer kafkaProducer;


    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void it_should_send_trace_event() throws Exception {
    	// prepare, add some records to db 
    	itemDataService.create(new Item("a"));
    	itemDataService.create(new Item("b"));
    	itemDataService.create(new Item("c"));
    	itemDataService.create(new Item("d"));
    	itemDataService.create(new Item("e"));
    	itemDataService.create(new Item("f"));

    	itemDataService.getByName("a");
    	
    	// 1st page
    	String result = this.restTemplate.getForObject("http://localhost:" + port + "/api/item/items/page?page=0&size=3&sort=name", String.class);
    	Map resultMap = (Map) new JSONParser(result).parse();  
    	List items = (List) resultMap.get("content");
    	assertEquals(items.size(), 3);
    	assertEquals(((Map)items.get(0)).get("itemId").toString(), "1");
    	assertEquals(((Map)items.get(0)).get("name").toString(), "a");
    	
    	// 2nd page
    	result = this.restTemplate.getForObject("http://localhost:" + port + "/api/item/items/page?page=1&size=3&sort=name", String.class);
    	resultMap = (Map) new JSONParser(result).parse();  
    	items = (List) resultMap.get("content");
    	assertEquals(items.size(), 3);
    	assertEquals(((Map)items.get(0)).get("itemId").toString(), "4");
    	assertEquals(((Map)items.get(0)).get("name").toString(), "d");
    }

}