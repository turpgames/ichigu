package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class AboutInfo implements IDrawable, ILanguageListener {
	private Text pageTitle;
	private Text version;
	private Text info1;
	private TextButton turpLink;
	private Text info2;
	private ImageButton libgdxLink;
	private TextButton rateLink;

	public AboutInfo() {
		pageTitle = new Text();
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		version = new Text();
		version.getColor().set(R.colors.ichiguCyan);
		version.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		version.setPadding(0, 200);
		version.setText("Ichigu v" + Game.getVersion().toString());

		info1 = new Text();
		info1.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		info1.setFontScale(R.fontSize.medium);
		info1.setPadding(35, 300);

		turpLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		turpLink.setFontScale(R.fontSize.medium);
		turpLink.setText("www.turpgames.com");
		turpLink.getLocation().set((Game.getVirtualWidth() - turpLink.getWidth()) / 2, Game.getVirtualHeight() - 360);
		turpLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.strings.turpAddress));
			}
		});

		info2 = new Text();
		info2.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		info2.setFontScale(R.fontSize.medium);
		info2.setPadding(35, 450);

		libgdxLink = new ImageButton(Game.scale(R.ui.libgdxLogoWidth), Game.scale(R.ui.libgdxLogoHeight), R.game.textures.libgdx);
		libgdxLink.getLocation().set((Game.getScreenWidth() - libgdxLink.getWidth()) / 2, Game.viewportToScreenY(Game.getVirtualHeight() - 520));
		libgdxLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.strings.libgdxAddress));
			}
		});
		libgdxLink.deactivate();

		rateLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		rateLink.setFontScale(R.fontSize.medium);
		rateLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (Game.isIOS()) {
					if (Game.getOSVersion().getMajor() < 7)
						Game.openUrl(Game.getParam(R.strings.appStoreAddressOld));
					else
						Game.openUrl(Game.getParam(R.strings.appStoreAddressIOS7));
				}
				else {
					Game.openUrl(Game.getParam(R.strings.playStoreAddress));
				}
			}
		});

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
		rateLink.draw();
	}

	public void activate() {
		turpLink.activate();
		libgdxLink.activate();
		rateLink.activate();
	}

	public void deactivate() {
		turpLink.deactivate();
		libgdxLink.deactivate();
		rateLink.deactivate();
	}

	private void setLanguageSensitiveInfo() {
		info1.setText(Ichigu.getString(R.strings.aboutInfo1));
		info2.setText(Ichigu.getString(R.strings.aboutInfo2));

		pageTitle.setText(Ichigu.getString(R.strings.about));
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);

		rateLink.setText(Ichigu.getString(R.strings.aboutInfo3));
		rateLink.getLocation().set((Game.getVirtualWidth() - rateLink.getWidth()) / 2, Game.getVirtualHeight() - 620);
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}