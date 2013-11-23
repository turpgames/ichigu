package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class HiScores implements IDrawable, ILanguageListener {
	private Text pageTitle;
	private Text info;
	private TextButton resetScores;
	private Dialog confirmDialog;

	public HiScores() {
		pageTitle = new Text();
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		info = new Text();
		info.setAlignment(Text.HAlignCenter, Text.VAlignCenter);

		resetScores = new TextButton(R.colors.ichiguYellow, R.colors.ichiguRed);
		resetScores.listenInput(false);
		resetScores.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				confirmDialog.open(Ichigu.getString(R.strings.hiscoreResetConfirm));
			}
		});

		confirmDialog = new IchiguDialog();
		confirmDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if (R.strings.yes.equals(id)) {
					resetHiscores();
				}
			}

			@Override
			public void onDialogClosed() {
				
			}
		});

		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	private void resetHiscores() {
		Settings.putInteger(R.settings.hiscores.minichallenge, 0);
		Settings.putInteger(R.settings.hiscores.normal, 0);
		Settings.putInteger(R.settings.hiscores.normaltime, 0);
		Settings.putInteger(R.settings.hiscores.fullchallenge, 0);
		info.setText(String.format(Ichigu.getString(R.strings.hiscoreInfo),
				0, Utils.getTimeString(0), 0));
	}

	public void activate() {
		resetScores.listenInput(true);

		int minichallengeScore = Settings.getInteger(R.settings.hiscores.minichallenge, 0);
		int normalTime = Settings.getInteger(R.settings.hiscores.normaltime, 0);
		int fullchallengeScore = Settings.getInteger(R.settings.hiscores.fullchallenge, 0);

		info.setText(String.format(Ichigu.getString(R.strings.hiscoreInfo),
				minichallengeScore, Utils.getTimeString(normalTime), fullchallengeScore));
	}

	public void deactivate() {
		confirmDialog.close();
		resetScores.listenInput(false);
	}

	@Override
	public void draw() {
		pageTitle.draw();
		info.draw();
		resetScores.draw();
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.hiScores));
		resetScores.setText(Ichigu.getString(R.strings.resetHiscore));
		resetScores.getLocation().set((Game.getVirtualWidth() - resetScores.getWidth()) / 2, 50);
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}