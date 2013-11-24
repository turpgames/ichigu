package com.turpgames.ichigu.model.fullgame.fullchallenge;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.FoundInfo;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class FullChallengeMode extends FullGameMode {
	private static int challengeTime = 5 * 60;

	private FoundInfo foundInfo;
	private CountDownTimer timer;

	public FullChallengeMode() {
		foundInfo = new FoundInfo();
		
//		foundInfo.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
//		foundInfo.setPadding(0, 55);
		
		foundInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		foundInfo.setPadding(10, 110);
		
		getDealer().setAsInfiniteDeal();
	}

	@Override
	protected Timer getTimer() {
		if (timer == null) {
			timer = new CountDownTimer(challengeTime);
			timer.setInterval(0.5f);
			timer.setCountDownListener(new CountDownTimer.ICountDownListener() {
				@Override
				public void onCountDownEnd(CountDownTimer timer) {
					notifyModeEnd();
				}
			});
		}
		return timer;
	}

	@Override
	protected void onStartMode() {
		super.onStartMode();
		foundInfo.reset();
	}

	@Override
	protected void onOpenExtraCards() {
		if (timer.getRemaining() > 10)
			super.onOpenExtraCards();
		else
			flashTimerText();
	}
	
	@Override
	protected void onEndMode() {
		int hiScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		int score = foundInfo.getFound();
		if (score > hiScore)
			Settings.putInteger(R.settings.hiscores.fullchallenge, score);

		setResultText(String.format(Ichigu.getString(R.strings.fullChallengeResult),
				foundInfo.getText(), (score > hiScore ? Ichigu.getString(R.strings.newHiscore) : "")));

		super.onEndMode();
	}
	
	@Override
	protected void onDraw() {
		drawScore();
		super.onDraw();
	}

	@Override
	protected int checkIchigu() {
		int score = super.checkIchigu();
		if (score > 0)
			foundInfo.increaseFound();
		return score;
	}

	protected void drawScore() {
		foundInfo.draw();
	}
}