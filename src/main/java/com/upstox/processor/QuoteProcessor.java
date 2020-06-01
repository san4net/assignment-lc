package com.upstox.processor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.upstox.PubSubService;
import com.upstox.config.OhlcConfig;
import com.upstox.model.Quote;
import com.upstox.stats.StatsMbean;

@Component
@EnableScheduling
public class QuoteProcessor implements Processor<Quote, OHLC>{
	private final Logger logger = LoggerFactory.getLogger(QuoteProcessor.class);
	
	@Autowired
	private StatsMbean stats;
	@Autowired
	private OhlcConfig config;
	
	@Autowired
	private PubSubService pubSubService;
	
	private Map<String, OHLC> ohlcOfSymbols = new ConcurrentHashMap<>();
	

	@Override
	public void onMsgReceived(Quote msg) {
		stats.increment();

		OHLC ohlc = null;
		if((ohlc = ohlcOfSymbols.get(msg.getSym())) == null) {
			ohlcOfSymbols.putIfAbsent(msg.getSym(), 
					OHLC.newInstance(msg.getSym()));
		}
		
		ohlc = ohlcOfSymbols.get(msg.getSym());
		process(msg, ohlc);
		logger.info("event {}", ohlc);
//		pubSubService.publish("result", ohlc);
	}
	
	@Override
	public void process(Quote msg, OHLC ohlc) {
		synchronized (ohlc) {
			ohlc.process(msg);
		}
	}
	
	@Scheduled(fixedDelayString = "${ohlc.interval}")
	public void nextBarEvent() {
			stats.computeThroughput(config.getOhlcInteval());
			stats.reset();
			
			Collection<OHLC> values = ohlcOfSymbols.values();
			values.forEach(a->{
				a.computeClose();
				logger.info("bar close event before {}", a);
				a.reset();
				logger.info("bar close event {}", a);
			});
			
	}

	@Override
	public OHLC getOhlc(Quote sym) {
		return ohlcOfSymbols.get(sym.getSym());
	}
	
}
