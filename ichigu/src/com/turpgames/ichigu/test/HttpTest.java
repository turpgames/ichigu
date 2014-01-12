package com.turpgames.ichigu.test;

import java.io.IOException;

import com.turpgames.framework.v0.IHttpClient;
import com.turpgames.framework.v0.IHttpRequest;
import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpClient;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.utils.Util;

public class HttpTest {
	public static void main(String[] args) {
		try {
			String uri = "http://ichigutest.turpgam.es/?a=l&m=sa";

			IHttpClient client = new HttpClient();

			IHttpRequest req = HttpRequest.newGetRequestBuilder()
					.setUrl(uri)
					.setTimeout(5000)
					.build();

			client.send(req, new IHttpResponseListener() {
				@Override
				public void onHttpResponseReceived(IHttpResponse response) {
					if (response.getStatus() == 200) {
						try {
							String json = Util.IO.readUtf8String(response.getInputStream());
							System.out.println(json);
							LeadersBoard leadersBoard = JsonEncoders.leadersBoard.decode(json);
							for (Score score: leadersBoard.getScores())
								System.out.println(leadersBoard.getPlayer(score.getPlayerId()).getUsername() + "\t" + score.getScore());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else {
						System.out.println("Failed: " + response.getStatus());
					}
				}

				@Override
				public void onError(Throwable t) {
					if (t != null) {
						t.printStackTrace();
					}
					else {
						System.out.println("onError");
					}
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}