package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.BonusFeatureButton;
import com.turpgames.ichigu.model.display.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.display.ResultScreenButtons;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.game.BonusFeature;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.mode.RegularMode;
import com.turpgames.ichigu.model.game.table.FullGameTable;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.R;

public abstract class FullGameMode extends RegularMode implements IResultScreenButtonsListener {

	private BonusFeatureButton singleHintFeatureButton;
	private BonusFeatureButton tripleHintFeatureButton;
	private BonusFeatureButton timerPauseFeatureButton;

	protected TimerText timerText;

	protected Text resultInfo;
	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);

		singleHintFeatureButton = BonusFeatureButton.newBuilder()
				.attachToFeature(BonusFeature.singleHint)
				.setTexture(R.game.textures.hintSingle)
				.setLocation(10, 30)
				.enableNotification()
				.setListener(singleHintFeatureListener)
				.build();

		tripleHintFeatureButton = BonusFeatureButton.newBuilder()
				.attachToFeature(BonusFeature.tripleHint)
				.setTexture(R.game.textures.hintTriple)
				.setLocation(10 + R.sizes.menuButtonSize + 20, 30)
				.setAsSingleUse()
				.setListener(tripleHintFeatureListener)
				.build();

		timerPauseFeatureButton = BonusFeatureButton.newBuilder()
				.attachToFeature(BonusFeature.timerPause)
				.setTexture(R.game.textures.timerPause)
				.setLocation(10 + 2 * (R.sizes.menuButtonSize + 20), 30)
				.setAsSingleUse()
				.setListener(timerPauseFeatureListener)
				.build();

		timerText = new TimerText(getTimer());
		timerText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timerText.setPadding(Game.getVirtualWidth() - 115, 110);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 150);
	}

	public void applyTimePenalty() {
		getTimer().addSeconds(R.counts.fullModeSecondPerPenalty);
		timerText.syncText();
		timerText.flash();
	}

	public void cardTapped(Card card) {
		singleHintFeatureButton.restartNotificationTimer();
	}

	@Override
	public void concreteIchiguFound() {

	}

	@Override
	public void concreteInvalidIchiguSelected() {
		IchiguToast.showError(R.strings.tryAgain);
	}

	@Override
	public void dealEnded() {
		activateFeatureButtons();
		super.dealEnded();
	}

	@Override
	public void dealStarted() {
		disableFeatureButtons();
		super.dealStarted();
	}

	public void deckFinished() {
		notifyModeEnd();
	}

	public void drawResult() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	protected void sendScore(int score, int mode) {
		if (!Facebook.canLogin())
			return;
				
		Facebook.sendScore(mode, score, new ICallback() {
			@Override
			public void onSuccess() {
				IchiguToast.showInfo(R.strings.sendScoreSuccess);				
			}
			
			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.sendScoreFail);				
			}
		});
	}

	@Override
	protected FullGameTable getTable() {
		return (FullGameTable) table;
	}

	protected abstract Timer getTimer();

	protected void notifyModeEnd() {
		prepareResultInfoAndSaveHiscore();
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	@Override
	protected void onDraw() {
		timerText.draw();
		drawFeatureButtons();
		super.onDraw();
	}

	@Override
	protected void onEndMode() {
		getTimer().stop();
		resultScreenButtons.listenInput(true);
		deactivateFeatureButtons();
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.listenInput(false);
		deactivateFeatureButtons();
		return true;
	}

	@Override
	protected void onResetMode() {
		getTimer().restart();
		timerText.syncText();
		deactivateFeatureButtons();
		resetFeatureButtons();
		super.onResetMode();
	}

	@Override
	protected void onStartMode() {
		getTimer().restart();
		timerText.syncText();
		resultScreenButtons.listenInput(false);
		resetFeatureButtons();
		super.onStartMode();
	}

	@Override
	protected void pauseTimer() {
		getTimer().pause();
		disableFeatureButtons();
	}

	@Override
	protected void startTimer() {
		getTimer().start();
		enableFeatureButtons();
	}

	private void enableFeatureButtons() {
		singleHintFeatureButton.enable();
		tripleHintFeatureButton.enable();
		timerPauseFeatureButton.enable();
	}

	private void disableFeatureButtons() {
		singleHintFeatureButton.disable();
		tripleHintFeatureButton.disable();
		timerPauseFeatureButton.disable();
	}

	private void activateFeatureButtons() {
		singleHintFeatureButton.activate();
		tripleHintFeatureButton.activate();
		timerPauseFeatureButton.activate();
	}

	private void deactivateFeatureButtons() {
		singleHintFeatureButton.deactivate();
		tripleHintFeatureButton.deactivate();
		timerPauseFeatureButton.deactivate();
	}

	private void resetFeatureButtons() {
		singleHintFeatureButton.reset();
		tripleHintFeatureButton.reset();
		timerPauseFeatureButton.reset();
	}

	private void drawFeatureButtons() {
		singleHintFeatureButton.draw();
		tripleHintFeatureButton.draw();
		timerPauseFeatureButton.draw();
	}

	private void toastInsufficientFeature() {
		IchiguToast.showError(R.strings.buyFromMarket);
	}

	private void toastFeatureAlreadyUsed() {
		IchiguToast.showError(R.strings.bonusFeatureOnceWarning);
	}

	private BonusFeatureButton.IListener singleHintFeatureListener = new BonusFeatureButton.IListener() {
		@Override
		public boolean onUseBonusFeature() {
			return getTable().showHint(false);
		}

		@Override
		public void onInsufficientBonusFeature() {
			toastInsufficientFeature();
		}

		@Override
		public void onBonusFeatureAlreadyUsed() {
			toastFeatureAlreadyUsed();
		}
	};

	private BonusFeatureButton.IListener tripleHintFeatureListener = new BonusFeatureButton.IListener() {
		@Override
		public boolean onUseBonusFeature() {
			return getTable().showHint(true);
		}

		@Override
		public void onInsufficientBonusFeature() {
			toastInsufficientFeature();
		}

		@Override
		public void onBonusFeatureAlreadyUsed() {
			toastFeatureAlreadyUsed();
		}
	};

	private BonusFeatureButton.IListener timerPauseFeatureListener = new BonusFeatureButton.IListener() {
		@Override
		public boolean onUseBonusFeature() {
			getTimer().pauseFor(R.durations.fullModeTimerPauseDuration);
			return true;
		}

		@Override
		public void onInsufficientBonusFeature() {
			toastInsufficientFeature();
		}

		@Override
		public void onBonusFeatureAlreadyUsed() {
			toastFeatureAlreadyUsed();
		}
	};
}
