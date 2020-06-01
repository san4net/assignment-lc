package com.upstox.stats;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource(objectName = "com.upstox.jmx:name=statsBean", description = "managedbean")
@Component("statsBean")
public class StatsMbean {

	private AtomicInteger count = new AtomicInteger();

	private int throughput;

	public void increment() {
		count.incrementAndGet();
	}

	public void reset() {
		count.set(0);
	}

	public void computeThroughput(int ms) {
		throughput = count.get() / ms;
	}

	@ManagedAttribute
	public int getThroughput() {
		return throughput;
	}
	
	@ManagedAttribute
	public int getCount() {
		return count.get();
	}

}
