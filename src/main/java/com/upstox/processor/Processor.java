package com.upstox.processor;

import com.upstox.Listener;

public interface Processor<T,R> extends Listener<T> {

	void process(T msg, R ohlc);
	
	R getOhlc(T msg);

}