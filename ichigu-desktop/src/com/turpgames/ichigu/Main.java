package com.turpgames.ichigu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.IEnvironmentProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.ISocializerFactory;
import com.turpgames.framework.v0.social.Player;
import com.turpgames.framework.v0.social.SocialFeed;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Version;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ichigu";
		cfg.useGL20 = true;

		float w = 9f;
		float h = 16f;
		float x = 35f;

		cfg.width = (int) (x * w);
		cfg.height = (int) (x * h);

		Game.setEnvironmentProvider(new IEnvironmentProvider() {
			@Override
			public Version getAppVersion() {
				return new Version("1.2.0");
			}

			@Override
			public Version getOsVersion() {
				return new Version(System.getProperty("os.version"));
			}

			@Override
			public ISocializerFactory createSocializerFactory() {
				return new ISocializerFactory() {
					@Override
					public ISocializer getSocializer(String provider) {
						return new ISocializer() {

							private boolean isLoggedin = false;
							private Player player;

							@Override
							public void postFeed(SocialFeed feed, ICallback callback) {
								callback.onSuccess();
							}

							@Override
							public void logout(ICallback callback) {
								isLoggedin = false;
								player = null;
								callback.onSuccess();
							}

							@Override
							public void login(ICallback callback) {
								isLoggedin = true;

								player = new Player();
								player.setName("Seda Çetinkaya");
								player.setSocialId("718801914");
								player.setEmail("player@test.com");

								callback.onSuccess();
							}

							@Override
							public boolean isLoggedIn() {
								return isLoggedin;
							}

							@Override
							public Player getPlayer() {
								return player;
							}

							@Override
							public void loadFriends(Player player, ICallback callback) {
								player.setFriends(new Player[0]);
								callback.onSuccess();
							}
						};
					}
				};
			}
		});

		new LwjglApplication(new GdxGame(), cfg);
	}
}
