package com.upstox;

@FunctionalInterface
public interface Listener<T> {
	void onMsgReceived(T msg);
}
