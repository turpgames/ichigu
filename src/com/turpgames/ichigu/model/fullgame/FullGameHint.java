package com.turpgames.ichigu.model.fullgame;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.BlinkingImageButton;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IchiguBank;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class FullGameHint implements IDrawable, IEffectEndListener, Toast.IToastListener {
	private final static int notificationInterval = 30;
	private static final float buttonSize = Game.scale(R.ui.imageButtonWidth);

	private BlinkingImageButton button;
	private Text hintCountText;
	private FullGameIchiguInfo ichiguInfo;
	private Card[] cards;
	private int hintIndex;
	private boolean isActive;
	private Timer notificationTimer;

	private Toast toast;

	private IHintListener hintListener;

	FullGameHint() {
		button = new BlinkingImageButton(buttonSize, buttonSize, R.colors.ichiguWhite, R.colors.ichiguYellow, 1f, 10);
		button.setTexture(R.game.textures.hint);
		button.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onHintButtonTapped();
			}
		});

		hintCountText = new Text(true, false);
		hintCountText.setFontScale(0.75f);
		hintCountText.getColor().set(R.colors.ichiguYellow);
		hintCountText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		hintCountText.setText("(" + IchiguBank.getHintCount() + ")");

		ichiguInfo = new FullGameIchiguInfo();

		notificationTimer = new Timer();
		notificationTimer.setInterval(notificationInterval);
		notificationTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				button.blink();
			}
		});

		toast = new Toast();
		toast.setListener(this);
		toast.setToastColor(R.colors.ichiguYellow);
	}

	private void onHintButtonTapped() {
		if (IchiguBank.hasHint()) {
			showNextHint();
			restartNotificationTimer();
		}
		else {
			hintListener.onInsufficientHint();
		}
	}

	public void setLocation(float x, float y) {
		button.getLocation().set(x, y);
		hintCountText.setLocation(x + buttonSize * 0.8f, y + buttonSize * 0.8f);
	}

	public float getWidth() {
		return button.getWidth();
	}

	public float getHeight() {
		return button.getHeight();
	}

	public void restartNotificationTimer() {
		notificationTimer.restart();
	}

	public void update(Card[] cards) {
		ichiguInfo.update(cards);
		toast.hide();
		hintIndex = 0;
		isActive = false;
		this.cards = cards;
	}

	public int getIchiguCount() {
		return ichiguInfo.getIchiguCount();
	}

	public void setHintListener(IHintListener hintListener) {
		this.hintListener = hintListener;
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
			int cardIndex = ichiguInfo.getIchiguCardIndex(hintIndex, 1);
			cards[cardIndex].blink(this, false);

			IchiguBank.decreaseHintCount();
			IchiguBank.saveData();
			updateText();
		}

		isActive = true;
	}

	private void hintEnd() {
		if (ichiguInfo.getIchiguCount() > 0)
			hintIndex = (hintIndex + 1) % ichiguInfo.getIchiguCount();
		isActive = false;
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

	@Override
	public void draw() {
		drawButton();
		drawHintCount();
	}

	private void drawButton() {
		button.draw();
	}

	private void drawHintCount() {
		hintCountText.draw();
	}

	public void activate() {
		restartNotificationTimer();
		button.listenInput(true);
	}

	public void deactivate() {
		button.listenInput(false);
		toast.dispose();
		notificationTimer.stop();
	}

	public boolean isActive() {
		return isActive;
	}

	public void updateText() {
		hintCountText.setText("(" + IchiguBank.getHintCount() + ")");
	}
}