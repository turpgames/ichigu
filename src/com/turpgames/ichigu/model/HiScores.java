package com.turpgames.ichigu.model;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class HiScores implements IDrawable {
	private GameInfo info;
	private IchiguTextButton resetScores;
	private Dialog confirmDialog;

	public HiScores() {
		info = new GameInfo();
		info.locate(Text.HAlignCenter, Text.VAlignCenter);

		resetScores = new IchiguTextButton();
		resetScores.setText("Reset Hi Scores");
		resetScores.listenInput(false);
		resetScores.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open("Are you sure that you want to reset hi-scores?");
			}
		});

		confirmDialog = new Dialog();
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if ("Yes".equals(id)) {
					Settings.putInteger(R.settings.hiscores.minichallenge, 0);
					Settings.putInteger(R.settings.hiscores.normal, 0);
					Settings.putInteger(R.settings.hiscores.normaltime, 0);
					Settings.putInteger(R.settings.hiscores.fullchallenge, 0);
					info.setText("Mini Challenge: 0\n\nNormal: 0\nTime: 00:00\n\nFull Challenge: 0\n\n\n\n");
				}
			}
		});

		resetScores.getLocation().set(
				(Game.getVirtualWidth() - resetScores.getWidth()) / 2, 200
				);
	}

	public void activate() {
		resetScores.listenInput(true);
		
		int minichallengeScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		int normalScore = Settings.getInteger(R.settings.hiscores.normal, 0);
		int normalTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int fullchallengeScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		
		int min = normalTime / 60;
		int sec = normalTime % 60;
		
		info.setText("Mini Challenge: " + minichallengeScore + "\n\nNormal: " + normalScore + 
				"\nTime: " + (min < 10 ? ("0" + min) : ("" + min)) + ":" + (sec < 10 ? ("0" + sec) : ("" + sec)) + 
				"\n\nFull Challenge: " + fullchallengeScore + "\n\n\n\n");
	}

	public void deactivate() {
		confirmDialog.close();
		resetScores.listenInput(false);
	}

	@Override
	public void draw() {
		info.draw();
		resetScores.draw();
	}
}