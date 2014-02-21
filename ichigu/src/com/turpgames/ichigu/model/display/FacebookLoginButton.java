package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class FacebookLoginButton implements IDrawable {
	private ImageButton button;
	private Dialog logoutConfirmDialog;

	public FacebookLoginButton() {
		button = new ImageButton(Game.scale(R.sizes.menuButtonSize), Game.scale(R.sizes.menuButtonSize), R.game.textures.facebook);
		button.setLocation(Button.AlignNW, R.sizes.menuButtonSpacing, R.sizes.menuButtonSpacing);
		button.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onLoginButtonTapped();
			}
		});
		
		logoutConfirmDialog = new Dialog();
		logoutConfirmDialog.addButton("yes", R.strings.yes);
		logoutConfirmDialog.addButton("no", R.strings.no);
		logoutConfirmDialog.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogClosed() {

			}
			
			@Override
			public void onDialogButtonClicked(String id) {
				if (!"yes".equals(id))
					return;
				Facebook.logout(loginCallback);
			}
		});
	}

	private void onLoginButtonTapped() {
		if (Facebook.isLoggedIn()) {
			logoutConfirmDialog.open(Ichigu.getString(R.strings.logoutConfirm));
		}
		else {
			Facebook.login(loginCallback);
		}
	}

	@Override
	public void draw() {
		if (button.isListeningInput())
			button.draw();
	}

	public void activate() {
		button.listenInput(true);
		update();
	}
	
	public void deactivate() {
		button.listenInput(false);
	}

	public void update() {
		if (Facebook.isLoggedIn()) {
			button.getColor().a = 0.5f;
		}
		else {
			button.getColor().a = 1f;
		}
	}
	
	private final ICallback loginCallback = new ICallback() {
		@Override
		public void onSuccess() {
			update();
		}
		
		@Override
		public void onFail(Throwable t) {
			
		}
	};
}
