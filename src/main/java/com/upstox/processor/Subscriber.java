package com.upstox.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upstox.Listener;
import com.upstox.PubSubService;
import com.upstox.model.Quote;

@Component
public class Subscriber {
	@Autowired
	private PubSubService pubSubService;
	
	public void subscribe(Listener<Quote> l) {
		pubSubService.subscribe("test", l);
	}

}
