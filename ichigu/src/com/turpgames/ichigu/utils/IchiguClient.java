package com.turpgames.ichigu.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.JsonEncoders;
import com.turpgames.ichigu.entity.LeadersBoard;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.utils.ScoreManager.ILeadersBoardCallback;
import com.turpgames.utils.Util;

class IchiguClient {
	private final static String sendScoreUrlFormat;
	private final static String getLeadersBoardUrlFormat;
	private final static String registerPlayerUrlFormat;

	static {
		String server = Game.getParam("server");
		String baseUrl = Game.getParam(server + "-server");

		sendScoreUrlFormat = baseUrl + Game.getParam("send-score-params");
		getLeadersBoardUrlFormat = baseUrl + Game.getParam("get-leadersboard-params");
		registerPlayerUrlFormat = baseUrl + Game.getParam("register-player-params");
	}

	public static void sendScore(int mode, int score, final ICallback callback) {
		String url = String.format(sendScoreUrlFormat, Facebook.getPlayer().getId(), mode, score);

		Debug.println("doSendScore, sending score...");
		HttpRequest.newPostRequestBuilder()
				.setUrl(url)
				.setTimeout(5000)
				.setAsync(true)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							callback.onSuccess();
						} else {
							callback.onFail(null);
						}
					}

					@Override
					public void onError(Throwable t) {
						callback.onFail(t);
					}

					@Override
					public void requestCancelled() {
						callback.onFail(null);
					}
				});
	}

	public static void registerPlayer(final ICallback callback) {
		try {
			Debug.println("registerPlayer, getting player...");
			final Player player = Facebook.getPlayer();

			String url = String.format(registerPlayerUrlFormat,
					player.getFacebookId(),
					URLEncoder.encode(player.getEmail(), "UTF-8"),
					URLEncoder.encode(player.getUsername(), "UTF-8"));

			Debug.println("sending http request...");
			HttpRequest.newPostRequestBuilder()
					.setUrl(url)
					.setTimeout(5000)
					.build()
					.send(new IHttpResponseListener() {
						@Override
						public void onHttpResponseReceived(IHttpResponse response) {
							if (response.getStatus() == 200) {
								try {
									String idStr = Util.IO.readUtf8String(response.getInputStream());
									player.setId(Util.Strings.parseInt(idStr));

									Debug.println("register player succeeded...");

									Settings.putString("player-facebook-id", player.getFacebookId());
									Settings.putString("player-id", idStr);

									callback.onSuccess();
								} catch (Throwable t) {
									Debug.println("register player failed while processing registration response");
									callback.onFail(t);
								}
							} else {
								Debug.println("register player failed with http status code: " + response.getStatus());
								callback.onFail(null);
							}
						}

						@Override
						public void onError(Throwable t) {
							Debug.println("register player http request failed");
							callback.onFail(t);
						}

						@Override
						public void requestCancelled() {
							callback.onFail(null);
						}
					});
		} catch (Throwable t) {
			Debug.println("register player failed");
			callback.onFail(t);
		}
	}

	public static void getLeadersBoard(int mode, int days, int whose, final ILeadersBoardCallback callback) {
		int playerId = Facebook.getPlayer().getId();
		final String url = String.format(getLeadersBoardUrlFormat, playerId, mode, days, whose);

		if (whose == Score.FriendsScores) {
			Facebook.loadFriendList(new ICallback() {
				@Override
				public void onSuccess() {
					byte[] contentData = getRequestContentForFriendsHiScores();

					InputStream contentStream = new ByteArrayInputStream(contentData);
					int contentLength = contentData.length;

					doGetLeadersBoard(callback, url, contentStream, contentLength);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
			return;
		}
		else {
			doGetLeadersBoard(callback, url, null, 0);
		}
	}

	private static byte[] getRequestContentForFriendsHiScores() {
		String friendIds = "";

		SocialUser user = Facebook.getUser();
		SocialUser[] friends = user.getFriends();
		for (int i = 0; i < friends.length; i++) {
			friendIds += friends[i].getSocialId();
			if (i < friends.length - 1)
				friendIds += ",";
		}

		return Util.Strings.toUtf8ByteArray(friendIds);
	}

	private static void doGetLeadersBoard(final ILeadersBoardCallback callback, final String url, InputStream contentStream, int contentLength) {
		HttpRequest.newPostRequestBuilder()
				.setUrl(url)
				.setTimeout(5000)
				.setAsync(true)
				.setContent(contentStream, contentLength)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							try {
								String json = Util.IO.readUtf8String(response.getInputStream());
								LeadersBoard lb = JsonEncoders.leadersBoard.decode(json);
								callback.onSuccess(lb);
							} catch (IOException e) {
								callback.onFail(e);
							}
						} else {
							callback.onFail(null);
						}
					}

					@Override
					public void onError(Throwable t) {
						callback.onFail(t);
					}

					@Override
					public void requestCancelled() {
						callback.onFail(null);
					}
				});
	}
}
