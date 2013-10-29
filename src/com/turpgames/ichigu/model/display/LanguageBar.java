package com.turpgames.ichigu.model.display;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.metadata.GameMetadata;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class LanguageBar extends GameObject {
	private static final float buttonSpacing = Game.scale(10);
	private static final float toolbarMargin = Game.scale(15);
	private static final float buttonWidth = Game.scale(R.ui.flagButtonWidth);
	
	private List<LanguageButton> buttons;
	
	protected LanguageBar() {
		buttons = new ArrayList<LanguageButton>();
	}

	public static LanguageBar load() {
		Object[] languages = GameMetadata.getLanguages().toArray();
		LanguageBar bar = new LanguageBar();
		LanguageButton btn;
		
		for(int i = 0; i < languages.length; i++) {
			btn = LanguageButton.load((String) languages[i]);
			btn.setLocation(ToolbarButton.AlignNW, toolbarMargin + i * (buttonWidth + buttonSpacing), toolbarMargin);
			bar.buttons.add(btn);
		}
		return bar;
	}
	
	public void activate() {
		for(LanguageButton btn : buttons)
			btn.activate();
	}
	
	public void deactivate() {
		for(LanguageButton btn : buttons)
			btn.deactivate();
	}
	
	@Override
	public void draw() {
		for(LanguageButton btn : buttons)
			btn.draw();
	}

	@Override
	public void registerSelf() {
		for(LanguageButton btn : buttons)
			btn.registerSelf();
	}
}
