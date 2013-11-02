package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class IchiguLanguageMenu extends LanguageMenu {

	public IchiguLanguageMenu() {
		super(Game.scale(30), R.ui.flagButtonWidth, R.ui.flagButtonHeight, R.ui.flagControlButtonWidth, R.ui.flagControlButtonHeight);
	}

	@Override
	protected void concreteAddControlButton() {
		controlButton = new ImageButton(R.ui.flagControlButtonWidth, R.ui.flagControlButtonHeight, Color.white(), Color.white());
		controlButton.setLocation(Button.AlignSW, Game.scale(10), Game.scale(10));
	}

}
