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

	public void deal() {
		modeListener.onDealStarted();
		table.deal();
	}
	
	public void dealEnded() {
		startTimer();
		resetButton.listenInput(true);
		IchiguToolbar.getInstance().getBackButton().listenInput(true);
	}
	
	public void dealStarted() {
		pauseTimer();
		resetButton.listenInput(false);
		IchiguToolbar.getInstance().getBackButton().listenInput(false);
	}

	@Override
	public final void draw() {
		table.draw();
		onDraw();
	}

	public final void endMode() {
		table.end();
		onEndMode();
	}
	
	public final boolean exitMode() {
		return onExitMode();
	}
	
	public IIchiguModeListener getModeListener() {
		return modeListener;
	}

	public void openCloseCards(boolean open) {
		table.openCloseCards(open);
	}

	public void setModeListener(IIchiguModeListener controller) {
		this.modeListener = controller;
		setTableListener(controller);
	}

	public final void startMode() {
		onStartMode();
	}

	private void confirmModeExit() {
		pause();
		openCloseCards(false);
		confirmExitDialog.open(Ichigu.getString(R.strings.exitConfirm));
	}

	private void drawResetButton() {
		resetButton.draw();
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

	private void onResetConfirmed(boolean reset) {
		if (reset) {
			resetMode();
		}
		else {
			resume();
		}
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
	
	private void setTableListener(ITableListener controller) {
		table.setListener(controller);
	}

	protected Table getTable() {
		return table;
	}

	protected abstract void initTable();
	
	protected void onDraw() {
		drawResetButton();
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
	
	protected void onResetMode() {
		resetButton.listenInput(true);
		table.reset();
		deal();
	}

	protected void onStartMode() {
		isExitConfirmed = false;
		resetButton.listenInput(true);
		table.start();
	}

	protected abstract void pauseTimer();

	protected abstract void prepareResultInfoAndSaveHiscore();

	protected void resetMode() {
		onResetMode();
	}

	protected abstract void startTimer();
}