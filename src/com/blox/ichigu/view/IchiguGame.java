package com.blox.ichigu.view;

import com.blox.framework.v0.forms.xml.Dialog;
import com.blox.framework.v0.impl.BaseGame;
import com.blox.framework.v0.impl.Screen;
import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.model.Toolbar;
import com.blox.ichigu.utils.R;

public class IchiguGame extends BaseGame {
	public static Toolbar toolbar;

	@Override
	public void init() {
		super.init();
		Dialog.activeButtonColor.set(R.colors.ichiguYellow);
		Dialog.closeButtonFocusColor.set(R.colors.ichiguRed);
	}

	public static Toolbar getToolbar() {
		if (toolbar == null) {
			Toolbar.init();
			toolbar = Toolbar.getInstance();
			toolbar.setListener(new Toolbar.IToolbarListener() {
				@Override
				public void onToolbarBack() {
					Screen screen = ScreenManager.instance.getCurrentScreen();
					if (screen instanceof IchiguScreen)
						((IchiguScreen) screen).back();
				}
			});
		}
		return toolbar;
	}

	@Override
	public void draw() {
		super.draw();
	}
}