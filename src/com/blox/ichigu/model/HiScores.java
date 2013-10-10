package com.blox.ichigu.model;

import com.blox.framework.v0.IDrawable;
import com.blox.framework.v0.forms.xml.Dialog;
import com.blox.framework.v0.impl.Settings;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.TextDrawer;
import com.blox.ichigu.utils.R;
import com.blox.ichigu.utils.IchiguResources;

public class HiScores implements IDrawable {
	private GameInfo info;
	private IchiguTextButton resetScores;
	private Dialog confirmDialog;

	public HiScores() {
		info = new GameInfo(20, 0);
		info.setFontScale(R.fontSize.medium);

		resetScores = new IchiguTextButton();
		resetScores.setFont(FontManager.createDefaultFontInstance());
		resetScores.setText("Reset Hi Scores");
		resetScores.listenInput(false);
		resetScores.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open("Are you sure that\nyou want to reset hi-scores?");
			}
		});

		confirmDialog = new Dialog();
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if ("Yes".equals(id)) {
					Settings.putInteger(R.settings.hiscores.practice, 0);
					Settings.putInteger(R.settings.hiscores.challenge, 0);
				}
				IchiguResources.playSoundFlip();
				Game.vibrate(50);				
			}
		});

		resetScores.getLocation().set(
				(Game.getVirtualWidth() - resetScores.getWidth()) / 2, 200
				);
	}

	public void activate() {
		resetScores.listenInput(true);
	}

	public void deactivate() {
		resetScores.listenInput(false);
	}

	@Override
	public void draw() {
		int practiceScore = Settings.getInteger(R.settings.hiscores.practice, 0);
		int challengeScore = Settings.getInteger(R.settings.hiscores.challenge, 0);

		info.draw("Practice: " + practiceScore, TextDrawer.AlignCentered, 200);
		info.draw("Challenge: " + challengeScore, TextDrawer.AlignCentered, 80);

		resetScores.draw();
	}
}