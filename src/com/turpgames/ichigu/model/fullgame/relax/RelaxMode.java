package com.turpgames.ichigu.model.fullgame.relax;

import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.info.GameInfo;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.R;

public class RelaxMode extends FullGameMode {
	protected ImageButton resetButton;

	private GameInfo resultInfo;
	private Dialog resetConfirmDialog;

	public RelaxMode() {
		super();
		hint.getLocation().set(Game.getScreenWidth() - hint.getWidth() - 10, Game.viewportToScreenY(30));

		resultInfo = new GameInfo();
		resultInfo.locate(Text.HAlignCenter, Text.VAlignCenter);

		remaingCardInfo.setPadding(0, 55);
		
		resetConfirmDialog = new IchiguDialog();
		resetConfirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onResetConfirmed(R.strings.yes.equals(id));
			}
		});

		resetButton = new ImageButton(R.ui.imageButtonWidth, R.ui.imageButtonHeight, R.game.textures.refresh, R.colors.buttonDefault, R.colors.buttonTouched);
		resetButton.getLocation().set(10,  Game.viewportToScreenY(30));
		resetButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmResetMode();
			}
		});
		
		pointsInfoActive = false;
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
		resetConfirmDialog.open(Game.getLanguageManager().getString(R.strings.resetConfirm));
		openCloseCards(false);
	}

	private void resetMode() {
		endMode();
		startMode();
		deal();
		closeExtraCards();
	}

	public void pause() {
		resetConfirmDialog.close();
		hint.deactivate();
		resetButton.listenInput(false);
	}

	public void resume() {
		hint.activate();
		resetButton.listenInput(true);
		openCloseCards(true);
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
		
//		if (ichigusFound != 1)
//			resultInfo.setText(String.format(Game.getLanguageManager().getString(R.strings.relaxResultMultiple), ichigusFound));
//		else 
//			resultInfo.setText(String.format(Game.getLanguageManager().getString(R.strings.relaxResultSingle), ichigusFound));
		
		resultInfo.setText(Game.getLanguageManager().getString(R.strings.relaxResult));
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
		resultScreenButtons.draw();
	}

	protected void drawHint() {
		hint.draw();
	}

	protected void drawResetButton() {
		resetButton.draw();
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