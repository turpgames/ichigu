package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.IchiguMarket;

public class MarketScreen extends IchiguScreen {
	private IchiguMarket market;

	@Override
	protected void onAfterActivate() {
		IchiguToolbar.getInstance().activateBackButton();
		market.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		IchiguToolbar.getInstance().deactivateBackButton();
		market.deactivate();
		return true;
	}

	@Override
	public void init() {
		super.init();
		market = new IchiguMarket();
		registerDrawable(market, Utils.LAYER_SCREEN);
	}
}
