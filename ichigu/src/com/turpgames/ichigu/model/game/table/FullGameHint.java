package com.turpgames.ichigu.model.game.table;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.NoTipToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.model.game.mode.IchiguMode;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

class FullGameHint implements IDrawable, IEffectEndListener, Toast.IToastListener {
	class FullGameIchiguInfo {
		private final List<Card> ichiguCards;

		FullGameIchiguInfo() {
			ichiguCards = new ArrayList<Card>();
		}

		public int getIchiguCount() {
			return ichiguCards.size() / 3;
		}

		public Card getIchiguHintCard(int ichiguIndex, int cardIndex) {
			return ichiguCards.get(ichiguIndex * 3 + cardIndex);
		}
		
		public void update(List<Card> cards) {
			ichiguCards.clear();

			for (int i = 0; i < cards.size(); i++) {
				for (int j = i + 1; j < cards.size(); j++) {
					for (int k = j + 1; k < cards.size(); k++) {
						if (Card.isIchigu(cards.get(i), cards.get(j), cards.get(k))) {
							ichiguCards.add(cards.get(i));
							ichiguCards.add(cards.get(j));
							ichiguCards.add(cards.get(k));
						}
					}
				}
			}
		}
	}
	private Text hintCountText;
	private FullGameIchiguInfo ichiguInfo;
	private int hintIndex;

	private boolean isActive;
	private Toast toast;
	
	private NoTipToast noTip;

	FullGameHint(Table table) {
		noTip = new NoTipToast();
		
		hintCountText = new Text(true, false);
		hintCountText.setFontScale(0.75f);
		hintCountText.getColor().set(R.colors.ichiguYellow);
		hintCountText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		setHintCountText();
		hintCountText.setLocation(10 + IchiguMode.buttonSize * 0.8f, Game.viewportToScreenY(30) + IchiguMode.buttonSize * 0.8f);
		
		ichiguInfo = new FullGameIchiguInfo();

		toast = new Toast();
		toast.setListener(this);
		toast.setToastColor(R.colors.ichiguYellow);
		
		IchiguBank.registerListener(new IchiguBank.IIchiguBankListener() {			
			@Override
			public void update() {
				setHintCountText();			
			}
		});
	}

	@Override
	public void draw() {
		hintCountText.draw();
	}

	public int getIchiguCount() {
		return ichiguInfo.getIchiguCount();
	}

	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public boolean onEffectEnd(Object card) {
		hintEnd();
		return true;
	}

	@Override
	public void onToastHidden(Toast toast) {
		hintEnd();
	}

	public void showHint() {
		if (IchiguBank.hasHint()) {
			showNextHint();
		}
		else {
			Ichigu.playSoundError();
			noTip.show();
		}
	}

	public void update(List<Card> hintCards) {
		ichiguInfo.update(hintCards);
		toast.hide();
		hintIndex = 0;
		isActive = false;
	}

	private void hintEnd() {
		if (ichiguInfo.getIchiguCount() > 0)
			hintIndex = (hintIndex + 1) % ichiguInfo.getIchiguCount();
		isActive = false;
	}

	private void setHintCountText() {
		hintCountText.setText(IchiguBank.getHintCount() + "");
	}
	
	private void showNextHint() {
		if (isActive) {
			toast.hide();
			return;
		}

		if (ichiguInfo.getIchiguCount() == 0) {
			toast.show(Ichigu.getString(R.strings.noIchigu), 3f);
		}
		else {
			ichiguInfo.getIchiguHintCard(hintIndex, 1).blink(this, false);
			ichiguInfo.getIchiguHintCard(hintIndex, 0).blink(this, false);
			ichiguInfo.getIchiguHintCard(hintIndex, 2).blink(this, false);
			//TODO BEFORE PRODUCTION: uncomment
//			IchiguBank.decreaseHintCount();
//			IchiguBank.saveData();
		}

		isActive = true;
	}
}