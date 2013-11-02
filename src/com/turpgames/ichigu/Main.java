package com.turpgames.ichigu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ichigu";
		cfg.useGL20 = true;

		float w = 11f;
		float h = 16f;
		float x = 50f;

		cfg.width = (int) (x * w);
		cfg.height = (int) (x * h);

		new LwjglApplication(new GdxGame(), cfg);
	}
}
