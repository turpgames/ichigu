package com.turpgames.ichigu.model.singlegame.minichallenge;

import com.turpgames.framework.v0.forms.xml.Toast;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.display.WaitToast;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.IResultScreenButtonsListener;
import com.turpgames.ichigu.model.game.ResultScreenButtons;
import com.turpgames.ichigu.model.singlegame.SingleGameCards;
import com.turpgames.ichigu.model.singlegame.SingleGameMode;
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
		resultInfo.setAlignment(Text.HAlignCenter, Text.VAlignCenter);

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

		centerResetButton();
	}

	private IMiniChallengeModeListener getModeListener() {
		return (IMiniChallengeModeListener) super.modeListener;
	}

	@Override
	protected void openCloseCards(boolean open) {
		for (int i = 0; i < SingleGameCards.TotalCardCount; i++) {
			if (open)
				cards.get(i).open();
			else
				cards.get(i).close();
		}
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
		deactivateCards();
		blockTimer.stop();
		challengeTimer.stop();
		timeInfo.syncText();
		cards.empty();
		int hiScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		if (ichigusFound > hiScore)
			Settings.putInteger(R.settings.hiscores.minichallenge, ichigusFound);

		resultInfo.setText(String.format(Ichigu.getString(R.strings.miniChallengeResult),
				ichigusFound, (ichigusFound > hiScore ? Ichigu.getString(R.strings.newHiscore) : "")));

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
	public void onCardSelected(Card selectedCard) {
		int ichiguScore = cards.checkScore(selectedCard);
		if (ichiguScore > 0) {
			ichigusFound++;
			ichigusFoundInfo.setText(Ichigu.getString(R.strings.found) + ": " + ichigusFound);
		}
		else {
			block();
		}
		super.onCardSelected(selectedCard);
	}

	@Override
	public void deal() {
		blockTimer.stop();
		super.deal();
	}

	@Override
	protected void onDraw() {
		drawCards();
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

	private void drawCards() {
		Card[] allCards = cards.getAllCards();
		for (int i = 0; i < allCards.length; i++)
			if (allCards[i] != null)
				allCards[i].draw();
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
}