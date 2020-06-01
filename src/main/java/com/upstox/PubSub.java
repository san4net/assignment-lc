package com.upstox;

public interface PubSub<T,M> {
	void publish(T topic, M msg);
	void subscribe(T topic, Listener<M> l);
	void unSubscribe(T topic, Listener<M> l);
}
