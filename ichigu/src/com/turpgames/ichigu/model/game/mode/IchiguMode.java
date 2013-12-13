package com.turpgames.ichigu.model.game.mode;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.table.ITableListener;
import com.turpgames.ichigu.model.game.table.Table;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguMode implements IDrawable {
	public final static float buttonSize = Game.scale(R.ui.imageButtonWidth);

	protected Table table;
	protected IIchiguModeListener modeListener;

	protected ImageButton resetButton;
	private Dialog resetConfirmDialog;

	private Dialog confirmExitDialog;
	protected boolean isExitConfirmed;

	public IchiguMode() {
		resetButton = new ImageButton(buttonSize, buttonSize, R.game.textures.refresh, R.colors.buttonDefault, R.colors.buttonTouched);
		resetButton.getLocation().set(Game.getScreenWidth() - buttonSize - 10, Game.viewportToScreenY(30));
		resetButton.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onConfirmResetMode();
			}
		});

		resetConfirmDialog = new IchiguDialog();
		resetConfirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onResetConfirmed(R.strings.yes.equals(id));
			}

			@Override
			public void onDialogClosed() {
				onResetConfirmed(false);			
			}
		});

		confirmExitDialog = new IchiguDialog();
		confirmExitDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				onExitConfirmed(R.strings.yes.equals(id));
			}

			@Override
			public void onDialogClosed() {
				onExitConfirmed(false);			
			}
		});
		
		initTable();
	}

	protected abstract void initTable();
	
	protected Table getTable() {
		return table;
	}
	
	protected abstract void pauseTimer();

	protected abstract void startTimer();

	protected abstract void prepareResultInfoAndSaveHiscore();
	
	public void openCloseCards(boolean open) {
		table.openCloseCards(open);
	}
	
	private void onResetConfirmed(boolean reset) {
		if (reset) {
			resetMode();
		}
		else {
			resume();
		}
	}

	private void onConfirmResetMode() {
		pause();
		resetConfirmDialog.open(Ichigu.getString(R.strings.resetConfirm));
		openCloseCards(false);
	}

	private void onExitConfirmed(boolean exit) {
		isExitConfirmed = exit;
		if (isExitConfirmed) {
			modeListener.onExitConfirmed();
		}
		else {
			resume();
		}
	}

	private void confirmModeExit() {
		pause();
		openCloseCards(false);
		confirmExitDialog.open(Ichigu.getString(R.strings.exitConfirm));
	}

	private void pause() {
		pauseTimer();
		resetConfirmDialog.close();
		resetButton.listenInput(false);
	}

	private void resume() {
		startTimer();
		resetButton.listenInput(true);
		openCloseCards(true);
	}

	public void setModeListener(IIchiguModeListener controller) {
		this.modeListener = controller;
		setTableListener(controller);
	}

	private void setTableListener(ITableListener controller) {
		table.setListener(controller);
	}

	public void deal() {
		modeListener.onDealStarted();
		table.deal();
	}

	public final void startMode() {
		onStartMode();
	}

	protected void resetMode() {
		onResetMode();
	}
	
	public final void endMode() {
		table.end();
		onEndMode();
	}

	public final boolean exitMode() {
		return onExitMode();
	}

	public void dealStarted() {
		pauseTimer();
		resetButton.listenInput(false);
		IchiguToolbar.getInstance().getBackButton().listenInput(false);
	}
	
	public void dealEnded() {
		startTimer();
		resetButton.listenInput(true);
		IchiguToolbar.getInstance().getBackButton().listenInput(true);
	}
	
	protected void onStartMode() {
		isExitConfirmed = false;
		resetButton.listenInput(true);
		table.start();
	}

	protected void onResetMode() {
		resetButton.listenInput(true);
		table.reset();
		table.deal();
	}
	
	protected void onEndMode() {
		isExitConfirmed = true;
		resetConfirmDialog.close();
	}

	protected boolean onExitMode() {
		if (!isExitConfirmed) {
			confirmModeExit();
			return false;
		}
		confirmExitDialog.close();
		isExitConfirmed = false;
		resetConfirmDialog.close();
		resetButton.listenInput(false);
		table.end();
		return true;
	}

	public final void draw() {
		table.draw();
		onDraw();
	}

	protected void onDraw() {
		drawResetButton();
	}

	private void drawResetButton() {
		resetButton.draw();
	}

	public IIchiguModeListener getModeListener() {
		return modeListener;
	}
}