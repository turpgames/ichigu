package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguMode implements IDrawable {
	protected final static float buttonSize = Game.scale(R.ui.imageButtonWidth);

	protected CardDealer dealer;
	protected IIchiguModeListener modeListener;

	private ImageButton resetButton;
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
	}

	protected abstract void pauseTimer();

	protected abstract void startTimer();

	protected void centerResetButton() {
		resetButton.getLocation().set((Game.getScreenWidth() - resetButton.getWidth()) / 2, Game.viewportToScreenY(50));
	}

	private void onResetConfirmed(boolean reset) {
		if (reset) {
			resetMode();
		}
		else {
			resume();
		}
	}

	protected void resetMode() {
		endMode();
		startMode();
		deal();
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

	protected abstract void openCloseCards(boolean open);

	protected void notifyIchiguFound() {
		if (modeListener != null)
			modeListener.onIchiguFound();
	}

	protected void notifyInvalidIchiguSelected() {
		if (modeListener != null)
			modeListener.onInvalidIchiguSelected();
	}

	public void setModeListener(IIchiguModeListener modeListener) {
		this.modeListener = modeListener;
	}

	public void setDealerListener(ICardDealerListener dealerListener) {
		if (dealer != null)
			dealer.setListener(dealerListener);
	}

	public void deal() {
		dealer.deal();
	}

	public final void startMode() {
		onStartMode();
	}

	public final void endMode() {
		dealer.abortDeal();
		onEndMode();
	}

	public final boolean exitMode() {
		dealer.abortDeal();
		return onExitMode();
	}

	protected void onStartMode() {
		isExitConfirmed = false;
		resetButton.listenInput(true);
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
		return true;
	}

	public final void draw() {
		onDraw();
	}

	protected void onDraw() {
		drawResetButton();
	}

	private void drawResetButton() {
		resetButton.draw();
	}
}