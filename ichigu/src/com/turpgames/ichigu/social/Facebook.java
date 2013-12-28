package com.turpgames.ichigu.social;

import java.net.URLEncoder;

import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
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

	public static void sendScore(final int mode, final int score, final ICallback callback) {
		execute(new IAction() {
			@Override
			public void execute() {
				doSendScore(mode, score, callback);
			}
		}, callback);
	}

	private static void doSendScore(int mode, int score, final ICallback callback) {
		HttpRequest.newPostRequestBuilder()
				.setUrl(String.format(saveHiScorescoreUrlFormat, mode, getPlayer().getId(), score))
				.setTimeout(5000)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							callback.onSuccess();
						}
						else {
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
			action.execute();
			return;
		}

		facebook.login(new ICallback() {
			@Override
			public void onSuccess() { // Login baþarýlý oldu
				onLoginSuccess(action, callback);
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}

	private static void onLoginSuccess(final IAction action, final ICallback callback) {
		// facebook kullanýcý bilgilerini getir
		final Player player = getPlayer();

		// Login baþarýlý olmasýna raðmen kullanýcý bilgileri alýnamýyorsa logout ol
		// logout baþarýlý da baþarýsýz da olsa callback fail çaðýr
		if (player == null) {
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

		// Settingsdeki facebookId, login olan user'ýn facebook idsine
		// eþitse action ý çalýþtýr
		if (getFacebookId().equals(player.getSocialId())) {
			player.setId(getPlayerId() + "");
			action.execute();
			return;
		}

		// Id ler uyuþmuyorsa kullanýcýyý register edip action'ý çalýþtýr
		registerPlayer(new ICallback() {
			@Override
			public void onSuccess() {
				Settings.putString("player-facebook-id", player.getSocialId());
				Settings.putString("player-id", player.getId());
				action.execute();
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}

	private static void registerPlayer(final ICallback callback) {
		try {
		final Player player = getPlayer();

		HttpRequest.newPostRequestBuilder()
				.setUrl(String.format(registerPlayerUrlFormat, player.getSocialId(), URLEncoder.encode(player.getEmail(), "UTF-8"), URLEncoder.encode(player.getName(), "UTF-8")))
				.setTimeout(5000)
				.build()
				.send(new IHttpResponseListener() {
					@Override
					public void onHttpResponseReceived(IHttpResponse response) {
						if (response.getStatus() == 200) {
							try {
								String idStr = Utils.readUtf8String(response.getInputStream());
								player.setId(idStr);
								callback.onSuccess();
							}
							catch (Throwable t) {
								callback.onFail(t);
							}
						}
						else {
							callback.onFail(null);
						}
					}

					@Override
					public void onError(Throwable t) {
						callback.onFail(t);
					}
				});
		}
		catch (Throwable t) {
			callback.onFail(t);
		}
	}

	private static interface IAction {
		void execute();
	}
}
