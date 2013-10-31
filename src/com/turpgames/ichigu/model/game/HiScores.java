package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IIchiguButtonListener;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.IchiguTextButton;
import com.turpgames.ichigu.model.game.info.GameInfo;
import com.turpgames.ichigu.utils.R;

public class HiScores implements IDrawable, ILanguageListener {
	private GameInfo info;
	private IchiguTextButton resetScores;
	private Dialog confirmDialog;

	public HiScores() {
		info = new GameInfo();
		info.locate(Text.HAlignCenter, Text.VAlignCenter);

		resetScores = new IchiguTextButton();
		resetScores.setDefaultColor(R.colors.ichiguYellow);
		resetScores.setTouchedColor(R.colors.ichiguRed);
		resetScores.listenInput(false);
		resetScores.setListener(new IIchiguButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open(Game.getLanguageManager().getString(R.strings.hiscoreResetConfirm));
			}
		});

		confirmDialog = new IchiguDialog();
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if (R.strings.yes.equals(id)) {
					Settings.putInteger(R.settings.hiscores.minichallenge, 0);
					Settings.putInteger(R.settings.hiscores.normal, 0);
					Settings.putInteger(R.settings.hiscores.normaltime, 0);
					Settings.putInteger(R.settings.hiscores.fullchallenge, 0);
					info.setText(Game.getLanguageManager().getString(R.strings.hiscoreReseted));
				}
			}
		});
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	public void activate() {
		resetScores.listenInput(true);
		
		int minichallengeScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		int normalScore = Settings.getInteger(R.settings.hiscores.normal, 0);
		int normalTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int fullchallengeScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);
		
		info.setText(String.format(Game.getLanguageManager().getString(R.strings.hiscoreInfo),
				minichallengeScore, normalScore, Utils.getTimeString(normalTime), fullchallengeScore));
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

	private void setLanguageSensitiveInfo() {
		resetScores.setText(Game.getLanguageManager().getString(R.strings.resetHiscore));
		resetScores.getLocation().set((Game.getVirtualWidth() - resetScores.getWidth()) / 2, 50);
	}
	
	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}