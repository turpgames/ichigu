package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.utils.Util;

public class Facebook {
	private static ISocializer facebook;
	private static ITexture defaultProfilePicture;

	static {
		facebook = Game.getSocializer("facebook");
	}

	public static ITexture getDefaultProfilePicture() {
		if (defaultProfilePicture == null) {
			defaultProfilePicture = Game.getResourceManager().getTexture(R.game.textures.fb_default);
		}
		return defaultProfilePicture;
	}

	public static class Session {

	}

	public static class ScoreSharer {

	}

	private static String getPlayerId() {
		return Settings.getString("player-id", "");
	}

	private static String getFacebookId() {
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
		Ichigu.blockUI(R.strings.loggingIn);
		facebook.login(new ICallback() {
			@Override
			public void onSuccess() {
				onLoginSuccess(new ICallback() {
					@Override
					public void onSuccess() {
						Ichigu.unblockUI();
						IchiguToast.showInfo(R.strings.loginSuccess);
						callback.onSuccess();
					}

					@Override
					public void onFail(Throwable t) {
						Ichigu.unblockUI();
						IchiguToast.showError(R.strings.loginFail);
						callback.onFail(t);
					}
				});
			}

			@Override
			public void onFail(Throwable t) {
				Ichigu.unblockUI();
				IchiguToast.showError(R.strings.loginFail);
				callback.onFail(t);
			}
		});
	}

	public static void logout(final ICallback callback) {
		facebook.logout(new ICallback() {
			@Override
			public void onSuccess() {
				Settings.putString("player-facebook-id", "");
				Settings.putString("player-id", "");
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				callback.onFail(t);
			}
		});
	}
	
	public static void shareScore(final int mode, final int score, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			shareScoreImpl(mode, score, callback);
		}
		else {
			Facebook.login(new ICallback() {				
				@Override
				public void onSuccess() {
					doShareScore(mode, score, callback);
				}
				
				@Override
				public void onFail(Throwable t) {
					callback.onFail(t);
				}
			});
		}
	}

	private static void doShareScore(final int mode, final int score, final ICallback callback) {
		Ichigu.blockUI(R.strings.sharingScore);
		shareScoreImpl(mode, score, new ICallback() {
			@Override
			public void onSuccess() {
				Ichigu.unblockUI();
				IchiguToast.showInfo(R.strings.shareScoreSuccess);
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				Ichigu.unblockUI();
				IchiguToast.showError(R.strings.shareScoreFail);
				callback.onFail(t);
			}
		});
	}

	private static void shareScoreImpl(int mode, int score, ICallback callback) {
		try {
			SocialFeed scoreFeed = SocialFeed.newBuilder()
					.setTitle(prepareScoreMessage(mode, score))
					.setSubtitle("turpgames")
					.setMessage("Ichigu")
					.setHref("http://www.turpgames.com/ichiguredirect.html")
					.setImageUrl("http://www.turpgames.com/res/img/ichigu_logo.png")
					.build();
			facebook.postFeed(scoreFeed, callback);
		} catch (Throwable t) {
			callback.onFail(t);
		}
	}

	public static void loadFriendList(ICallback callback) {
			facebook.loadFriends(getPlayer(), callback);
	}

	private static String prepareScoreMessage(int mode, int score) {
		if (mode == Score.ModeMini) {
			return String.format("%s just found %d ichigu%s in Mini Mode", getPlayer().getName().split(" ")[0], score, score > 1 ? "s" : "");
		}
		else if (mode == Score.ModeStandard) {
			return String.format("%s completed Standard Mode in %s", getPlayer().getName().split(" ")[0], Util.Strings.getTimeString(score));
		}
		else if (mode == Score.ModeTime) {
			return String.format("%s just found %d ichigu%s in Time Challenge Mode", getPlayer().getName().split(" ")[0], score, score > 1 ? "s" : "");
		}
		return "";
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
		IchiguClient.registerPlayer(new ICallback() {
			@Override
			public void onSuccess() {
				ScoreManager.instance.sendScores();
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {

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
}
