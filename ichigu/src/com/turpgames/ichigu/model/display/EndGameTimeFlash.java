package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.CountDownTimer.ICountDownListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.ichigu.utils.R;

public class EndGameTimeFlash extends GameObject {

	public boolean isVisible;
	private boolean isHiscore;
	
	private Text hiScore;
	private Text finishedTime;

	private TextFlasher hiScoreFlash;

	private CountDownTimer timer;
	
	public static final Color modalBackgroundColor = new Color(0, 0, 0, 0.5f);
	public EndGameTimeFlash() {
		finishedTime = new Text();
		finishedTime.setText("00:00");
		finishedTime.setFontScale(R.fontSize.xLarge);
		finishedTime.getColor().set(R.colors.ichiguGreen);
		finishedTime.setAlignment(Text.HAlignLeft, Text.VAlignCenter);
		finishedTime.setPadding((Game.getVirtualWidth() - finishedTime.getTextAreaWidth())/2, 0);
		
		hiScore = new Text();
		hiScore.setText("HI");
		hiScore.setAlignment(Text.HAlignLeft, Text.VAlignCenter);
		hiScore.setPadding((Game.getVirtualWidth() + finishedTime.getTextAreaWidth())/2, 0);
		hiScoreFlash = new TextFlasher(hiScore, R.colors.ichiguRed, 5, 1.5f);
		
		timer = new CountDownTimer(4.5f);
		timer.setCountDownListener(new ICountDownListener() {
			@Override
			public void onCountDownEnd(CountDownTimer timer) {
				isVisible = false;
			}
		});
	}
	
	public void show(String time, boolean isHiscore) {
		finishedTime.setText(time);
		finishedTime.setPadding((Game.getVirtualWidth() - finishedTime.getTextAreaWidth())/2, 0);
		hiScoreFlash.flash();
		this.isVisible = true;
		this.isHiscore = isHiscore;
		timer.start();
	}
	
	@Override
	public void draw() {
		if (isVisible) {
			if (isHiscore)
				hiScore.draw();
			finishedTime.draw();
			ShapeDrawer.drawRect(Game.scale(Game.getVirtualWidth() / 2 - 100), Game.scale(Game.getVirtualHeight() / 2 - 50), 
					Game.scale(200), Game.scale(100), modalBackgroundColor, true, true);
		}
	}

	@Override
	public void registerSelf() {
		
	}
}
