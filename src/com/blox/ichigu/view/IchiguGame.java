package com.blox.ichigu.view;

import com.blox.framework.v0.forms.xml.Dialog;
import com.blox.framework.v0.impl.BaseGame;
import com.blox.framework.v0.impl.Screen;
import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.model.Toolbar;
import com.blox.ichigu.utils.R;

public class IchiguGame extends BaseGame {
	private static Toolbar toolbar;

	@Override
	public void init() {
		Dialog.activeButtonColor = R.colors.ichiguBlue;
		Dialog.closeButtonFocusColor = R.colors.ichiguRed;
		super.init();
	}

	static void activateToolbar() {
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

	@Override
	public void render() {
		if (toolbar != null)
			toolbar.draw();
		super.render();
	}
}