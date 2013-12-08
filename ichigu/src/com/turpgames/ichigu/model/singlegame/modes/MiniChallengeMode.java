package com.turpgames.ichigu.model.singlegame.modes;

import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.WaitToast;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.model.singlegame.ISingleGameModeListener;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
import com.turpgames.ichigu.model.singlegame.SingleGameQuestion;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class MiniChallengeMode extends SingleGameMode implements IResultScreenButtonsListener {
	private final static float blockDuration = 2f;
	private final static int challengeTime = 60;

	private final CountDownTimer blockTimer;
	private final CountDownTimer challengeTimer;

	private int ichigusFound;
	private Text ichigusFoundInfo;

	private TimerText timeInfo;
	private WaitToast waitInfo;
	private Text resultInfo;

	private ResultScreenButtons resultScreenButtons;

	public MiniChallengeMode() {
		question = new SingleGameQuestion(0.3f, 2.2f);
		
		resultScreenButtons = new ResultScreenButtons(this);

		waitInfo = new WaitToast();
		waitInfo.setToastListener(new Toast.IToastListener() {
			@Override
			public void onToastHidden(Toast toast) {
				notifyUnblocked();
			}
		});

		ichigusFoundInfo = new Text();
		ichigusFoundInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		ichigusFoundInfo.setPadding(20, 125);

		resultInfo = new Text();
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		resultInfo.setPadding(0, 150);

		blockTimer = new CountDownTimer(challengeTime);
		blockTimer.setInterval(blockDuration);
		blockTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				timer.stop();
			}
		});

		challengeTimer = new CountDownTimer(challengeTime);
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
	public ISingleGameModeListener getModeListener() {
		return (ISingleGameModeListener) super.modeListener;
	}

	private void notifyUnblocked() {
		if (getModeListener() != null)
			getModeListener().onUnblock();
	}

	private void notifyModeEnd() {
		resultScreenButtons.listenInput(true);
		if (getModeListener() != null)
			getModeListener().onModeEnd();
	}

	private void notifyNewGame() {
		resultScreenButtons.listenInput(false);
		if (getModeListener() != null)
			getModeListener().onNewGame();
	}

	private void block() {
		blockTimer.start();
		waitInfo.show(blockDuration);
	}

	@Override
	protected void onStartMode() {
		ichigusFound = 0;
		ichigusFoundInfo.setText(Ichigu.getString(R.strings.found) + ": " + ichigusFound);
		blockTimer.stop();
		challengeTimer.restart();
		timeInfo.syncText();
		super.onStartMode();
	}

	@Override
	protected void onEndMode() {
		blockTimer.stop();
		challengeTimer.stop();
		timeInfo.syncText();
		
		super.onEndMode();
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		int hiScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		if (ichigusFound > hiScore)
			Settings.putInteger(R.settings.hiscores.minichallenge, ichigusFound);

		resultInfo.setText(String.format(Ichigu.getString(R.strings.miniChallengeResult),
				ichigusFound, (ichigusFound > hiScore ? Ichigu.getString(R.strings.newHiscore) : "")));
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
	public void deal() {
		blockTimer.stop();
		super.deal();
	}

	@Override
	protected void onDraw() {
		drawRemainingTime();
		drawIchigusFound();
		if (!blockTimer.isStopped())
			drawWaitMessage();
		super.onDraw();
	}

	public void drawResultScreen() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	private void drawRemainingTime() {
		timeInfo.draw();
	}

	private void drawWaitMessage() {
		waitInfo.setText(String.format("%.1f", blockDuration - blockTimer.getElapsedTime()));
	}

	protected void drawIchigusFound() {
		ichigusFoundInfo.draw();
	}

	@Override
	public void onBackToMenuTapped() {
		getModeListener().onExitConfirmed();
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}

	@Override
	protected void pauseTimer() {
		challengeTimer.pause();
	}

	@Override
	protected void startTimer() {
		challengeTimer.start();
	}

	@Override
	public void concreteIchiguFound() {
		ichigusFound++;
		ichigusFoundInfo.setText(Ichigu.getString(R.strings.found) + ": " + ichigusFound);
		super.concreteIchiguFound();
	}

	@Override
	public void concreteInvalidIchiguSelected() {
		block();
		super.concreteInvalidIchiguSelected();
	}
}