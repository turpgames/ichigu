package com.blox.ichigu.model;

import com.blox.framework.v0.forms.xml.Dialog;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Game;
import com.blox.ichigu.utils.R;

public class RelaxMode extends FullGameMode {
	protected IchiguImageButton resetButton;

	private GameInfo resultInfo;
	private Dialog resetConfirmDialog;

	public RelaxMode() {
		super();
		hint.getLocation().set(Game.getVirtualWidth() - hint.getWidth() - 10, 50);

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		resetConfirmDialog = new Dialog();
		resetConfirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onResetConfirmed("Yes".equals(id));
			}
		});

		resetButton = new IchiguImageButton();
		resetButton.getLocation().set(10, 50);
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
			timer.start();
			openCloseCards(true);
		}
	}

	private void confirmResetMode() {
		resetConfirmDialog.open("Are you sure you want to reset game?");
		timer.pause();
		openCloseCards(false);
	}

	private void resetMode() {
		endMode();
		startMode();
		deal();
	}

	public void pause() {
		resetConfirmDialog.close();
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
		resetConfirmDialog.close();
		resetButton.listenInput(false);

		resultInfo.setText("Congratulations,\n" +
				String.format("You found %d ichigu%s!", ichigusFound, ichigusFound != 1 ? "s" : "") +
				"\n\nTotal Time " + modeCompleteTime +
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