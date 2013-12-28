/*
 * Copyright 2013 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.turpgames.ichigu.chat.servlet;

import java.util.Date;

import com.turpgames.servlet.JsonEncoder;

public class ChatMessage {
	private String message;
	private String from;
	private String to;
	private String fromUuid;
	private long time;

	public ChatMessage() {
		time = new Date().getTime();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFromUuid() {
		return fromUuid;
	}

	public void setFromUuid(String fromUuid) {
		this.fromUuid = fromUuid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static class Encoder extends JsonEncoder<ChatMessage> {
		public Encoder() {
			super(ChatMessage.class);
		}
	}
}