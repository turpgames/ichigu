package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.BlinkingImageButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.mode.IchiguMode;
import com.turpgames.ichigu.utils.R;

public class HintButton extends BlinkingImageButton {
	private final static int notificationInterval = 30;
	private Timer notificationTimer;
	
	public HintButton() {
		super(IchiguMode.buttonSize, IchiguMode.buttonSize, R.colors.ichiguWhite, R.colors.ichiguYellow, 1f, 10);
		setTexture(R.game.textures.hint);
		getLocation().set(10, Game.viewportToScreenY(30));
		
		notificationTimer = new Timer();
		notificationTimer.setInterval(notificationInterval);
		notificationTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				blink();
			}
		});
	}
	
	public void activateHint() {
		notificationTimer.restart();
		listenInput(true);
	}

	public void deactivateHint() {
		listenInput(false);
		notificationTimer.stop();
	}

	public void restartNotificationTimer() {
		notificationTimer.restart();
	}
	
}
