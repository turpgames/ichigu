package com.turpgames.ichigu;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.turpgames.framework.v0.android.AndroidGameActivity;

public class MainActivity extends AndroidGameActivity {
	@Override
	protected AndroidApplicationConfiguration getAndroidApplicationConfiguration() {
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		return cfg;
	}
}