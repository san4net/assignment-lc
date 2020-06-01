package com.upstox;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.upstox.model.Quote;

@Component
public class PubSubService implements PubSub<String, Quote> {
	private final Logger logger = LoggerFactory.getLogger(PubSubService.class);
	private Map<String, List<Listener<Quote>>> topicSubscriber = new ConcurrentHashMap<>();
	
	@Override
	public void publish(String topic, Quote msg) {
		topicSubscriber.get(topic).forEach(l -> l.onMsgReceived(msg));
	}

	@Override
	public void subscribe(String topic, Listener<Quote> l) {
		
		topicSubscriber.merge(topic, 
				Lists.newArrayList(l), 
				(List<Listener<Quote>> old, List<Listener<Quote>> b)->{
					old.addAll(b);
					return old;
				});
		logger.info("subscribed successfully count {}", topicSubscriber.get(topic).size());
	}

	@Override
	public void unSubscribe(String topic, Listener<Quote> l) {
		topicSubscriber.merge(topic, 
				Lists.newArrayList(l), 
				(List<Listener<Quote>> old, List<Listener<Quote>> b)->{
					old.remove(l);
					return old;
				 }
				);
		
	}
	

}
