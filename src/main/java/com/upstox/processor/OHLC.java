package com.upstox.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.upstox.model.Quote;

import lombok.Data;

@Data
public class OHLC {
	Logger logger = LoggerFactory.getLogger(OHLC.class.getClass());
	private String sym;
	private double open;
	private double high;
	private double low;
	private double close;
	private double quantity;
	private int barNum;
	private List<Quote> trades = new ArrayList<>(10);

	public OHLC(String sym) {
		this.sym = sym;
		reset();
	}

	public static OHLC newInstance(String symbol) {
		return new OHLC(symbol);
	}

	public synchronized void computeClose() {
		close = trades.size() == 0 ? 0 : trades.get(trades.size()-1).getPrice();
	}

	public synchronized void reset() {
		open = 0;
		high = 0;
		low = 0;
		close = 0;
		quantity = 0;
		barNum++;
		trades = Lists.newArrayList();
	}

	public synchronized void process(Quote msg) {

		trades.add(msg);
		this.quantity = quantity + msg.getQuanity();
		if (open <= 0) {
			// this is first
			open = msg.getPrice();
			high = open;
			low = open;

		} else {
			if (msg.getPrice() > high) {
				// update high
				high = msg.getPrice();
			} else if (msg.getPrice() < low) {
				low = msg.getPrice();
			}
		}
	 logger.info("process event {} " , this);	

	}

	@Override
	public String toString() {
		return "OHLC [ "+"sym=" + sym + "tradesSize=" + trades.size() + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", quantity=" + quantity + ", barNum=" + barNum + "]";
	}
	

}
