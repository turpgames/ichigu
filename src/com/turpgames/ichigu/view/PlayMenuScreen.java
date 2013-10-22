package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.ichigu.model.Logo;
import com.turpgames.ichigu.model.Toolbar;
import com.turpgames.ichigu.model.Toolbar.IToolbarListener;
import com.turpgames.ichigu.utils.R;

public class PlayMenuScreen extends FormScreen implements IToolbarListener {
	@Override
	public void init() {
		super.init();
		
		Logo logo = new Logo();
		logo.getColor().a = 0.25f;
		registerDrawable(logo, 1);
		Toolbar toolbar = IchiguGame.getToolbar();
		toolbar.setListener(this);
		registerDrawable(toolbar, 1);
		
		setForm(R.game.forms.playMenu, false);
	}
	
	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		Toolbar.getInstance().activateBackButton();
	}

	@Override
	public void onToolbarBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
	}
}
