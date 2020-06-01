package com.upstox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Quote {
	@JsonProperty("sym")
	private String sym;
	
	@JsonProperty("T")
	private String t;
	
	@JsonProperty("P")
	private double price;
	
	@JsonProperty("Q")
	private double quanity;
	
	@JsonProperty("TS")
	private double ts;
	
	private String side;
	
	@JsonProperty("TS2")
	private long timeStamp;

	public static QuoteBuilder builder() {
		return new QuoteBuilder();
	}

	public static class QuoteBuilder {
		private String sym;
		private double price;
		private double quantity;
		private long timeStamp;

		public QuoteBuilder sym(String sym) {
			this.sym = sym;
			return this;
		}

		public QuoteBuilder price(double price) {
			this.price = price;
			return this;
		}

		public QuoteBuilder quantity(double quantity) {
			this.quantity = quantity;
			return this;
		}

		public QuoteBuilder timeStamp(long timeStamp) {
			this.timeStamp = timeStamp;
			return this;
		}

		public Quote build() {
			Quote q = new Quote();
			q.setSym(sym);
			q.setPrice(price);
			q.setQuanity(quantity);
			q.setTimeStamp(timeStamp);
			return q;
		}

	}
}
