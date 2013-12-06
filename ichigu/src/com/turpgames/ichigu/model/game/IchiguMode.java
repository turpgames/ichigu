package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public abstract class IchiguMode implements IDrawable, ICardDealerListener {
	protected final static float buttonSize = Game.scale(R.ui.imageButtonWidth);

	protected CardDealer dealer;
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
		
		initDealer();
	}

	protected abstract void initDealer();
	
	protected abstract void pauseTimer();

	protected abstract void startTimer();

	public void onCardTapped(Card card) {
		dealer.onCardTapped(card);
	}
	
	public void activateCards() {
		dealer.activateCards();
	}

	public void deactivateCards() {
		dealer.deactivateCards();
	}
	
	protected abstract void prepareResultInfoAndSaveHiscore();
	
	public void openCloseCards(boolean open) {
		dealer.openCloseCards(open);
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

	protected void resetMode() {
		onResetMode();
	}
	
	public final void endMode() {
		dealer.end();
		onEndMode();
	}

	public final boolean exitMode() {
		return onExitMode();
	}

	protected void onStartMode() {
		isExitConfirmed = false;
		resetButton.listenInput(true);
		dealer.start();
	}

	protected void onResetMode() {
		resetButton.listenInput(true);
		dealer.reset();
		dealer.deal();
	}
	
	protected void onEndMode() {
		isExitConfirmed = true;
		resetConfirmDialog.close();
		dealer.end();
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
		dealer.end();
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

	public IIchiguModeListener getModeListener() {
		return modeListener;
	}
	

	@Override
	public void onDealEnd() {
		
	}

	@Override
	public void onCardsActivated() {
		
	}

	@Override
	public void onCardsDeactivated() {
		
	}
}