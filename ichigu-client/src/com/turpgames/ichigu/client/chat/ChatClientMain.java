package com.turpgames.ichigu.client.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Decoder;
import org.atmosphere.wasync.Encoder;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.Socket;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.atmosphere.wasync.impl.AtmosphereRequest.AtmosphereRequestBuilder;
import org.atmosphere.wasync.impl.DefaultOptions;
import org.atmosphere.wasync.impl.DefaultOptionsBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.providers.netty.NettyAsyncHttpProvider;

public class ChatClientMain {
	private final static ObjectMapper mapper = new ObjectMapper();

	private static String uuid;

	public static void main(String[] args) throws IOException {

		String url = "http://192.168.2.4:8080/ichigu-server/chat/";
		if (args.length > 0) {
			url = args[0];
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter room name: ");
		url += br.readLine();

		AtmosphereClient client = ClientFactory.getDefault().newClient(
				AtmosphereClient.class);

		AtmosphereRequestBuilder request = client
				.newRequestBuilder()
				.method(Request.METHOD.GET)
				.uri(url)
				.trackMessageLength(true)
				.encoder(new Encoder<ChatMessage, String>() {
					@Override
					public String encode(ChatMessage data) {
						try {
							return mapper.writeValueAsString(data);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				})
				.decoder(new Decoder<String, ChatMessage>() {
					@Override
					public ChatMessage decode(Event type, String data) {

						data = data.trim();

						// Padding
						if (data.length() == 0) {
							return null;
						}

						if (type.equals(Event.MESSAGE)) {
							try {
								return mapper.readValue(data, ChatMessage.class);
							} catch (IOException e) {
								System.out.println("Invalid message " + data);
								return null;
							}
						} else {
							return null;
						}
					}
				}).transport(Request.TRANSPORT.WEBSOCKET)
				.transport(Request.TRANSPORT.SSE)
				.transport(Request.TRANSPORT.LONG_POLLING);

		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		DefaultOptionsBuilder options = new DefaultOptionsBuilder() {
		};
		options.runtime(new AsyncHttpClient(new NettyAsyncHttpProvider(
				new AsyncHttpClientConfig.Builder().build())));
		Socket socket = client.create(new DefaultOptions(options));

		socket.on(Event.MESSAGE.name(), new Function<ChatMessage>() {
			@Override
			public void on(ChatMessage t) {
				if (uuid == null && "server".equals(t.getFrom())) {
					uuid = t.getMessage();
					System.out.println("login-key: " + uuid);
				} else {
					Date d = new Date(t.getTime());
					System.out.println(String.format("%s [%s] - %s",
							t.getFrom(), sdf.format(d), t.getMessage()));
				}
			}
		}).on(new Function<Throwable>() {

			@Override
			public void on(Throwable t) {
				t.printStackTrace();
			}

		}).on(Event.CLOSE.name(), new Function<String>() {
			@Override
			public void on(String t) {
				System.out.println("Connection closed");
			}
		}).open(request.build());

		System.out.print("Choose Name: ");
		String name = br.readLine();

		ChatMessage login = new ChatMessage();
		login.setFrom(name);
		login.setFromUuid(uuid);
		login.setMessage("login message");
		socket.fire(login);

		String message = "";
		while (true) {
			message = br.readLine();
			if (message.equals("quit")) {
				ChatMessage msg = new ChatMessage();
				msg.setFrom(name);
				msg.setFromUuid(uuid);
				msg.setMessage("disconnecting");
				socket.fire(msg);
				break;
			} else if (message.startsWith("@")) {
				ChatMessage msg = new ChatMessage();
				msg.setFrom(name);
				msg.setFromUuid(uuid);
				msg.setMessage(message.substring(message.indexOf(':') + 1));
				msg.setTo(message.substring(1, message.indexOf(':')));
				socket.fire(msg);
			} else {
				ChatMessage msg = new ChatMessage();
				msg.setFrom(name);
				msg.setFromUuid(uuid);
				msg.setMessage(message);
				socket.fire(msg);
			}
		}
		socket.close();
	}
}