package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class IchiguLanguageMenu extends LanguageMenu {
	private static final float controlButtonSize = Game.scale(R.ui.flagControlButtonWidth);
	private static final float flagButtonSize = Game.scale(R.ui.flagButtonWidth);
	private static final float buttonSpacing = Game.scale(30);

	public IchiguLanguageMenu() {
		super(buttonSpacing, flagButtonSize, flagButtonSize);
	}

	@Override
	protected void concreteAddControlButton() {
		controlButton = new ImageButton(controlButtonSize, controlButtonSize, Color.white(), Color.white());
		controlButton.setLocation(Button.AlignSW, 0, 0);
	}

}
