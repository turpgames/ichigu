package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.entity.Score;
import com.turpgames.ichigu.model.game.table.FullGameTable;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class StandardGame extends FullGameMode {
	private Timer timer;
	private Text remaingCardInfo;
	private int completeTime;

	public StandardGame() {
		remaingCardInfo = new Text();
		remaingCardInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		remaingCardInfo.setPadding(10, 110);
	}
	
	@Override
	protected String getScreenId() {
		return R.game.screens.standard;
	}

	@Override
	protected Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.setInterval(0.5f);
		}
		return timer;
	}

	@Override
	protected void onDraw() {
		remaingCardInfo.setText(table.getDealtCardCount() + "/" + R.counts.ichiguDeckCardCount);
		remaingCardInfo.draw();
		super.onDraw();
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		int hiTime = Settings.getInteger(R.settings.hiscores.standard, 0);
		completeTime = (int) timer.getTotalElapsedTime();

		boolean isNewRecord = completeTime < hiTime || hiTime == 0;

		if (isNewRecord)
			Settings.putInteger(R.settings.hiscores.standard, completeTime);

		resultInfo.setText(String.format(
				Ichigu.getString(R.strings.normalResult),
				timer.getText(),
				(isNewRecord ? Ichigu.getString(R.strings.newHiscore) : "")));

		// Workaround: InputManager bir şekilde deactivated kalıyor. 
		// Neden olduğunu aramak yerine burda tekrar activate etmeyi tercih ettim :p
		Game.getInputManager().activate();
		
		super.sendScore();
	}

	@Override
	protected Table createTable() {
		return new FullGameTable();
	}

	@Override
	protected int getRoundScore() {
		return completeTime;
	}

	@Override
	protected int getScoreMode() {
		return Score.ModeStandard;
	}
}
