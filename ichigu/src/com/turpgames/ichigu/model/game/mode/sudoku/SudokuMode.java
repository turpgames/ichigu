package com.turpgames.ichigu.model.game.mode.sudoku;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.mode.IchiguMode;
import com.turpgames.ichigu.model.game.table.SudokuTable;
import com.turpgames.ichigu.model.game.table.Table;

public class SudokuMode extends IchiguMode {

	private Timer timer;
	protected TimerText timerText;
	
	public SudokuMode() {	
		timer = new Timer();
		timer.setInterval(0.5f);
		timerText = new TimerText(timer);
		timerText.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
		timerText.setPadding(0, 50);		
	}
	
	public void cardTapped(Card card) {
		
	}

	public void drawResult() {
		
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
	protected Table createTable() {
		return new SudokuTable();
	}

	@Override
	protected void onDraw() {
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
		IchiguToast.showInfo(timer.getText());
	}

	@Override
	protected void startTimer() {
		timer.start();
	}
}
