package com.turpgames.ichigu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.turpgames.framework.v0.impl.android.AndroidProvider;
import com.turpgames.framework.v0.impl.android.IAndroidLifecycleListener;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Game;

public class MainActivity extends AndroidApplication {
	private IAndroidLifecycleListener lifecycleListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		
		logSigningKeyHash();

		AndroidProvider prov = AndroidProvider.getInstance();
		prov.init(this, savedInstanceState);

		Game.setEnvironmentProvider(prov);

		lifecycleListener = prov.getLifecycleListener();
		lifecycleListener.onCreate(savedInstanceState);

		initialize(new GdxGame(), cfg);
	}

	protected void logSigningKeyHash() {
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.turpgames.ichigu",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.d("KeyHash:", keyHash);
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		lifecycleListener.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		lifecycleListener.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lifecycleListener.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		lifecycleListener.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		lifecycleListener.onDestroy();
		System.exit(0);
	}
}