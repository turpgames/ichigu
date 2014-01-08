package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IDrawable;

public class ScoreBoard implements IDrawable {
	private Table table;

	public void bindData(HiScores s) {
		
	}
	
	@Override
	public void draw() {
		table.draw();
	}
}
