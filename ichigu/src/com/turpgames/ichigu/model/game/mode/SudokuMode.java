package com.turpgames.ichigu.model.game.mode;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.display.TimerText;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.model.game.table.SudokuTable;

public class SudokuMode extends IchiguMode {

	private Timer timer;
	protected TimerText timerText;
	
	public SudokuMode() {	
		timer = new Timer();
		timer.setInterval(0.5f);
		timerText = new TimerText(timer);
		timerText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		timerText.setPadding(Game.getVirtualWidth() - 115, 110);
	}
	
	@Override
	protected void initTable() {
		table = new SudokuTable();
	}

	@Override
	protected void pauseTimer() {
		timer.pause();
	}

	@Override
	protected void startTimer() {
		timer.start();
	}

	@Override
	protected void onDraw() {
		timerText.draw();
		super.onDraw();
	}
	
	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		// TODO Auto-generated method stub
		
	}

	public void cardTapped(Card card) {
		// TODO Auto-generated method stub
		
	}

	public void drawResult() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onStartMode() {
		timer.restart();
		timerText.syncText();
		super.onStartMode();
	}

	@Override
	protected void onResetMode() {
		timer.restart();
		timerText.syncText();
		super.onResetMode();
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

	public void tableFinished() {
		table.deal();
	}
}
