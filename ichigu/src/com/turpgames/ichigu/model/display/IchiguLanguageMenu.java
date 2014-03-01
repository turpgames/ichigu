package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.ichigu.utils.R;

public class IchiguLanguageMenu extends LanguageMenu {

	public IchiguLanguageMenu() {
		super(R.sizes.menuButtonSpacing, R.sizes.langFlagButtonSizeToScreen, R.sizes.langFlagButtonSizeToScreen, 2);
	}

	@Override
	protected void concreteAddControlButton() {
		controlButton = new ImageButton(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, Color.white(), Color.white());
		controlButton.setLocation(Button.AlignSW, 0, 0);
	}
}
