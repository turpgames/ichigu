package com.turpgames.ichigu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.IEnvironmentProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.social.ISocializerFactory;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Version;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ichigu";
		cfg.useGL20 = true;

		float w = 11f;
		float h = 16f;
		float x = 40f;
		
		cfg.width = (int) (x * w);
		cfg.height = (int) (x * h);
		
		Game.setEnvironmentProvider(new IEnvironmentProvider() {			
			@Override
			public Version getVersion() {
				return new Version("1.1");
			}

			@Override
			public ISocializerFactory createSocializerFactory() {
				// TODO Auto-generated method stub
				return null;
			}		
		});

		new LwjglApplication(new GdxGame(), cfg);
	}
}
