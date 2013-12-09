package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.TurpLogo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class AboutInfo implements IDrawable, ILanguageListener {
	private Text version;
	private TurpLogo logo;
	private TextButton turpLink;
	private TextButton facebookLink;
	private TextButton twitterLink;
	private Text info2;
	private ImageButton libgdxLink;
	private TextButton rateLink;
	private Text thanksInfo;

	public AboutInfo() {
		version = new Text();
		version.getColor().set(R.colors.ichiguRed);
		version.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		version.setPadding(0, 85);
		version.setText("Ichigu v" + Game.getVersion().toString());

		logo = new TurpLogo();
		float logoSize = 140f;
		logo.setLogoSize(logoSize);
		logo.getLocation().set((Game.getVirtualWidth() - logoSize) / 2, 170f + (Game.getVirtualHeight() - logoSize) / 2);
		//35, 300

		turpLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		turpLink.setFontScale(R.fontSize.medium);
		turpLink.setText("www.turpgames.com");
		turpLink.getLocation().set((Game.getVirtualWidth() - turpLink.getWidth()) / 2, Game.getVirtualHeight() - 360);
		turpLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.links.turpAddress));
			}
		});
		
		facebookLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		facebookLink.setFontScale(R.fontSize.medium);
		facebookLink.setText("turpgames@facebook");
		facebookLink.getLocation().set((Game.getVirtualWidth() - facebookLink.getWidth()) / 2, Game.getVirtualHeight() - 420);
		facebookLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.links.facebookAddress));
			}
		});

		twitterLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		twitterLink.setFontScale(R.fontSize.medium);
		twitterLink.setText("turpgames@twitter");
		twitterLink.getLocation().set((Game.getVirtualWidth() - twitterLink.getWidth()) / 2, Game.getVirtualHeight() - 480);
		twitterLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.links.twitterAddress));
			}
		});
		
		info2 = new Text();
		info2.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		info2.setFontScale(R.fontSize.medium);
		info2.setPadding(35, 620);

		libgdxLink = new ImageButton(Game.scale(R.ui.libgdxLogoWidth), Game.scale(R.ui.libgdxLogoHeight), R.game.textures.libgdx);
		libgdxLink.getLocation().set((Game.getScreenWidth() - libgdxLink.getWidth()) / 2, Game.viewportToScreenY(Game.getVirtualHeight() - 680));
		libgdxLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.links.libgdxAddress));
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
						Game.openUrl(Game.getParam(R.links.appStoreAddressOld));
					else
						Game.openUrl(Game.getParam(R.links.appStoreAddressIOS7));
				}
				else {
					Game.openUrl(Game.getParam(R.links.playStoreAddress));
				}
			}
		});

		thanksInfo = new Text();
		thanksInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		thanksInfo.setFontScale(R.fontSize.medium);
		thanksInfo.setPadding(35, 680);
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	@Override
	public void draw() {
		version.draw();
		logo.draw();
		turpLink.draw();
		facebookLink.draw();
		twitterLink.draw();
		info2.draw();
		libgdxLink.draw();
		rateLink.draw();
		thanksInfo.draw();
	}

	public void activate() {
		turpLink.activate();
		facebookLink.activate();
		twitterLink.activate();
		libgdxLink.activate();
		rateLink.activate();
	}

	public void deactivate() {
		turpLink.deactivate();
		facebookLink.deactivate();
		twitterLink.deactivate();
		libgdxLink.deactivate();
		rateLink.deactivate();
	}

	private void setLanguageSensitiveInfo() {
		info2.setText(Ichigu.getString(R.strings.aboutInfo2));

//		pageTitle.setText(Ichigu.getString(R.strings.about));
//		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);

		rateLink.setText(Ichigu.getString(R.strings.aboutInfo3));
		rateLink.getLocation().set((Game.getVirtualWidth() - rateLink.getWidth()) / 2, Game.getVirtualHeight() - 570);
		
		thanksInfo.setText(Game.getLanguageManager().getString(R.strings.aboutThanks));
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}