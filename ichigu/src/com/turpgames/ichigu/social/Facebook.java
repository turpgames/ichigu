package com.turpgames.ichigu.social;

import java.net.URLEncoder;

import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class Facebook {
	private final static String saveHiScorescoreUrlFormat = "http://192.168.2.4:8080/ichigu-server/ichigu?a=h&m=%d&p=%s&s=%d";
	private final static String registerPlayerUrlFormat = "http://192.168.2.4:8080/ichigu-server/ichigu?a=r&f=%s&e=%s&u=%s";

	private static ISocializer facebook;

	static {
		facebook = Game.getSocializer("facebook");
	}

	public static String getPlayerId() {
		return Settings.getString("player-id", "");
	}

	public static String getFacebookId() {
		return Settings.getString("player-facebook-id", "");
	}

	public static Player getPlayer() {
		return facebook.getPlayer();
	}

	public static void sendScore(final int mode, final int score,
			final ICallback callback) {
		Debug.println("sendScore, execute...");
		execute(new IAction() {
			@Override
			public void execute() {
				doSendScore(mode, score, callback);
			}
		}, callback);
	}

	private static void doSendScore(int mode, int score,
			final ICallback callback) {
		Debug.println("doSendScore, sending score...");
		HttpRequest
				.newPostRequestBuilder()
				.setUrl(String.format(saveHiScorescoreUrlFormat, mode,
						getPlayer().getId(), score)).setTimeout(5000).build()
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
				});
	}

	private static void execute(final IAction action, final ICallback callback) {
		if (facebook.isLoggedIn()) {
			Debug.println("user already logged in, executing action...");
			action.execute();
			return;
		}

		Debug.println("user not logged in, logging in...");
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() { // Login ba�ar�l� oldu
				onLoginSuccess(action, callback);
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}

	private static void onLoginSuccess(final IAction action,
			final ICallback callback) {
		Debug.println("login suceeded, getting player...");
		// facebook kullan�c� bilgilerini getir
		final Player player = getPlayer();

		// Login ba�ar�l� olmas�na ra�men kullan�c� bilgileri al�nam�yorsa
		// logout ol
		// logout ba�ar�l� da ba�ar�s�z da olsa callback fail �a��r
		if (player == null) {
			Debug.println("unable to get player, logging out...");
			facebook.logout(new ICallback() {
				@Override
				public void onSuccess() {
					callback.onFail(null);
				}

				@Override
				public void onFail(Throwable t) {
					callback.onFail(null);
				}
			});
			return;
		}

		// Settingsdeki facebookId, login olan user'�n facebook idsine
		// e�itse action � �al��t�r
		if (getFacebookId().equals(player.getSocialId())) {
			Debug.println("player already registered, executing action now...");
			player.setId(getPlayerId() + "");
			action.execute();
			return;
		}

		Debug.println("player not registered, registering now...");
		// Id ler uyu�muyorsa kullan�c�y� register edip action'� �al��t�r
		registerPlayer(new ICallback() {
			@Override
			public void onSuccess() {
				Debug.println("player registered, executing action now...");
				Settings.putString("player-facebook-id", player.getSocialId());
				Settings.putString("player-id", player.getId());
				action.execute();
			}

			@Override
			public void onFail(Throwable t) {
				Debug.println("player registeration failed...");
				callback.onFail(t);
			}
		});
	}

	private static void registerPlayer(final ICallback callback) {
		try {
			Debug.println("registerPlayer, getting player...");
			final Player player = getPlayer();


			Debug.println("sending http request...");
			HttpRequest
					.newPostRequestBuilder()
					.setUrl(String.format(registerPlayerUrlFormat,
							player.getSocialId(),
							URLEncoder.encode(player.getEmail(), "UTF-8"),
							URLEncoder.encode(player.getName(), "UTF-8")))
					.setTimeout(5000).build().send(new IHttpResponseListener() {
						@Override
						public void onHttpResponseReceived(
								IHttpResponse response) {
							if (response.getStatus() == 200) {
								try {
									String idStr = Utils
											.readUtf8String(response
													.getInputStream());
									player.setId(idStr);

									Debug.println("register player suceeded...");
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
					});
		} catch (Throwable t) {
			Debug.println("register player failed");
			callback.onFail(t);
		}
	}

	private static interface IAction {
		void execute();
	}
}
