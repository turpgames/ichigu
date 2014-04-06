package com.turpgames.ichigu;

import org.robovm.bindings.facebook.manager.FacebookManager;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.turpgames.framework.v0.impl.ios.IOSProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Game;

public class RobovmLauncher extends IOSApplication.Delegate {
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = false;
		config.orientationPortrait = true;
		
		Game.setEnvironmentProvider(new IOSProvider());
		
		return new IOSApplication(new GdxGame(), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.close();
	}
	
	@Override
	public boolean openURL (UIApplication application, NSURL url, String sourceApplication, NSObject annotation) {
		return FacebookManager.getInstance().handleOpenUrl(url, sourceApplication);
	}
}
