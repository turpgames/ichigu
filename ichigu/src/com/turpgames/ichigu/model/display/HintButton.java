package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.BlinkingImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.BonusFeature;
import com.turpgames.ichigu.utils.R;

public class HintButton extends BlinkingImageButton {
	private Timer notificationTimer;
	private Text hintCountText;
	
	public HintButton(String textureId) {
		super(R.sizes.menuButtonSizeToScreen, R.sizes.menuButtonSizeToScreen, R.colors.ichiguWhite, R.colors.ichiguWhite, 1f, 10);
		setTexture(textureId);
		getLocation().set(10, Game.viewportToScreenY(30));
		
		hintCountText = new Text(true, false);
		hintCountText.setFontScale(0.75f);
		hintCountText.getColor().set(R.colors.ichiguYellow);
		hintCountText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		hintCountText.setLocation(
				10 + R.sizes.menuButtonSizeToScreen * 0.8f, 
				Game.viewportToScreenY(30) + R.sizes.menuButtonSizeToScreen * 0.8f);
		
		notificationTimer = new Timer();
		notificationTimer.setInterval(R.durations.hintNotificationInterval);
		notificationTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				blink();
			}
		});
	}
	
	public void listenFeature(BonusFeature feature) {
		feature.setListener(featureListener);
		updateHintCountText(feature);
	}

	private void updateHintCountText(BonusFeature feature) {
		if (feature.getCount() > 0) {
			hintCountText.setText(feature.getCount() + "");			
		}
		else {
			listenInput(false);
		}
	}

	public void activateHint() {
		notificationTimer.restart();
		listenInput(true);
	}

	public void deactivateHint() {
		listenInput(false);
		notificationTimer.stop();
	}

	public void restartNotificationTimer() {
		notificationTimer.restart();
	}
	
	@Override
	public void draw() {
		hintCountText.draw();
		super.draw();
	}
	
	private BonusFeature.IListener featureListener = new BonusFeature.IListener() {		
		@Override
		public void onFeatureUpdated(BonusFeature feature) {
			updateHintCountText(feature);
		}
	};
}
