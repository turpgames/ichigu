package com.turpgames.ichigu.model.game.mode.singlegame;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.FoundInfo;
import com.turpgames.ichigu.model.display.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.display.ResultScreenButtons;
import com.turpgames.ichigu.model.display.SingleGameQuestion;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class MiniGame extends SingleGameMode implements IResultScreenButtonsListener {

	private final CountDownTimer blockTimer;
	private final CountDownTimer challengeTimer;

	private FoundInfo foundInfo;

	private TimerText timeInfo;
	private Text resultInfo;

	private ResultScreenButtons resultScreenButtons;

	public MiniGame() {
		question = new SingleGameQuestion(0.3f, 2.2f);

		resultScreenButtons = new ResultScreenButtons(this);

		foundInfo = new FoundInfo();
		foundInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		foundInfo.setPadding(20, 125);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 150);

		blockTimer = new CountDownTimer(R.durations.miniModeChallengeDuration);
		blockTimer.setInterval(R.durations.miniModeBlockDuration);
		blockTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				timer.stop();
				notifyUnblocked();
			}
		});

		challengeTimer = new CountDownTimer(R.durations.miniModeChallengeDuration);
		challengeTimer.setInterval(0.5f);
		challengeTimer.setCountDownListener(new CountDownTimer.ICountDownListener() {
			@Override
			public void onCountDownEnd(CountDownTimer timer) {
				notifyModeEnd();
			}
		});

		timeInfo = new TimerText(challengeTimer);
		timeInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timeInfo.setPadding(Game.getVirtualWidth() - 120, 125);

		// Center reset button
		resetButton.getLocation().set((Game.getScreenWidth() - resetButton.getWidth()) / 2, Game.viewportToScreenY(50));
	}
	
	@Override
	public void concreteIchiguFound() {
		foundInfo.increaseFound();
		super.concreteIchiguFound();
	}

	@Override
	public void concreteInvalidIchiguSelected() {
		block();
		super.concreteInvalidIchiguSelected();
	}

	@Override
	public void deal() {
		blockTimer.stop();
		super.deal();
	}

	@Override
	public void drawResultScreen() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	@Override
	public ISingleGameModeListener getModeListener() {
		return (ISingleGameModeListener) super.modeListener;
	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}

	private void block() {
		blockTimer.start();
		IchiguToast.preapreToast()
				.setHideOnTap(false)
				.setMessage(Ichigu.getString(R.strings.wait) + ": " + String.format("%.1f", R.durations.miniModeBlockDuration))
				.setBackColor(R.colors.ichiguRed)
				.setDisplayDuration(R.durations.miniModeBlockDuration)
				.show();
	}

	private void drawRemainingTime() {
		timeInfo.draw();
	}

	private void drawWaitMessage() {
		IchiguToast.updateMessage(Ichigu.getString(R.strings.wait) + ": " + String.format("%.1f", R.durations.miniModeBlockDuration - blockTimer.getElapsedTime()));
	}

	private void notifyModeEnd() {
		prepareResultInfoAndSaveHiscore();
		resultScreenButtons.listenInput(true);
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	private void notifyNewGame() {
		resultScreenButtons.listenInput(false);
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	private void notifyUnblocked() {
		if (getModeListener() != null)
			getModeListener().onUnblock();
	}

	@Override
	protected void onDraw() {
		drawRemainingTime();
		foundInfo.draw();
		if (!blockTimer.isStopped())
			drawWaitMessage();
		super.onDraw();
	}

	@Override
	protected void onEndMode() {
		blockTimer.stop();
		challengeTimer.stop();
		timeInfo.syncText();

		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;

		blockTimer.stop();
		challengeTimer.stop();
		timeInfo.syncText();
		resultScreenButtons.listenInput(false);
		return true;
	}

	@Override
	protected void onResetMode() {
		challengeTimer.restart();
		timeInfo.syncText();
		foundInfo.reset();
		super.onResetMode();
	}

	@Override
	protected void onStartMode() {
		foundInfo.reset();
		blockTimer.stop();
		super.onStartMode();
		challengeTimer.restart();
		timeInfo.syncText();
	}

	@Override
	protected void pauseTimer() {
		challengeTimer.pause();
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		int hiScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		int ichigusFound = foundInfo.getFound();
		if (ichigusFound > hiScore)
			Settings.putInteger(R.settings.hiscores.minichallenge, ichigusFound);

		resultInfo.setText(String.format(Ichigu.getString(R.strings.miniChallengeResult),
				ichigusFound, (ichigusFound > hiScore ? Ichigu.getString(R.strings.newHiscore) : "")));
	}

	@Override
	protected void startTimer() {
		challengeTimer.start();
	}
}