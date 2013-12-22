package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class IchiguMarket implements IDrawable, ILanguageListener {
	private Text pageTitle;
	private Text priceInfo;
	private Text balanceInfo;
	private TextButton btnSingleHint;
	private TextButton btnTripleHint;
	private TextButton btnTimerPause;
	private TextButton btnBuy;
	private Dialog dialog;

	private IchiguBonusFeature currentFeature = IchiguBonusFeature.singleHint;

	public IchiguMarket() {
		pageTitle = new Text();
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);
		
		priceInfo = new Text();
		priceInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		priceInfo.setFontScale(R.fontSize.medium);
		priceInfo.setPadding(30, 325);

		balanceInfo = new Text();
		balanceInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		balanceInfo.setFontScale(R.fontSize.medium);
		balanceInfo.setPadding(0, 425);

		btnSingleHint = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		btnSingleHint.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setCurrentFeature(IchiguBonusFeature.singleHint);

				setActive(btnSingleHint, true);
				setActive(btnTripleHint, false);
				setActive(btnTimerPause, false);
			}
		});

		btnTripleHint = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		btnTripleHint.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setCurrentFeature(IchiguBonusFeature.tripleHint);

				setActive(btnSingleHint, false);
				setActive(btnTripleHint, true);
				setActive(btnTimerPause, false);
			}
		});

		btnTimerPause = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		btnTimerPause.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				setCurrentFeature(IchiguBonusFeature.timerPause);

				setActive(btnSingleHint, false);
				setActive(btnTripleHint, false);
				setActive(btnTimerPause, true);
			}
		});

		btnBuy = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		btnBuy.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				buy();
			}
		});
		
		dialog = new Dialog();
		dialog.addButton("ok", R.strings.ok);

		Game.getLanguageManager().register(this);
	}

	private void setCurrentFeature(IchiguBonusFeature feature) {
		currentFeature = feature;
		setLanguageSensitiveInfo();
		IchiguToast.showInfo(feature.getInfo());
	}

	private void setActive(TextButton btn, boolean isActive) {
		if (isActive) {
			btn.getColor().a = 1f;
			btn.setFontScale(R.fontSize.medium);
		} else {
			btn.getColor().a = 0.5f;
			btn.setFontScale(R.fontSize.xSmall);
		}
		ensureButtonLocations();
	}

	public void activate() {
		setLanguageSensitiveInfo();
		btnSingleHint.activate();
		btnTripleHint.activate();
		btnTimerPause.activate();
		btnBuy.activate();

		setActive(btnSingleHint, false);
		setActive(btnTripleHint, true);
		setActive(btnTimerPause, false);
		
		setCurrentFeature(IchiguBonusFeature.tripleHint);
	}

	public void deactivate() {
		btnSingleHint.deactivate();
		btnTripleHint.deactivate();
		btnTimerPause.deactivate();
		btnBuy.deactivate();
	}

	@Override
	public void draw() {
		pageTitle.draw();
		priceInfo.draw();
		balanceInfo.draw();
		btnSingleHint.draw();
		btnTripleHint.draw();
		btnTimerPause.draw();
		btnBuy.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	private void buy() {
		if (IchiguBank.buy(currentFeature))
			setBankInfoText();
		else
			dialog.open(String.format(
					Ichigu.getString(R.strings.insufficientIchiguBalance), 
					Ichigu.getString(currentFeature.getName())));
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.market));
		
		btnSingleHint.setText(Ichigu.getString(R.strings.singleHint));
		btnTripleHint.setText(Ichigu.getString(R.strings.tripleHint));
		btnTimerPause.setText(Ichigu.getString(R.strings.pauseTimer));
		btnBuy.setText(Ichigu.getString(R.strings.buy));

		priceInfo.setText(String.format(
				Ichigu.getString(R.strings.marketPriceInfo),
				currentFeature.getPrice()));

		setBankInfoText();
		ensureButtonLocations();
	}

	private void setBankInfoText() {
		balanceInfo.setText(String.format(
				Ichigu.getString(R.strings.marketFeatureStatus),
				IchiguBank.getBalance(),
				currentFeature.getCount()));
	}

	private void ensureButtonLocations() {
		btnSingleHint.getLocation().set(20, Game.getVirtualHeight() - 250);
		btnTripleHint.getLocation().set((Game.getVirtualWidth() - btnTripleHint.getWidth()) / 2, Game.getVirtualHeight() - 250);
		btnTimerPause.getLocation().set((Game.getVirtualWidth() - btnTimerPause.getWidth() - 20), Game.getVirtualHeight() - 250);
		btnBuy.getLocation().set((Game.getVirtualWidth() - btnBuy.getWidth()) / 2, 100);
	}
}