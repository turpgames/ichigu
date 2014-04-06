package com.turpgames.ichigu.utils;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.IFacebookConnector;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.social.SocialUser;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.entity.Player;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.utils.Util;

public class Facebook {
	private static IFacebookConnector facebook;
	private static ITexture defaultProfilePicture;
	private static Player player;

	static {
		facebook = Game.getFacebookConnector();
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

	public static Player getPlayer() {
		if (player == null && isLoggedIn()) {
			SocialUser user = getUser();

			player = new Player();

			player.setEmail(user.getEmail());
			player.setFacebookId(user.getSocialId());
			player.setUsername(user.getName());
		}

		return player;
	}

	private static String getFacebookId() {
		return Settings.getString("player-facebook-id", "");
	}

	public static boolean canLogin() {
		return !"".equals(Settings.getString("player-facebook-id", ""));
	}

	public static SocialUser getUser() {
		return facebook.getUser();
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
						IchiguSettings.setAsFacebookAnnounced();
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
		Ichigu.blockUI(R.strings.loggingOut);
		facebook.logout(new ICallback() {
			@Override
			public void onSuccess() {
				Settings.putString("player-facebook-id", "");
				Settings.putString("player-id", "");

				Ichigu.unblockUI();
				IchiguToast.showInfo(R.strings.logoutSuccess);

				player = null;
				
				callback.onSuccess();
			}

			@Override
			public void onFail(Throwable t) {
				Ichigu.unblockUI();
				IchiguToast.showInfo(R.strings.logoutFail);

				callback.onFail(t);
			}
		});
	}

	public static void shareScore(final int mode, final int score, final ICallback callback) {
		if (Facebook.isLoggedIn()) {
			doShareScore(mode, score, callback);
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
		if (getUser().getFriends() == null)
			facebook.loadFriends(callback);
		else
			callback.onSuccess();
	}

	private static String prepareScoreMessage(int mode, int score) {
		if (mode == Score.ModeMini) {
			return String.format("%s just found %d ichigu%s in Mini Mode", getUser().getName().split(" ")[0], score, score > 1 ? "s" : "");
		}
		else if (mode == Score.ModeStandard) {
			return String.format("%s completed Standard Mode in %s", getUser().getName().split(" ")[0], Util.Strings.getTimeString(score));
		}
		else if (mode == Score.ModeTime) {
			return String.format("%s just found %d ichigu%s in Time Challenge Mode", getUser().getName().split(" ")[0], score, score > 1 ? "s" : "");
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
		final SocialUser user = getUser();

		if (user == null) {
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

		if (getFacebookId().equals(user.getSocialId())) {
			Debug.println("player already registered");
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
