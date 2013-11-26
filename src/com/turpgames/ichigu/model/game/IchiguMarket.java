package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class IchiguMarket implements IDrawable, ILanguageListener {
	private Text pageTitle;
	private Text marketInfo;
	private Text bankInfo;
	private TextButton buyButton;
	private Dialog dialog;

	public IchiguMarket() {
		pageTitle = new Text();
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		marketInfo = new Text();
		marketInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		marketInfo.setFontScale(R.fontSize.medium);
		marketInfo.setPadding(30, 250);

		bankInfo = new Text();
		bankInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		bankInfo.setFontScale(R.fontSize.medium);
		bankInfo.setPadding(0, 400);

		buyButton = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		buyButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onBuyButtonTapped();
			}
		});

		dialog = new Dialog();
		dialog.addButton("ok", R.strings.ok);
		
		Game.getLanguageManager().register(this);
	}

	private void onBuyButtonTapped() {
		if (IchiguBank.buyHintWithBalance())
			setBankInfoText();
		else
			dialog.open(Ichigu.getString(R.strings.insufficientIchiguBalance));
	}

	@Override
	public void draw() {
		pageTitle.draw();
		marketInfo.draw();
		bankInfo.draw();
		buyButton.draw();
	}

	public void activate() {
		setLanguageSensitiveInfo();
		buyButton.activate();
	}

	public void deactivate() {
		buyButton.deactivate();
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.market));

		buyButton.setText(Ichigu.getString(R.strings.buy));
		buyButton.getLocation().set((Game.getVirtualWidth() - buyButton.getWidth()) / 2, 100);
		
		marketInfo.setText(Ichigu.getString(R.strings.marketInfo));
		
		setBankInfoText();
	}

	protected void setBankInfoText() {
		String bankInfoText = String.format(Ichigu.getString(R.strings.bankInfo), 
				IchiguBank.getBalance(), 
				IchiguBank.getHintCount());
		
		bankInfo.setText(bankInfoText);
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}