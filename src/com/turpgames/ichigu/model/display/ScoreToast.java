package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.ToastGameInfo;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class ScoreToast extends ToastGameInfo {

	public ScoreToast() {
		setAlpha(1.0f);
		setToastColor(R.colors.ichiguBlue);
		setTextColor(R.colors.ichiguWhite);
	}
	
	public void show(int score) {
		super.show(score + " " + Game.getLanguageManager().getString(R.strings.points), 1f, 0.2f);
	}
}
