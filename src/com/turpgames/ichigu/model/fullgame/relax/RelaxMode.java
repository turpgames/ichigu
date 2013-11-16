package com.turpgames.ichigu.model.fullgame.relax;

import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.R;

public class RelaxMode extends FullGameMode {	
	private GameInfo resultInfo;

	public RelaxMode() {
		super();
		hint.getLocation().set(Game.getScreenWidth() - hint.getWidth() - 10, Game.viewportToScreenY(30));

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		remaingCardInfo.setPadding(0, 55);
		
		pointsInfoActive = false;
	}

	public void pause() {
		super.pause();
		hint.deactivate();
	}

	public void resume() {
		super.resume();
		hint.activate();
	}

	@Override
	public void startMode() {
		super.startMode();
		timer.stop();
	}

	@Override
	public void endMode() {
		super.endMode();
		resultInfo.setText(Game.getLanguageManager().getString(R.strings.relaxResult));
	}

	@Override
	public void drawGame() {
		drawHint();		
		super.drawGame();
	}
	
	@Override
	protected void drawTime() {	}
	 
	public void drawResult() {
		resultInfo.draw();
		resultScreenButtons.draw();
	}

	protected void drawHint() {
		hint.draw();
	}

	@Override
	public void onBackToMenuTapped() {
		exitMode();
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
	}

	@Override
	public void onNewGameTapped() {
		notifyNewGame();
	}
}