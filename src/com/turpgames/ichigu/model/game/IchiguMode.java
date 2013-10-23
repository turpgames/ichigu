package com.turpgames.ichigu.model.game;


public abstract class IchiguMode {
	protected CardDealer dealer;
	protected IIchiguModeListener modeListener;

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
}