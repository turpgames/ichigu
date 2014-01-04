package com.turpgames.ichigu.social;

import java.net.URLEncoder;

import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.component.UIBlocker;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

public class Facebook {
	private final static String saveHiScorescoreUrlFormat = "http://78.188.46.171/ichigu-server/ichigu?a=h&m=%d&p=%s&s=%d";
	private final static String registerPlayerUrlFormat = "http://78.188.46.171/ichigu-server/ichigu?a=r&f=%s&e=%s&u=%s";

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

	public static boolean canLogin() {
		return !"".equals(Settings.getString("player-facebook-id", ""));
	}

	public static Player getPlayer() {
		return facebook.getPlayer();
	}

	public static boolean isLoggedIn() {
		return facebook.isLoggedIn();
	}

	public static void login(final ICallback callback) {
		final CallbackInterceptor callbackInterceptor = new CallbackInterceptor(callback);
		blockUI(R.strings.loggingIn);
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				onLoginSuccess(callbackInterceptor);
			}

			@Override
			public void onFail(Throwable t) {
				unblockUI();
				callback.onFail(t);
			}
		});
	}

	public static void logout(final ICallback callback) {
		blockUI(R.strings.loggingOut);
		facebook.logout(new ICallback() {
			@Override
			public void onSuccess() {
				Settings.putString("player-facebook-id", "");
				Settings.putString("player-id", "");
				unblockUI();
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				unblockUI();
				callback.onFail(t);
			}
		});
	}

	public static void sendScore(final int mode, final int score, final ICallback callback) {
		final CallbackInterceptor callbackInterceptor = new CallbackInterceptor(callback);
		Debug.println("sendScore, execute...");
		executeSafe(new IAction() {
			@Override
			public void execute() {
				doSendScore(mode, score, callbackInterceptor);
			}
		}, callbackInterceptor);
	}

	private static void doSendScore(int mode, int score, final ICallback callback) {
		blockUI(R.strings.sendingScore);
		String url = String.format(saveHiScorescoreUrlFormat, mode, getPlayer().getId(), score);

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
				});
	}

	/**
	 * ensures player is logged in and registered, before executing action
	 * 
	 * @param action
	 * @param callback
	 */
	private static void executeSafe(final IAction action, final ICallback callback) {
		if (facebook.isLoggedIn()) {
			Debug.println("user already logged in, executing action...");
			action.execute();
			return;
		}

		updateBlockMessage(R.strings.loggingIn);
		Debug.println("user not logged in, logging in...");
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				onLoginSuccess(new ICallback() {
					@Override
					public void onSuccess() {
						action.execute();
					}

					@Override
					public void onFail(Throwable t) {
						callback.onFail(t);
					}
				});
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}

	/**
	 * gets player info from facebook and registers player if required
	 * 
	 * @param action
	 * @param callback
	 */
	private static void onLoginSuccess(final ICallback callback) {
		Debug.println("login suceeded, getting player...");
		final Player player = getPlayer();

		if (player == null) {
			updateBlockMessage(R.strings.loginError);
			Debug.println("unable to get player, logging out...");
			logout(new ICallback() {
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

		if (getFacebookId().equals(player.getSocialId())) {
			Debug.println("player already registered");
			player.setId(getPlayerId() + "");
			callback.onSuccess();
			return;
		}

		Debug.println("player not registered, registering now...");
		registerPlayer(new ICallback() {
			@Override
			public void onSuccess() {
				Debug.println("player registered");
				Settings.putString("player-facebook-id", player.getSocialId());
				Settings.putString("player-id", player.getId());
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				Debug.println("player registration failed, logging out...");
				logout(new ICallback() {
					@Override
					public void onSuccess() {
						callback.onFail(null);
					}

					@Override
					public void onFail(Throwable t) {
						callback.onFail(null);
					}
				});
			}
		});
	}

	private static void registerPlayer(final ICallback callback) {
		try {
			updateBlockMessage(R.strings.registeringPlayer);
			Debug.println("registerPlayer, getting player...");
			final Player player = getPlayer();

			String url = String.format(registerPlayerUrlFormat,
					player.getSocialId(),
					URLEncoder.encode(player.getEmail(), "UTF-8"),
					URLEncoder.encode(player.getName(), "UTF-8"));

			Debug.println("sending http request...");
			HttpRequest.newPostRequestBuilder()
					.setUrl(url)
					.setTimeout(5000)
					.setAsync(true)
					.build()
					.send(new IHttpResponseListener() {
						@Override
						public void onHttpResponseReceived(IHttpResponse response) {
							if (response.getStatus() == 200) {
								try {
									String idStr = Util.IO.readUtf8String(response.getInputStream());
									player.setId(idStr);

									Debug.println("register player succeeded...");
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

	private static void blockUI(String message) {
		UIBlocker.instance.block(Ichigu.getString(message));
	}

	private static void unblockUI() {
		UIBlocker.instance.unblock();
	}

	private static void updateBlockMessage(String message) {
		UIBlocker.instance.setMessage(Ichigu.getString(message));
	}

	private static class CallbackInterceptor implements ICallback {
		private final ICallback callback;

		private CallbackInterceptor(ICallback callback) {
			this.callback = callback;
		}

		@Override
		public void onSuccess() {
			unblockUI();
			callback.onSuccess();
		}

		@Override
		public void onFail(Throwable t) {
			unblockUI();
			callback.onFail(t);
		}
	}

	private static interface IAction {
		void execute();
	}
}
