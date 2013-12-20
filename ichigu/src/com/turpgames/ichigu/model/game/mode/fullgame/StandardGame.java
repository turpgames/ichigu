package com.turpgames.ichigu.model.game.mode.fullgame;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class StandardGame extends FullGameMode {
	private Timer timer;
	private Text remaingCardInfo;

	public StandardGame() {
		remaingCardInfo = new Text();		
		remaingCardInfo.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		remaingCardInfo.setPadding(10, 110);
	}	
	
	@Override
	protected Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.setInterval(0.5f);
		}
		return timer;
	}

	@Override
	protected void onDraw() {
		remaingCardInfo.setText(table.getDealtCardCount() + "/" + Card.CardsInDeck);
		remaingCardInfo.draw();
		super.onDraw();
	}
	
	@Override
	protected void prepareResultInfoAndSaveHiscore() {
		int hiTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int completeTime = (int) timer.getTotalElapsedTime();

		boolean isNewRecord = completeTime < hiTime || hiTime == 0;

		if (isNewRecord)
			Settings.putInteger(R.settings.hiscores.normaltime, completeTime);

		resultInfo.setText(String.format(
				Ichigu.getString(R.strings.normalResult),
				timer.getText(),
				(isNewRecord ? Ichigu.getString(R.strings.newHiscore) : "")));
	}
}
