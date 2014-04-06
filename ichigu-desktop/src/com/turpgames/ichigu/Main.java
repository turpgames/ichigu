package com.turpgames.ichigu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.impl.desktop.DesktopEnvironmentProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Game;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ichigu";

		float w = 11f;
		float h = 16f;
		float x = 40;

		cfg.width = (int) (x * w);
		cfg.height = (int) (x * h);

		Game.setEnvironmentProvider(new DesktopEnvironmentProvider());

		new LwjglApplication(new GdxGame(), cfg);
	}
}
