package com.turpgames.ichigu.model.fullgame.relax;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IIchiguButtonListener;
import com.turpgames.ichigu.model.display.IchiguImageButton;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.GameInfo;
import com.turpgames.ichigu.utils.R;

public class RelaxMode extends FullGameMode {
	protected IchiguImageButton resetButton;

	private GameInfo resultInfo;
	private Dialog resetConfirmDialog;

	public RelaxMode() {
		super();
		hint.getLocation().set(Game.getVirtualWidth() - hint.getWidth() - 10, 30);

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		remaingCardInfo.setPadding(0, 55);
		
		resetConfirmDialog = new Dialog();
		resetConfirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onResetConfirmed("Yes".equals(id));
			}
		});

		resetButton = new IchiguImageButton();
		resetButton.getLocation().set(10, 30);
		resetButton.setTexture(R.game.textures.refresh);
		resetButton.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmResetMode();
			}
		});
	}

	private void onResetConfirmed(boolean reset) {
		if (reset) {
			resetMode();
		}
		else {
			openCloseCards(true);
		}
	}

	private void confirmResetMode() {
		resetConfirmDialog.open("Are you sure you want to reset game?");
		openCloseCards(false);
	}

	private void resetMode() {
		endMode();
		startMode();
		deal();
	}

	public void pause() {
		resetConfirmDialog.close();
		hint.deactivate();
		resetButton.listenInput(false);
	}

	public void resume() {
		hint.activate();
		resetButton.listenInput(true);
	}

	@Override
	public void startMode() {
		super.startMode();
		resetButton.listenInput(true);
		timer.stop();
	}

	@Override
	public void endMode() {
		super.endMode();
		resetConfirmDialog.close();
		resetButton.listenInput(false);

		resultInfo.setText("Game over!\n\nCongratulations,\n" +
				String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
				"\n\nTouch Screen To Continue");
	}

	@Override
	public boolean exitMode() {
		super.exitMode();
		resetConfirmDialog.close();
		resetButton.listenInput(false);
		return true;
	}

	@Override
	public void drawGame() {
		drawHint();
		drawResetButton();
		
		super.drawGame();
	}

//	@Override
//	public void deckFinished() {
//		dealer.reset();
//		deckCount++;
//	}
	
	@Override
	protected void drawTime() {	}
	 
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