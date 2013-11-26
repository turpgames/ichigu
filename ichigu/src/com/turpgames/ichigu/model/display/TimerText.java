package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;

public class TimerText extends Text {
	private TextFlasher flasher;
	private Timer timer;
	
	public TimerText(Timer timer) {
		this.timer = timer;
		this.timer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				syncText();
			}
		});
		flasher = new TextFlasher(this);
	}
	
	public void syncText() {
		setText(timer.getText());		
	}
	
	public void flash() {
		flasher.flash();
	}
}
