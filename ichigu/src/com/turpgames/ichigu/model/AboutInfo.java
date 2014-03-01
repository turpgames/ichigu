package com.turpgames.ichigu.model;

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

	private Text version;
	private ImageButton turpLink;
	private TextButton facebookLink;
	private TextButton twitterLink;
	private TextButton rateLink;
	private Text thanksInfo;

	public AboutInfo() {
		version = new Text();
		version.getColor().set(R.colors.ichiguYellow);
		version.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		version.setPadding(0, 85);
		version.setFontScale(1.5f);
		version.setText("Ichigu v" + Game.getVersion().toString());

		float size = Game.scale(R.sizes.logoSize);
		turpLink = new ImageButton(size, size, R.game.textures.splashLogo);
		turpLink.getLocation().set(
				(Game.getScreenWidth() - size) / 2,
				size + (Game.getScreenHeight() - size) / 2);
		turpLink.deactivate();
		turpLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.game.params.turpAddress));
			}
		});

		facebookLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		facebookLink.setFontScale(R.fontSize.medium);
		facebookLink.setText("turpgames@facebook");
		facebookLink.getLocation().set((Game.getVirtualWidth() - facebookLink.getWidth()) / 2, Game.getVirtualHeight() - 420);
		facebookLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.game.params.facebookAddress));
			}
		});

		twitterLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		twitterLink.setFontScale(R.fontSize.medium);
		twitterLink.setText("turpgames@twitter");
		twitterLink.getLocation().set((Game.getVirtualWidth() - twitterLink.getWidth()) / 2, Game.getVirtualHeight() - 480);
		twitterLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Game.getParam(R.game.params.twitterAddress));
			}
		});

		rateLink = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		rateLink.setFontScale(R.fontSize.medium);
		rateLink.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Game.openUrl(Ichigu.getStoreUrl());
			}
		});

		thanksInfo = new Text();
		thanksInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		thanksInfo.setFontScale(R.fontSize.medium);
		thanksInfo.setPadding(35, 680);

		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	public void activate() {
		turpLink.activate();
		facebookLink.activate();
		twitterLink.activate();
		rateLink.activate();
	}

	public void deactivate() {
		turpLink.deactivate();
		facebookLink.deactivate();
		twitterLink.deactivate();
		rateLink.deactivate();
	}

	@Override
	public void draw() {
		version.draw();
		turpLink.draw();
		facebookLink.draw();
		twitterLink.draw();
		rateLink.draw();
		thanksInfo.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	private void setLanguageSensitiveInfo() {
		rateLink.setText(Ichigu.getString(R.strings.aboutInfo3));
		rateLink.getLocation().set((Game.getVirtualWidth() - rateLink.getWidth()) / 2, Game.getVirtualHeight() - 570);

		thanksInfo.setText(Game.getLanguageManager().getString(R.strings.aboutThanks));
	}
}