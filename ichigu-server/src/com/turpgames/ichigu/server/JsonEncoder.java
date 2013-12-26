package com.turpgames.ichigu.server;

import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;

import com.google.gson.Gson;

public  class JsonEncoder<T> implements Encoder<T, String>, Decoder<String, T> {
	private final Gson gson;
	private final Class<T> type;

	protected JsonEncoder(Class<T> type) {
		this.gson = new Gson();
		this.type = type;
	}

	@Override
	public T decode(String json) {
		return gson.fromJson(json, type);
	}
	
	@Override
	public String encode(T obj) {
		return gson.toJson(obj);
	}
}
