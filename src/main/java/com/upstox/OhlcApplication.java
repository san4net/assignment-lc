package com.upstox;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.upstox.model.Quote;
import com.upstox.processor.OHLC;
import com.upstox.processor.Processor;
import com.upstox.processor.Subscriber;
import com.upstox.producer.Source;

@SpringBootApplication
public class OhlcApplication implements CommandLineRunner {
	
	@Autowired
	private PubSubService pubSubService;
	
	@Autowired
	private Source<Path> source;
	@Autowired
	Subscriber subscriber;
	
	@Autowired
	private Processor<Quote, OHLC> processor;
	
	public static void main(String[] args) {
		SpringApplication.run(OhlcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		subscriber.subscribe((Quote q) -> {
			processor.onMsgReceived(q);
		});
		source.load(l -> pubSubService.publish("test", l));
	}

}
