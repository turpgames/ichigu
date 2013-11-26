package com.turpgames.ichigu.model.fullgame.normal;

import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.ichigu.model.fullgame.FullGameMode;
import com.turpgames.ichigu.model.game.Card;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class NormalMode extends FullGameMode {
	private Timer timer;
	private Text remaingCardInfo;

	public NormalMode() {
		remaingCardInfo = new Text();
		
//		remaingCardInfo.setAlignment(Text.HAlignCenter, Text.VAlignBottom);
//		remaingCardInfo.setPadding(0, 55);
		
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
	
	@Override
	protected void onDraw() {
		drawRemainingCards();
		super.onDraw();
	}

	private void drawRemainingCards() {
		remaingCardInfo.setText(getDealer().getIndex() + "/" + Card.CardsInDeck);
		remaingCardInfo.draw();
	}
}
