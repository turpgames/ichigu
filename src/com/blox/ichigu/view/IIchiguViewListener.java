package com.blox.ichigu.view;

import com.blox.framework.v0.IDrawable;

public interface IIchiguViewListener extends IDrawable {
	void onScreenActivated();

	void onScreenDeactivated();
}
