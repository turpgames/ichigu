package com.blox.ichigu.view;

import com.blox.framework.v0.impl.Screen;
import com.blox.framework.v0.impl.ScreenManager;
import com.blox.ichigu.model.Logo;
import com.blox.ichigu.model.Toolbar;
import com.blox.ichigu.utils.R;

public abstract class IchiguScreen extends Screen {
	protected IIchiguViewListener screenListener;

	protected void notifyScreenActivated() {
		if (screenListener != null)
			screenListener.onScreenActivated();
	}

	protected void notifyScreenDeactivated() {
		if (screenListener != null)
			screenListener.onScreenDeactivated();
	}

	protected void notifyDraw() {
		if (screenListener != null)
			screenListener.draw();
	}

	protected void setScreenListener(IIchiguViewListener screenListener) {
		this.screenListener = screenListener;
	}

	@Override
	public void activated() {
		super.activated();
		notifyScreenActivated();
		Toolbar.getInstance().activateBackButton();
	}

	@Override
	public void deactivated() {
		super.deactivated();
		notifyScreenDeactivated();
	}

	@Override
	public void init() {
		super.init();

		Logo logo = new Logo();
		logo.getColor().a = 0.25f;
		registerDrawable(logo, 1);

		registerInputListener(this);
	}

	@Override
	public void render() {
		super.render();
		
		if (super.isActive())
			notifyDraw();
	}
	
	void back() {
		onBack();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.game.screens.menu, true);
		return true;
	}

	protected abstract String getTitle();
}