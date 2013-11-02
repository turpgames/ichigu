package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.FlashingGameInfo;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class ScoreInfo extends FlashingGameInfo {
	int score;
	
	public ScoreInfo() {
		super(R.colors.ichiguRed, 8, 0.5f);
		init();
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void increaseScore(int score) {
		this.score += score;
		updateText();
	}
	
	public void decreaseScore(int score) {
		this.score -= score;
		updateText();
		start();
	}

	public void init() {
		this.score = 0;
		updateText();
	}
	
	private void updateText() {
		setText(Game.getLanguageManager().getString(R.strings.score) + ": " + this.score);
	}
}
