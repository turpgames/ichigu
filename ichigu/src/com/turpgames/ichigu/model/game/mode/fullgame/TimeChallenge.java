package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.FoundInfo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class TimeChallenge extends FullGameMode {
	private static int challengeTime = Game.isDebug() ? 5 : 5 * 60;

	private FoundInfo foundInfo;
	private CountDownTimer timer;

	public TimeChallenge() {
		foundInfo = new FoundInfo();		
		foundInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		foundInfo.setPadding(10, 110);
		
		getTable().setAsInfiniteDeal();
	}

	@Override
	public void concreteIchiguFound() {
		foundInfo.increaseFound();
		super.concreteIchiguFound();
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
	protected int getScore() {		
		return foundInfo.getFound();
	}
	
	@Override
	protected void onDraw() {
		foundInfo.draw();
		super.onDraw();
	}
	
	@Override
	protected void onResetMode() {
		foundInfo.reset();
		super.onResetMode();
	}
	
	@Override
	protected void onStartMode() {
		super.onStartMode();
		foundInfo.reset();
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		int hiScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		int score = foundInfo.getFound();
		if (score > hiScore)
			Settings.putInteger(R.settings.hiscores.fullchallenge, score);

		resultInfo.setText(String.format(Ichigu.getString(R.strings.fullChallengeResult),
				score, (score > hiScore ? Ichigu.getString(R.strings.newHiscore) : "")));
	}
}