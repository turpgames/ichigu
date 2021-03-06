package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.component.Toast;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.CountDownTimer;
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
import com.turpgames.ichigu.utils.Facebook;
import com.turpgames.ichigu.utils.R;
import com.turpgames.ichigu.utils.ScoreManager;
import com.turpgames.ichigu.view.MarketScreen;

public abstract class FullGameMode extends RegularMode implements IResultScreenButtonsListener {

	private BonusFeatureButton singleHintFeatureButton;
	private BonusFeatureButton tripleHintFeatureButton;
	private BonusFeatureButton timerPauseFeatureButton;
	
	private BonusFeature insufficientFeature;

	protected TimerText timerText;
	
	private TimerText pauseTimerText;
	private CountDownTimer pauseTimer; 

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
		
		pauseTimer = new CountDownTimer(R.durations.fullModeTimerPauseDuration);
		pauseTimer.setCountDownListener(new CountDownTimer.ICountDownListener() {
			@Override
			public void onCountDownEnd(CountDownTimer timer) {
				onTimerPauseEnd();
			}
		});
		pauseTimer.setInterval(0.2f);
		
		pauseTimerText = new TimerText(pauseTimer);
		pauseTimerText.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pauseTimerText.setPadding(Game.getVirtualWidth() - 115, 115);
		pauseTimerText.getColor().set(R.colors.ichiguYellow);
		pauseTimerText.setFontScale(R.fontSize.small);

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
	
	private IFullGameModeListener getFullGameModeListener() {
		return (IFullGameModeListener)getModeListener();
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
	
	private void switchToMarketMenu() {
		getFullGameModeListener().onPauseForMarketMenu();
	}

	public void resumeFromMarketMenu() {
		resume();
	}

	public void pauseForMarketMenu() {
		pause();
		MarketScreen.show(getScreenId(), insufficientFeature);
	}

	protected abstract String getScreenId();

	protected abstract int getRoundScore();

	protected abstract int getScoreMode();
	
	@Override
	protected void pause() {
		super.pause();
		disableFeatureButtons();
		getTable().disableCardsOnTable();
	}
	
	@Override
	protected void resume() {
		super.resume();
		enableFeatureButtons();
		getTable().enableCardsOnTable();
	}

	@Override
	public void onShareScore() {
		Facebook.shareScore(getScoreMode(), getRoundScore(), new ICallback() {
			@Override
			public void onSuccess() {
				FullGameMode.this.resultScreenButtons.deactivateShareScoreButton();
			}

			@Override
			public void onFail(Throwable t) {
			}
		});
	}

	protected void sendScore() {
		ScoreManager.instance.sendScore(getScoreMode(), getRoundScore());
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
		if (!pauseTimer.isStopped())
			pauseTimerText.draw();
		drawFeatureButtons();
		super.onDraw();
	}

	@Override
	protected void onEndMode() {
		getTimer().stop();
		pauseTimer.stop();
		resultScreenButtons.activate();
		deactivateFeatureButtons();
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		getTimer().stop();
		resultScreenButtons.deactivate();
		deactivateFeatureButtons();
		pauseTimer.stop();
		return true;
	}

	@Override
	protected void onResetMode() {
		pauseTimer.stop();
		getTimer().restart();
		timerText.syncText();
		deactivateFeatureButtons();
		resetFeatureButtons();
		super.onResetMode();
	}

	@Override
	protected void onStartMode() {
		pauseTimer.stop();
		getTimer().restart();
		timerText.syncText();
		resultScreenButtons.deactivate();
		resetFeatureButtons();
		super.onStartMode();
	}

	@Override
	protected void pauseTimer() {
		if (pauseTimer.isRunning())
			pauseTimer.pause();
		getTimer().pause();
	}

	@Override
	protected void startTimer() {
		if (pauseTimer.isPaused())
			pauseTimer.start();
		getTimer().start();
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

	private void toastInsufficientFeature(BonusFeature feature) {
		this.insufficientFeature = feature;
		IchiguToast.showWarning(R.strings.buyFromMarket, new Toast.IListener() {
			@Override
			public boolean onToastTapped(Toast toast) {
				switchToMarketMenu();
				return true;
			}

			@Override
			public void onToastHidden(Toast toast) {

			}
		});
	}

	private void toastFeatureAlreadyUsed() {
		IchiguToast.showError(R.strings.bonusFeatureOnceWarning);
	}

	private void onPauseTimerFeatureUsed() {
		getTimer().pauseFor(R.durations.fullModeTimerPauseDuration);
		pauseTimer.restart();
		pauseTimerText.syncText();
	}
	
	private void onTimerPauseEnd() {
		pauseTimer.stop();
	} 
	
	private BonusFeatureButton.IListener singleHintFeatureListener = new BonusFeatureButton.IListener() {
		@Override
		public boolean onUseBonusFeature() {
			return getTable().showHint(false);
		}

		@Override
		public void onInsufficientBonusFeature() {
			toastInsufficientFeature(BonusFeature.singleHint);
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
			toastInsufficientFeature(BonusFeature.tripleHint);
		}

		@Override
		public void onBonusFeatureAlreadyUsed() {
			toastFeatureAlreadyUsed();
		}
	};

	private BonusFeatureButton.IListener timerPauseFeatureListener = new BonusFeatureButton.IListener() {
		@Override
		public boolean onUseBonusFeature() {
			onPauseTimerFeatureUsed();
			return true;
		}

		@Override
		public void onInsufficientBonusFeature() {
			toastInsufficientFeature(BonusFeature.timerPause);
		}

		@Override
		public void onBonusFeatureAlreadyUsed() {
			toastFeatureAlreadyUsed();
		}
	};
}
