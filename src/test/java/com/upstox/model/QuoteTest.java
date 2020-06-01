package com.upstox.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QuoteTest {
	Quote q = Quote.builder().sym("MSFT").price(2.1).quantity(2.3d).build();
	
	
	@Test
	void testSymbol() {
		assertEquals("MSFT", q.getSym());
	}
	
	@Test
	void testLow() {
		assertEquals(2.1, q.getPrice());
	}
	
	@Test
	void testQty() {
		assertEquals(2.3, q.getQuanity());
	}
	
}
