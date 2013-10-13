package com.blox.ichigu.model;

import com.blox.framework.v0.IDrawable;
import com.blox.framework.v0.IFont;
import com.blox.framework.v0.effects.IEffectEndListener;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.ITextSliderListener;
import com.blox.framework.v0.util.TextSlider;
import com.blox.framework.v0.util.Timer;
import com.blox.framework.v0.util.Vector;
import com.blox.ichigu.utils.R;

class FullGameHint implements IDrawable, ITextSliderListener, IEffectEndListener {
	private final static int notificationInterval = 30;
	
	private IchiguImageButton button;
	private FullGameIchiguInfo ichiguInfo;
	private Card[] cards;
	private TextSlider textSlider;
	private String text;
	private int hintIndex;
	private boolean isActive;
	private IFont font;
	private Timer notificationTimer;

	FullGameHint() {
		button = new IchiguImageButton();
		button.setTexture(R.game.textures.hint);
		button.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				showNextHint();
				restartNotificationTimer();
			}
		});

		ichiguInfo = new FullGameIchiguInfo();
		textSlider = new TextSlider();
		textSlider.setListener(this);
		
		notificationTimer = new Timer();
		notificationTimer.setInterval(notificationInterval);
		notificationTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				button.blink(null, false);
			}
		});
		
		font = FontManager.createDefaultFontInstance();
		font.setScale(R.fontSize.small);
	}
	
	public Vector getLocation() {
		return button.getLocation();
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
		updateText();
		hintIndex = 0;
		isActive = false;
		this.cards = cards;
	}

	public int getIchiguCount() {
		return ichiguInfo.getIchiguCount();
	}

	public void setSlideY(float slideY) {
		textSlider.setY(slideY);
	}
	
	private void showNextHint() {
		if (isActive)
			return;

		if (hintIndex == 0) {
			textSlider.setText(text);
			textSlider.slide();
		}
		else {
			int cardIndex = ichiguInfo.getIchiguCardIndex(hintIndex - 1, 1);
			cards[cardIndex].blink(this, false);
		}

		isActive = true;
	}

	private void updateText() {
		int count = ichiguInfo.getIchiguCount();
		if (count < 1)
			text = "No ichigu exists on table";
		else if (count == 1)
			text = "1 ichigu exists on table";
		else
			text = count + " ichigus exist on table";
	}

	private void hintEnd() {
		hintIndex = (hintIndex + 1) % (1 + ichiguInfo.getIchiguCount());
		isActive = false;
	}

	@Override
	public boolean onEffectEnd(Object card) {
		hintEnd();
		return true;
	}

	@Override
	public void onTextSlideEnd(TextSlider slider) {
		hintEnd();
	}

	@Override
	public void draw() {
		drawButton();
	}
	
	private void drawButton() {
		button.draw();
	}

	public void activate() {
		button.listenInput(true);
	}

	public void deactivate() {
		button.listenInput(false);
	}
}