package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.IchiguToast;
import com.turpgames.ichigu.social.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;


class OnlineHiScores implements IHiScores {	
	private Text pageTitle;
	private final HiScores parent;
	private ImageButton logoutOfFacebook;

	OnlineHiScores(HiScores parent) {
		this.parent = parent;	
		
		pageTitle = new Text();
		pageTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		pageTitle.getColor().set(R.colors.ichiguYellow);
		pageTitle.setFontScale(1.5f);
		pageTitle.setPadding(0, 85);
		
		logoutOfFacebook = new ImageButton(R.sizes.loginWidth, R.sizes.loginHeight, R.game.textures.fb_logout);
		logoutOfFacebook.setLocation(Button.AlignS, 0, Game.viewportToScreenY(50));
		logoutOfFacebook.listenInput(false);
		logoutOfFacebook.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				logoutOfFacebook();
			}
		});
		
		setLanguageSensitiveInfo();
		Game.getLanguageManager().register(this);
	}

	private void logoutOfFacebook() {
		Facebook.logout(new ICallback() {			
			@Override
			public void onSuccess() {
				parent.updateView();
			}
			
			@Override
			public void onFail(Throwable t) {
				IchiguToast.showError(R.strings.logoutError);
			}
		});
	}
	
	@Override
	public void draw() {
		pageTitle.draw();
		logoutOfFacebook.draw();	
	}

	@Override
	public void onLanguageChanged() {
		
	}

	@Override
	public void activate() {
		logoutOfFacebook.listenInput(true);
	}

	@Override
	public boolean deactivate() {
		logoutOfFacebook.listenInput(false);
		return true;
	}

	@Override
	public String getId() {
		return "OnlineHiScores";
	}

	private void setLanguageSensitiveInfo() {
		pageTitle.setText(Ichigu.getString(R.strings.hiScores));
	}
}
