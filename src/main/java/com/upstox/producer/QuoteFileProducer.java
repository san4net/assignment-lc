package com.upstox.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upstox.Listener;
import com.upstox.config.OhlcConfig;
import com.upstox.model.Quote;

@Component
public class QuoteFileProducer implements Source<Path> {
	Logger logger = LoggerFactory.getLogger(QuoteFileProducer.class);

	@Autowired
	private OhlcConfig config;
	
	@Override
	public void load(Listener<Quote> listener) {
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new FileReader(getSource().toFile()))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				listener.onMsgReceived(mapper.readValue(line, Quote.class));
			}
		} catch (IOException e) {
			logger.error("eroor starting {}", e);
		}

	}

	@Override
	public Path getSource() {
		return Paths.get(config.getSourceLocation());
	}

}
