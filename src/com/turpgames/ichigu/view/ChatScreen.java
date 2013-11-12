package com.turpgames.ichigu.view;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Decoder;
import org.atmosphere.wasync.Encoder;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.OptionsBuilder;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.atmosphere.wasync.impl.DefaultOptions;
import org.atmosphere.wasync.impl.DefaultOptionsBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpProvider;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.providers.netty.NettyAsyncHttpProvider;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class ChatScreen extends IchiguScreen {
	private ImageButton btn;
	private Text txt;

	private String serverIpAddress = "http://192.168.2.4:8080/AtmosphereServer/chat";
	private final static ObjectMapper mapper = new ObjectMapper(); 

	private org.atmosphere.wasync.Socket socket = null;
	
	@Override
	public void init() {		
		final String name = "ios";
		
		super.init();
		
		txt = new Text();
		txt.setFontScale(0.5f);
		txt.setVerticalAlignment(Text.VAlignCenter);
		txt.setHorizontalAlignment(Text.HAlignCenter);
		txt.setWidth(Game.getScreenWidth() - 200);
		txt.setHeight(Game.getScreenHeight() - 200);
		txt.setX(100);
		txt.setY(100);

		btn = new ImageButton(100, 100, "libgdx");
		btn.getLocation().set((Game.getScreenWidth() - 100) / 2, 110);

		btn.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				System.out.print("Button Tapped: ");
				try {
					socket.fire(new Data(name, new Date().toString()));
					System.out.println("OK!");
				}
				catch (Throwable e) {
					txt.setText("ERROR 3: " + e.getMessage());
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		});
		
		try {
			URL url = new URL(serverIpAddress);
			URLConnection conn =  url.openConnection();
			conn.getInputStream().close();
			System.out.println("Connection OK: " + conn.getClass().getName());
		}
		catch (Throwable t) {
			System.out.println("Connection FAIL!");
			t.printStackTrace();
		}

		try {

			System.out.println("1");
			AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);

			System.out.println("2");
			RequestBuilder request = client.newRequestBuilder()
					.method(Request.METHOD.GET)
					.uri(serverIpAddress)
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
					;
			
			System.out.println("3");
			DefaultOptionsBuilder options = new DefaultOptionsBuilder(){};
			options.runtime(new AsyncHttpClient(new NettyAsyncHttpProvider(new AsyncHttpClientConfig.Builder().build())));
			socket = client.create(new DefaultOptions(options));
			
			System.out.println("4");
			socket.on("message", new Function<Data>() {
				@Override
				public void on(final Data t) {
					System.out.println("6");
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
			
			
			System.out.println("5");
		}
		catch (Throwable e) {
			System.out.println("7: " + e.getMessage());
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
	
	public static final class NettyOptionsBuilder extends DefaultOptionsBuilder {
		
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
