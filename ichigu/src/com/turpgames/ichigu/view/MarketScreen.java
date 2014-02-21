package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.IchiguMarket;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

public class MarketScreen extends IchiguScreen {
	private IchiguMarket market;

	@Override
	public void init() {
		super.init();
		market = new IchiguMarket();
		registerDrawable(market, Game.LAYER_SCREEN);
	}

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
	
	private static String backScreen;
	
	public static void show(String backMenu) {
		MarketScreen.backScreen = backMenu;
		ScreenManager.instance.switchTo(R.game.screens.market, false);
	}
	
	@Override
	protected boolean onBack() {
		if (Util.Strings.isNullOrWhitespace(backScreen))
			return super.onBack();
		ScreenManager.instance.switchTo(backScreen, true);
		backScreen = null;
		return true;
	}
}
