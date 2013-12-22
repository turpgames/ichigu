package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.BlinkingImageButton;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.IchiguBonusFeature;
import com.turpgames.ichigu.utils.R;

public class FullGameBonusFeature implements IDrawable {
	private final BlinkingImageButton button;
	private final Text featureCountText;
	
	private IchiguBonusFeature feature;
	private Timer notificationTimer = Timer.NULL;
	
	private boolean isActive;
	private boolean singleUse;
	private boolean permanentlyDeactivated;

	private FullGameBonusFeature() {
		this.button = new BlinkingImageButton();
		this.button.setWidth(R.sizes.menuButtonSizeToScreen);
		this.button.setHeight(R.sizes.menuButtonSizeToScreen);
		this.button.setTouchedColor(R.colors.ichiguWhite);

		this.featureCountText = new Text(true, false);
		this.featureCountText.setFontScale(0.75f);
		this.featureCountText.getColor().set(R.colors.ichiguYellow);
		this.featureCountText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);		
	}

	public void reset() {
		permanentlyDeactivated = false;
	}

	public void enable() {
		if (permanentlyDeactivated)
			return;
		button.listenInput(true);
		notificationTimer.start();
	}

	public void disable() {
		button.listenInput(false);
		notificationTimer.pause();
	}

	public void activate() {
		if (permanentlyDeactivated)
			return;
		button.activate();
		notificationTimer.restart();
		isActive = true;
	}

	public void deactivate() {
		button.deactivate();
		notificationTimer.stop();
		isActive = false;
	}

	public void restartNotificationTimer() {
		notificationTimer.restart();
	}

	@Override
	public void draw() {
		if (!isActive)
			return;

		button.draw();
		featureCountText.draw();
	}

	private void onUsed() {
		feature.used();
		if (singleUse) {
			deactivate();
			permanentlyDeactivated = true;
		}
	}

	private void updateFeatureCountText(IchiguBonusFeature feature) {
		featureCountText.setText(feature.getCount() + "");
	}

	private IchiguBonusFeature.IListener featureListener = new IchiguBonusFeature.IListener() {
		@Override
		public void onFeatureUpdated(IchiguBonusFeature feature) {
			updateFeatureCountText(feature);
		}
	};

	public static final class Builder {
		private final FullGameBonusFeature featureButton;

		private Builder() {
			featureButton = new FullGameBonusFeature();
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder listenFeature(IchiguBonusFeature feature) {
			featureButton.feature = feature;
			featureButton.feature.setListener(featureButton.featureListener);
			featureButton.updateFeatureCountText(feature);
			return this;
		}

		public Builder setTexture(String textureId) {
			featureButton.button.setTexture(textureId);
			return this;
		}

		public Builder enableNotification() {
			featureButton.button.setBlinkDuration(R.durations.blinkDuration);
			featureButton.button.setBlinksPerSecond(R.counts.blinkPerSecond);
			featureButton.notificationTimer = new Timer();
			featureButton.notificationTimer.setInterval(R.durations.hintNotificationInterval);
			featureButton.notificationTimer.setTickListener(new Timer.ITimerTickListener() {
				@Override
				public void timerTick(Timer timer) {
					featureButton.button.blink();
				}
			});
			return this;
		}

		public Builder setAsSingleUse() {
			featureButton.singleUse = true;
			return this;
		}

		public Builder setLocation(float x, float y) {
			featureButton.button.getLocation().set(
					Game.viewportToScreenX(x),
					Game.viewportToScreenY(y));

			featureButton.featureCountText.setLocation(
					Game.viewportToScreenX(x) + R.sizes.menuButtonSizeToScreen * 0.8f,
					Game.viewportToScreenY(y) + R.sizes.menuButtonSizeToScreen * 0.8f);

			return this;
		}

		public Builder setListener(final IButtonListener listener) {
			featureButton.button.setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					listener.onButtonTapped();
					featureButton.onUsed();
				}
			});
			return this;
		}

		public FullGameBonusFeature build() {
			return featureButton;
		}
	}
}
