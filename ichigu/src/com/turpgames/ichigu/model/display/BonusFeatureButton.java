package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.BlinkingImageButton;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.BonusFeature;
import com.turpgames.ichigu.utils.R;

public class BonusFeatureButton implements IDrawable {
	public static interface IListener {
		/**
		 * returns true if feature is used
		 */
		boolean onUseBonusFeature();

		void onInsufficientBonusFeature();

		void onBonusFeatureAlreadyUsed();
	}

	private final BlinkingImageButton button;
	private final Text featureCountText;

	private BonusFeature feature;
	private Timer notificationTimer = Timer.NULL;

	private boolean isActive;
	private boolean singleUse;
	private String defaultTextureId;
	private boolean isUsed;
	private IListener listener;

	private BonusFeatureButton() {
		this.button = new BlinkingImageButton();
		this.button.setWidth(R.sizes.menuButtonSizeToScreen);
		this.button.setHeight(R.sizes.menuButtonSizeToScreen);
		this.button.setTouchedColor(R.colors.ichiguWhite);

		this.featureCountText = new Text(true, false);
		this.featureCountText.setFontScale(0.75f);
		this.featureCountText.getColor().set(R.colors.ichiguYellow);
		this.featureCountText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
	}

	public void enable() {
		button.listenInput(true);
		notificationTimer.start();
	}

	public void disable() {
		button.listenInput(false);
		notificationTimer.pause();
	}

	public void activate() {
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

	private void useFeature() {
		if (listener == null)
			return;

		if (singleUse && isUsed) {
			listener.onBonusFeatureAlreadyUsed();
			return;
		}

		if (feature.getCount() < 1) {
			listener.onInsufficientBonusFeature();
			return;
		}

		if (listener.onUseBonusFeature()) {
			if (!Game.isDebug()) {
				feature.used();
				if (singleUse)
					setAsUsed();
			}
		}
	}

	private void setAsUsed() {
		isUsed = true;
		button.setDefaultColor(R.colors.ichiguYellow);
		button.setTouchedColor(R.colors.ichiguRed);
		button.setTexture(R.game.textures.singlegame.incorrectmark);
	}

	public void reset() {
		isUsed = false;
		button.setDefaultColor(R.colors.ichiguWhite);
		button.setTouchedColor(R.colors.ichiguWhite);
		button.setTexture(defaultTextureId);
	}

	private void updateFeatureCountText(BonusFeature feature) {
		featureCountText.setText(feature.getCount() + "");
	}

	private BonusFeature.IListener featureListener = new BonusFeature.IListener() {
		@Override
		public void onFeatureUpdated(BonusFeature feature) {
			updateFeatureCountText(feature);
		}
	};

	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private final BonusFeatureButton featureButton;

		private Builder() {
			featureButton = new BonusFeatureButton();
		}

		public Builder attachToFeature(BonusFeature feature) {
			featureButton.feature = feature;
			featureButton.feature.setListener(featureButton.featureListener);
			featureButton.updateFeatureCountText(feature);
			return this;
		}

		public Builder setTexture(String textureId) {
			featureButton.defaultTextureId = textureId;
			featureButton.button.setTexture(textureId);
			return this;
		}

		public Builder enableNotification() {
			featureButton.button.setBlinkDuration(R.durations.blinkDuration);
			featureButton.button.setBlinksPerSecond(R.counts.blinkPerSecond);
			featureButton.notificationTimer = new Timer();
			featureButton.notificationTimer
					.setInterval(R.durations.hintNotificationInterval);
			featureButton.notificationTimer
					.setTickListener(new Timer.ITimerTickListener() {
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
			featureButton.button.getLocation().set(Game.viewportToScreenX(x),
					Game.viewportToScreenY(y));

			featureButton.featureCountText.setLocation(
					Game.viewportToScreenX(x) + R.sizes.menuButtonSizeToScreen
							* 0.8f, Game.viewportToScreenY(y)
							+ R.sizes.menuButtonSizeToScreen * 0.8f);

			return this;
		}

		public Builder setListener(IListener listener) {
			featureButton.listener = listener;
			featureButton.button.setListener(new IButtonListener() {
				@Override
				public void onButtonTapped() {
					featureButton.useFeature();
				}
			});
			return this;
		}

		public BonusFeatureButton build() {
			return featureButton;
		}
	}
}
