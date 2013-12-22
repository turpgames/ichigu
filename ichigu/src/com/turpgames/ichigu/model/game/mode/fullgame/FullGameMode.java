package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.FullGameBonusFeature;
import com.turpgames.ichigu.model.display.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.display.ResultScreenButtons;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IchiguBonusFeature;
import com.turpgames.ichigu.model.game.mode.RegularMode;
import com.turpgames.ichigu.model.game.table.FullGameTable;
import com.turpgames.ichigu.utils.R;

public abstract class FullGameMode extends RegularMode implements IResultScreenButtonsListener {

	private FullGameBonusFeature singleHintButton;
	private FullGameBonusFeature tripleHintButton;
	private FullGameBonusFeature timerPauseButton;

	protected Text resultInfo;
	protected TimerText timerText;

	private ResultScreenButtons resultScreenButtons;

	public FullGameMode() {
		resultScreenButtons = new ResultScreenButtons(this);

		singleHintButton = FullGameBonusFeature.Builder.newBuilder()
				.listenFeature(IchiguBonusFeature.singleHint)
				.setTexture(R.game.textures.hintSingle)
				.setLocation(10, 30)
				.enableNotification()
				.setListener(new IButtonListener() {
					@Override
					public void onButtonTapped() {
						getTable().showHint(false);
					}
				})
				.build();

		tripleHintButton = FullGameBonusFeature.Builder.newBuilder()
				.listenFeature(IchiguBonusFeature.tripleHint)
				.setTexture(R.game.textures.hintTriple)
				.setLocation(10 + R.sizes.menuButtonSize + 20, 30)
				.setAsSingleUse()
				.setListener(new IButtonListener() {
					@Override
					public void onButtonTapped() {
						getTable().showHint(true);
						tripleHintButton.deactivate();
					}
				})
				.build();

		timerPauseButton = FullGameBonusFeature.Builder.newBuilder()
				.listenFeature(IchiguBonusFeature.timerPause)
				.setTexture(R.game.textures.timerPause)
				.setLocation(10 + 2 * (R.sizes.menuButtonSize + 20), 30)
				.setAsSingleUse()
				.setListener(new IButtonListener() {
					@Override
					public void onButtonTapped() {
						timerPauseButton.deactivate();
					}
				})
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
		timerText.flash();
	}

	public void cardTapped(Card card) {
		singleHintButton.restartNotificationTimer();
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
		notifyNewGame();
	}

	private void notifyNewGame() {
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	@Override
	protected FullGameTable getTable() {
		return (FullGameTable) table;
	}

	protected abstract Timer getTimer();

	@Override
	protected void initTable() {
		table = new FullGameTable();
	}

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
		singleHintButton.enable();
		tripleHintButton.enable();
		timerPauseButton.enable();
	}

	private void disableFeatureButtons() {
		singleHintButton.disable();
		tripleHintButton.disable();
		timerPauseButton.disable();
	}

	private void activateFeatureButtons() {
		singleHintButton.activate();
		tripleHintButton.activate();
		timerPauseButton.activate();
	}

	private void deactivateFeatureButtons() {
		singleHintButton.deactivate();
		tripleHintButton.deactivate();
		timerPauseButton.deactivate();
	}

	private void resetFeatureButtons() {
		singleHintButton.reset();
		tripleHintButton.reset();
		timerPauseButton.reset();
	}

	private void drawFeatureButtons() {
		singleHintButton.draw();
		tripleHintButton.draw();
		timerPauseButton.draw();
	}
}
