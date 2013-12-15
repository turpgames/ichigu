package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IDrawable;

public interface IIchiguViewListener extends IDrawable {
	public static final IIchiguViewListener NULL = new IIchiguViewListener() {
		@Override
		public void draw() {

		}

		@Override
		public void onScreenActivated() {

		}

		@Override
		public boolean onScreenDeactivated() {
			return true;
		}
	};

	void onScreenActivated();

	boolean onScreenDeactivated();
}
