package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IDrawable;

public interface IIchiguViewListener extends IDrawable {
	public static final IIchiguViewListener NULL = new IIchiguViewListener() {
		@Override
		public boolean onScreenDeactivated() {
			return true;
		}

		@Override
		public void onScreenActivated() {

		}

		@Override
		public void draw() {

		}
	};

	void onScreenActivated();

	boolean onScreenDeactivated();
}
