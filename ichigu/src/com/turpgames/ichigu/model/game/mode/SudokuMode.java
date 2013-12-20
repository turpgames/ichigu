package com.turpgames.ichigu.model.game.mode;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.EndGameTimeFlash;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.SudokuTable;
import com.turpgames.ichigu.utils.R;

public class SudokuMode extends IchiguMode {

	private Timer timer;
	protected TimerText timerText;
	private EndGameTimeFlash endGameTimeFlash;
	
	public SudokuMode() {	
		timer = new Timer();
		timer.setInterval(0.5f);
		timerText = new TimerText(timer);
		timerText.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		timerText.setPadding(0, 50);
		
		endGameTimeFlash = new EndGameTimeFlash();
	}
	
	public void cardTapped(Card card) {
		// TODO Auto-generated method stub
		
	}

	public void drawResult() {
		// TODO Auto-generated method stub
		
	}

	public void swapEnded() {
		dealEnded();
	}

	public void swapStarted() {
		dealStarted();
	}
	
	public void tableFinished() {
		prepareResultInfoAndSaveHiscore();
		table.deal();
		timer.restart();
	}

	@Override
	protected void initTable() {
		table = new SudokuTable();
	}

	@Override
	protected void onDraw() {
		if (endGameTimeFlash.isVisible)
			endGameTimeFlash.draw();
		timerText.draw();
		super.onDraw();
	}
	
	@Override
	protected void onEndMode() {
		timer.stop();
		super.onEndMode();
	}

	@Override
	protected boolean onExitMode() {
		if (!super.onExitMode())
			return false;
		timer.stop();
		return true;
	}
	
	@Override
	protected void onResetMode() {
		timer.restart();
		timerText.syncText();
		super.onResetMode();
	}

	@Override
	protected void onStartMode() {
		timer.restart();
		timerText.syncText();
		super.onStartMode();
	}

	@Override
	protected void pauseTimer() {
		timer.pause();
	}

	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		float hiTime = Settings.getFloat(R.settings.hiscores.sudoku, 0);
		float completeTime = timer.getTotalElapsedTime();
		
		boolean isNewRecord = completeTime < hiTime || hiTime == 0;

		if (isNewRecord) {
			Settings.putFloat(R.settings.hiscores.sudoku, completeTime);
			endGameTimeFlash.show(timer.getText(), true);
		}
		else
			endGameTimeFlash.show(timer.getText(), false);
	}

	@Override
	protected void startTimer() {
		timer.start();
	}
}
