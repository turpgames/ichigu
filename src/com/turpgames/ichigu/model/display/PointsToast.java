package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class PointsToast extends ToastGameInfo {

	public PointsToast() {
		super();
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguBlue);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show(int points) {
		super.show(points + " " + Game.getLanguageManager().getString(R.strings.points), 1000, 200);
	}
}
