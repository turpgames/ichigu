package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class AboutInfo implements IDrawable, ILanguageListener {
	private GameInfo pageTitle;
	private GameInfo info;

	public AboutInfo() {
		pageTitle = new GameInfo();
		pageTitle.setColor(R.colors.ichiguBlue);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 25);
		
		info = new GameInfo();
		info.locate(Text.HAlignCenter, Text.VAlignCenter);
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	@Override
	public void draw() {
		pageTitle.draw();
		info.draw();
	}

	private void setLanguageSensitiveInfo() {
		info.setText(Game.getLanguageManager().getString(R.strings.aboutInfo));
		pageTitle.setText(Game.getLanguageManager().getString(R.strings.about));
		pageTitle.locate(Text.HAlignCenter, Text.VAlignTop);
	}
	
	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}