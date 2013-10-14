package com.blox.ichigu.model;

import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Game;
import com.blox.ichigu.utils.R;

public class RelaxMode extends FullGameMode {
	protected IchiguImageButton resetButton;
	
	private GameInfo resultInfo;
	
	public RelaxMode() {
		super();
		hint.getLocation().set(Game.getVirtualWidth() - hint.getWidth() - 10, 50);
		
		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		resetButton = new IchiguImageButton();
		resetButton.getLocation().set(10, 50);
		resetButton.setTexture(R.game.textures.refresh);
		resetButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				endMode();
				startMode();
				deal();
			}
		});
	}

	public void pause() {
		timer.pause();
		hint.deactivate();
		resetButton.listenInput(false);
	}

	public void resume() {
		timer.start();
		hint.activate();
		resetButton.listenInput(true);
	}
	
	@Override
	public void startMode() {
		super.startMode();
		resetButton.listenInput(true);
	}
	
	@Override
	public void endMode() {
		super.endMode();
		resetButton.listenInput(false);
		
		resultInfo.setText("Congratulations,\n" +
				String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
				"\nTotal Time " + modeCompleteTime + 
				"\nTouch Screen\nTo Continue");
	}
	
	@Override
	public void exitMode() {
		super.exitMode();
		resetButton.listenInput(false);
	}

	@Override
	public void drawGame() {
		drawHint();
		drawResetButton();
		super.drawGame();
	}

	public void drawResult() {
		resultInfo.draw();
	}

	protected void drawHint() {
		hint.draw();
	}

	protected void drawResetButton() {
		resetButton.draw();
	}
}