package com.upstox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.upstox.model.Quote;
import com.upstox.processor.OHLC;
import com.upstox.processor.Processor;
import com.upstox.stats.StatsMbean;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OhlcApplicationTests {
	
	@Autowired
	private PubSubService service;
	
	@Autowired
	private Processor<Quote, OHLC> processor ;
	@Autowired
	private StatsMbean statsMbean;
	
	private Quote q = null;
	
	static int c=0;
	
	@BeforeEach
	void setUp() throws Exception {
//		service.unSubscribe(topic, l);
//		service.subscribe("test", processor);
		 q = Quote.builder().sym("MSFT").price(2).quantity(2.3).build();
	}
	
	@AfterEach
	void tearDown() {
//		service.unSubscribe("test", processor);
	}
	
	@Test
	@Order(1)
	void testSubscribe() {
		service.publish("test", q);
		assertEquals(3, statsMbean.getCount());
	}
	
	
	
	@Test
	@Order(2)
	void testThroughput() throws InterruptedException {
		assertEquals(0, statsMbean.getThroughput());
		IntStream.range(1, 11).forEach(a ->service.publish("test", modifyQuote(q, a)));
		System.out.println("count=="+ c);
		
		System.out.println("th=="+ statsMbean.getThroughput() +"cc" +statsMbean.getCount());
		statsMbean.computeThroughput(2);

		// 
		assertEquals(6, statsMbean.getThroughput());
		assertEquals(13, statsMbean.getCount());
		assertEquals(1, processor.getOhlc(q).getBarNum());
		
		//assert high
		assertEquals(57.0, processor.getOhlc(q).getHigh());
		assertEquals(2.0, processor.getOhlc(q).getLow());
		
	}
	
	@Test
	@Order(3)
	void testLowHigh() throws InterruptedException {
		Thread.sleep(3000);
		System.out.println(q+ "====");
		IntStream.range(1, 11).forEach(a ->service.publish("test", modifyQuote(q, a)));
		assertEquals(2, processor.getOhlc(q).getBarNum());
		//assert high
		assertEquals(57.0, processor.getOhlc(q).getHigh());
		assertEquals(3.0, processor.getOhlc(q).getLow());
		
	}
	
	
	
	private Quote modifyQuote(Quote quote, double delta) {
		quote.setPrice(quote.getPrice() + delta);
		return q;
	}

}
