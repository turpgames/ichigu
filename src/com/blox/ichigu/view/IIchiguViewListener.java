package com.blox.ichigu.view;

import com.blox.framework.v0.IDrawable;

public interface IIchiguViewListener extends IDrawable {
	public static final IIchiguViewListener NULL = new IIchiguViewListener() {
		@Override
		public void draw() {

		}

		@Override
		public boolean onScreenDeactivated() {
			return true;
		}

		@Override
		public void onScreenActivated() {

		}
	};

	void onScreenActivated();

	boolean onScreenDeactivated();
}
