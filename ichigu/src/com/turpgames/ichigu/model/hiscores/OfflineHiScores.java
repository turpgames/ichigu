package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;
import com.turpgames.utils.Util;

class OfflineHiScores implements IHiScores, IView {
	private Text pageTitle;
	private Text info;
	private TextButton resetScores;
	private ImageButton loginWithFacebook;
	private Dialog confirmDialog;
	private final HiScores parent;

	OfflineHiScores(HiScores parent) {
		this.parent = parent;

		pageTitle = new Text();
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);

		info = new Text();
		info.setFontScale(R.fontSize.medium);
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

		loginWithFacebook = new ImageButton(R.sizes.loginWidth, R.sizes.loginHeight, R.game.textures.fb_login);
		loginWithFacebook.setLocation(Button.AlignS, 0, Game.viewportToScreenY(50));
		loginWithFacebook.listenInput(false);
		loginWithFacebook.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				loginWithFacebook();
			}
		});

		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	private void loginWithFacebook() {
		Facebook.login(new ICallback() {
			
			@Override
			public void onSuccess() {
				parent.updateView();
			}
			
			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.loginError);
				if (t != null)
					t.printStackTrace();
			}
		});
		
	}

	@Override
	public String getId() {
		return "OfflineHiScores";
	}

	@Override
	public void activate() {
		resetScores.listenInput(true);
		loginWithFacebook.listenInput(true);

		setInfo();
	}

	@Override
	public boolean deactivate() {
		confirmDialog.close();
		resetScores.listenInput(false);
		loginWithFacebook.listenInput(false);
		return true;
	}

	@Override
	public void draw() {
		pageTitle.draw();
		info.draw();
		resetScores.draw();
		loginWithFacebook.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	private void resetHiscores() {
		Settings.putInteger(R.settings.hiscores.miniChallenge, 0);
		Settings.putInteger(R.settings.hiscores.standard, 0);
		Settings.putInteger(R.settings.hiscores.timeChallenge, 0);
		setInfo();
	}

	private void setInfo() {
		int minichallengeScore = Settings.getInteger(R.settings.hiscores.miniChallenge, 0);
		int normalTime = Settings.getInteger(R.settings.hiscores.standard, 0);
		int fullchallengeScore = Settings.getInteger(R.settings.hiscores.timeChallenge, 0);

		info.setText(String.format(Ichigu.getString(R.strings.hiscoreInfo),
				minichallengeScore,
				normalTime == 0 ? "-" : Util.Strings.getTimeString(normalTime),
				fullchallengeScore));
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.hiScores));
		resetScores.setText(Ichigu.getString(R.strings.resetHiscore));
		resetScores.getLocation().set((Game.getVirtualWidth() - resetScores.getWidth()) / 2, 120);
	}
}