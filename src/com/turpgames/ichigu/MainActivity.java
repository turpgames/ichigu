package com.turpgames.ichigu;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		
		// cfg.numSamples = 2;
		
		initialize(new GdxGame(), cfg);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	System.exit(0);
    }
}