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
package com.turpgames.ichigu.server.chat;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple annotated class that demonstrate the power of Atmosphere. This class
 * supports all transports, support message length garantee, heart beat, message
 * cache thanks to the @managedAService.
 */
@ManagedService(path = "{room: [a-zA-Z][a-zA-Z_0-9]*}")
public class ChatRoom {
	private final Logger logger = LoggerFactory.getLogger(ChatRoom.class);

	private final ConcurrentHashMap<String, String> users = new ConcurrentHashMap<String, String>();
	private String chatroomName;
	private String mappedPath;
	private BroadcasterFactory factory;

	/**
	 * Invoked when the connection as been fully established and suspended, e.g
	 * ready for receiving messages.
	 * 
	 * @param r
	 */
	@Ready(value = Ready.DELIVER_TO.ALL, encoders = { JacksonEncoder.class })
	public ChatMessage onReady(final AtmosphereResource r) {
		logger.info("Browser {} connected.", r.uuid());
		if (chatroomName == null) {
			mappedPath = r.getBroadcaster().getID();
			// Get rid of the AtmosphereFramework mapped path.
			chatroomName = mappedPath.split("/")[2];
			factory = r.getAtmosphereConfig().getBroadcasterFactory();
		}

		ChatMessage readyMsg = new ChatMessage();
		readyMsg.setFrom("server");
		readyMsg.setMessage(r.uuid());
		return readyMsg;
	}

	/**
	 * Invoked when the client disconnect or when an unexpected closing of the
	 * underlying connection happens.
	 * 
	 * @param event
	 */
	@Disconnect
	public void onDisconnect(AtmosphereResourceEvent event) {
		if (event.isCancelled()) {
			// We didn't get notified, so we remove the user.
			users.values().remove(event.getResource().uuid());
			logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
		}
		else if (event.isClosedByClient()) {
			logger.info("Browser {} closed the connection", event.getResource().uuid());
		}
	}

/**
* Simple annotated class that demonstrate how {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder
* can be used.
*
* @param message an instance of {@link ChatMessage }
* @return
* @throws IOException
*/
	@Message(encoders = { JacksonEncoder.class }, decoders = { ChatMessageDecoder.class })
	public ChatMessage onMessage(ChatMessage message) throws IOException {
		if (!users.containsKey(message.getFrom())) {
			users.put(message.getFrom(), message.getFromUuid());
			ChatMessage msg = new ChatMessage();
			msg.setFrom("server");
			msg.setMessage("login ok!");
			return msg;
		}

		if (message.getMessage().contains("disconnecting")) {
			users.remove(message.getFrom());
			ChatMessage msg = new ChatMessage();
			msg.setFrom("server");
			msg.setMessage("disconnect ok!");
			return msg;
		}

		if (message.getTo() != null) {
			String userUUID = users.get(message.getTo());
			if (userUUID != null) {
				AtmosphereResource r = AtmosphereResourceFactory.getDefault().find(userUUID);
				if (r != null) {
					factory.lookup(mappedPath).broadcast(message, r);
				}
			}
			return null;
		}

		logger.info("{} just send {}", message.getFrom(), message.getMessage());
		return message;
	}
}