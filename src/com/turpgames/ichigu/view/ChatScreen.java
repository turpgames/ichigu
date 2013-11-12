package com.turpgames.ichigu.view;

import java.io.IOException;
import java.util.Date;

import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Decoder;
import org.atmosphere.wasync.Encoder;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class ChatScreen extends IchiguScreen {
	private ImageButton btn;
	private Text txt;

	private String serverIpAddress = "http://192.168.2.4:8080/AtmosphereServer";
	private final static ObjectMapper mapper = new ObjectMapper(); 

	@Override
	public void init() {
		super.init();
		txt = new Text();
		txt.setFontScale(0.5f);
		txt.setVerticalAlignment(Text.VAlignTop);
		txt.setHorizontalAlignment(Text.HAlignLeft);
		txt.setWidth(Game.getScreenWidth());
		txt.setHeight(Game.getScreenHeight());

		btn = new ImageButton(100, 100, "libgdx");
		btn.getLocation().set((Game.getScreenWidth() - 100) / 2, 110);

		try {
			AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);

			RequestBuilder request = client.newRequestBuilder()
					.method(Request.METHOD.GET)
					.uri(serverIpAddress + "/chat")
					.trackMessageLength(true)
					.encoder(new Encoder<Data, String>() {
						@Override
						public String encode(Data data) {
							try {
								return mapper.writeValueAsString(data);
							}
							catch (IOException e) {
								throw new RuntimeException(e);
							}
						}
					})
					.decoder(new Decoder<String, Data>() {
						@Override
						public Data decode(Event type, String data) {

							data = data.trim();

							// Padding
							if (data.length() == 0) {
								return null;
							}

							if (type.equals(Event.MESSAGE)) {
								try {
									return mapper.readValue(data, Data.class);
								}
								catch (IOException e) {
									e.printStackTrace();
									return null;
								}
							}
							else {
								return null;
							}
						}
					})
					.transport(Request.TRANSPORT.WEBSOCKET);

			final org.atmosphere.wasync.Socket socket = client.create();
			socket.on("message", new Function<Data>() {
				@Override
				public void on(final Data t) {
					Date d = new Date(t.getTime());
					txt.setText(txt.getText() + "\n" + t.getAuthor() + ": " + t.getMessage());
				}
			}).on(new Function<Throwable>() {
				@Override
				public void on(Throwable t) {
					txt.setText("ERROR 3: " + t.getMessage());
					t.printStackTrace();
				}

			}).open(request.build());

			final String name = "taga";

			btn.setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					try {
						socket.fire(new Data(name, new Date().toString()));
					}
					catch (Throwable e) {
						txt.setText("ERROR 3: " + e.getMessage());
						e.printStackTrace();
					}
				}
			});

		}
		catch (Throwable e) {
			txt.setText("Unable to connect: " + e.getMessage());

			e.printStackTrace();
		}

	}

	@Override
	public void draw() {
		super.draw();
		txt.draw();
		btn.draw();
	}
	
	public static final class Data {

		private String message;
		private String author;
		private long time;

		public Data() {
			this("", "");
		}

		public Data(String author, String message) {
			this.author = author;
			this.message = message;
			this.time = new Date().getTime();
		}

		public String getMessage() {
			return message;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

	}
}
