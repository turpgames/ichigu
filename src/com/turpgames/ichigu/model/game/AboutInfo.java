package com.turpgames.ichigu.model.game;

import com.badlogic.gdx.Gdx;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class AboutInfo implements IDrawable, ILanguageListener {
	private GameInfo pageTitle;
	private GameInfo version;
	private GameInfo info1;
	private TextButton turpLink;
	private GameInfo info2;
	private TextButton libgdxLink;
	private GameInfo info3;

	public AboutInfo() {
		pageTitle = new GameInfo();
		pageTitle.setColor(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		version = new GameInfo();
		version.setColor(R.colors.ichiguCyan);
		version.locate(Text.HAlignCenter, Text.VAlignTop);
		version.setPadding(0, 200);
		version.setText("Ichigu v1.1");
		
		info1 = new GameInfo();
		info1.locate(Text.HAlignCenter, Text.VAlignTop);
		info1.setFontScale(R.fontSize.medium);
		info1.setPadding(35, 300);
		
		turpLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		turpLink.setFontScale(R.fontSize.medium);
		turpLink.setText(Game.getParam(R.strings.turpAddress));
		turpLink.getLocation().set((Game.getVirtualWidth() - turpLink.getWidth()) / 2, Game.getVirtualHeight() - 380);
		turpLink.listenInput(true);
		turpLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Gdx.net.openURI(Game.getParam(R.strings.turpAddress));
			}
		});
		
		info2 = new GameInfo();
		info2.locate(Text.HAlignCenter, Text.VAlignTop);
		info2.setFontScale(R.fontSize.medium);
		info2.setPadding(35, 500);

		libgdxLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		libgdxLink.setFontScale(R.fontSize.medium);
		libgdxLink.setText(Game.getParam(R.strings.libgdxAddress));
		libgdxLink.getLocation().set((Game.getVirtualWidth() - libgdxLink.getWidth()) / 2, Game.getVirtualHeight() - 620);
		libgdxLink.listenInput(true);
		libgdxLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Gdx.net.openURI(Game.getParam(R.strings.libgdxAddress));
			}
		});
		
		info3 = new GameInfo();
		info3.locate(Text.HAlignCenter, Text.VAlignTop);
		info3.setFontScale(R.fontSize.medium);
		info3.setPadding(35, 700);
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	@Override
	public void draw() {
		pageTitle.draw();
		version.draw();
		info1.draw();
		turpLink.draw();
		info2.draw();
		libgdxLink.draw();
		info3.draw();
	}

	private void setLanguageSensitiveInfo() {
		info1.setText(Game.getLanguageManager().getString(R.strings.aboutInfo1));
		info2.setText(Game.getLanguageManager().getString(R.strings.aboutInfo2));
		info3.setText(Game.getLanguageManager().getString(R.strings.aboutInfo3));
		pageTitle.setText(Game.getLanguageManager().getString(R.strings.about));
		pageTitle.locate(Text.HAlignCenter, Text.VAlignTop);
	}
	
	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}