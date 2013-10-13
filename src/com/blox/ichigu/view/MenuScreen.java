package com.blox.ichigu.view;

import com.blox.framework.v0.impl.FormScreen;
import com.blox.ichigu.model.Logo;
import com.blox.ichigu.model.Toolbar;
import com.blox.ichigu.utils.R;

public class MenuScreen extends FormScreen {
	@Override
	public void init() {
		super.init();

		Logo logo = new Logo();
		logo.getColor().a = 0.25f;
		registerDrawable(logo, 1);

		setForm(R.game.forms.mainMenu, false);
	}
	
	@Override
	public void activated() {
		super.activated();
		Toolbar.getInstance().deactivateBackButton();
	}
}