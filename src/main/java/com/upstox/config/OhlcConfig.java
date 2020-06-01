package com.upstox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource("classpath:application.properties")
@Data
public class OhlcConfig {
	@Value( "${feed.source}" )
	private String sourceLocation;
	
	@Value( "${ohlc.interval}" )
	private int ohlcInteval;
	
	
}
