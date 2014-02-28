package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.game.BonusFeature;
import com.turpgames.ichigu.model.game.IchiguMarket;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

public class MarketScreen extends IchiguScreen {
	private static String backScreen;
	private static BonusFeature defaultFeature = BonusFeature.tripleHint;
	
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
		market.activate(defaultFeature);
	}

	@Override
	protected boolean onBeforeDeactivate() {
		IchiguToolbar.getInstance().deactivateBackButton();
		market.deactivate();
		return true;
	}
	
	public static void show(String backMenu, BonusFeature feature) {
		MarketScreen.backScreen = backMenu;
		MarketScreen.defaultFeature = feature; 
		ScreenManager.instance.switchTo(R.game.screens.market, false);
	}
	
	@Override
	protected boolean onBack() {
		if (Util.Strings.isNullOrWhitespace(backScreen))
			return super.onBack();
		ScreenManager.instance.switchTo(backScreen, true);
		backScreen = null;
		defaultFeature = BonusFeature.tripleHint;
		return true;
	}
}
