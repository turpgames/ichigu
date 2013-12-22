package com.turpgames.ichigu.server;

import java.util.Calendar;
import java.util.List;

abstract class Cache<T> {
	private final int timeoutMinutes;
	
	private Calendar timeout;	
	private List<T> data;
	
	public Cache(int timeoutMinutes) {
		this.timeoutMinutes = timeoutMinutes;
	}
	
	private boolean requiresReload() {	
		return data == null || Calendar.getInstance().compareTo(timeout) > 0;
	}
	
	public List<T> getData() {
		if (requiresReload()) {
			data = load();
			timeout = Calendar.getInstance();
			timeout.add(Calendar.MINUTE, timeoutMinutes);
		}
		
		return data;
	}
	
	protected abstract List<T> load();
}
