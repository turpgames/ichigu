package com.turpgames.ichigu.model.fullgame.normal;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class NormalMode extends FullGameMode {
	private Timer timer;

	@Override
	protected Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.setInterval(0.5f);
		}
		return timer;
	}

	@Override
	protected void onEndMode() {
		int hiTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int completeTime = (int) timer.getTotalElapsedTime();

		boolean isNewRecord = completeTime < hiTime || hiTime == 0;

		if (isNewRecord)
			Settings.putInteger(R.settings.hiscores.normaltime, completeTime);

		setResultText(String.format(
				Ichigu.getString(R.strings.normalResult),
				timer.getText(),
				(isNewRecord ? Ichigu.getString(R.strings.newHiscore) : "")));

		super.onEndMode();
	}
}
