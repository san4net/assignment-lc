package com.upstox.producer;

import com.upstox.Listener;
import com.upstox.model.Quote;

public interface Source<T> {
	
	void load(Listener<Quote> listener);
	
	T getSource();
}
