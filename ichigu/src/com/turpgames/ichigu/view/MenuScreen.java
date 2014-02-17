package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.IchiguLanguageMenu;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.display.Logo;
import com.turpgames.ichigu.utils.Facebook;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class MenuScreen extends FormScreen implements IGameExitListener {
	private Dialog exitConfirm;
	private LanguageMenu languageBar;
	private boolean isFirstActivate;

	@Override
	public void init() {
		super.init();

		isFirstActivate = true;

		languageBar = new IchiguLanguageMenu();
		languageBar.setListener(new LanguageMenu.ILanguageMenuListener() {
			@Override
			public void onLanguageMenuShown() {
				getCurrentForm().disable();
				IchiguToolbar.getInstance().disable();
			}

			@Override
			public void onLanguageMenuHidden() {
				getCurrentForm().enable();
				IchiguToolbar.getInstance().enable();
				ensureBackButton(getCurrentForm());
			}
		});

		Dialog.clickSound = Game.getResourceManager().getSound(R.game.sounds.flip);

		Game.exitListener = this;

		Logo logo = new Logo();
		logo.getColor().a = 0.50f;
		GameObject bg = new GameObject() {
			ITexture texture = Game.getResourceManager().getTexture(R.game.textures.backgroundPixel);

			@Override
			public void draw() {
				TextureDrawer.draw(texture, this);
			}

			@Override
			public boolean ignoreViewport() {
				return true;
			}
		};
		bg.setWidth(Game.getScreenWidth());
		bg.setHeight(Game.getScreenHeight());
		bg.getColor().set(R.colors.ichiguBlack);
		registerDrawable(bg, Game.LAYER_BACKGROUND);
		registerDrawable(logo, Game.LAYER_BACKGROUND);
		registerDrawable(IchiguGame.getToolbar(), Game.LAYER_SCREEN);

		exitConfirm = new IchiguDialog();
		exitConfirm.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if (R.strings.yes.equals(id)) {
					Game.exitListener = null;
					Game.exit();
				}
			}

			@Override
			public void onDialogClosed() {

			}
		});

		setForm(R.game.forms.mainMenu, false);
	}

	@Override
	public boolean onGameExit() {
		exitConfirm.open(Ichigu.getString(R.strings.exitProgramConfirm));
		return false;
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();

		languageBar.activate();

		ensureBackButton(getCurrentForm());

		if (isFirstActivate) {
			isFirstActivate = false;
			announceFacebook();
			forceUpgrade();
			loginWithFacebook();
		}
	}
	
	private void announceFacebook() {
		Version v120 = new Version("1.2.0");
		if (Game.getVersion().compareTo(v120) < 0)
			return;
		
		boolean facebookAnnounced = Settings.getBoolean("facebook-announced", false);
		if (facebookAnnounced)
			return;
		
		Dialog dlg = new Dialog();
		dlg.addButton("yes", R.strings.yes);
		dlg.addButton("no", R.strings.no);
		dlg.setFontScale(R.fontSize.medium);
		dlg.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogClosed() {

			}
			
			@Override
			public void onDialogButtonClicked(String id) {
				if (!"yes".equals(id))
					return;
				Facebook.login(new ICallback() {
					@Override
					public void onSuccess() {
						Settings.putBoolean("facebook-announced", true);
					}
					
					@Override
					public void onFail(Throwable t) {
						
					}
				});
			}
		});
		dlg.open(Ichigu.getString(R.strings.announceFacebook));
	}
	
	private void forceUpgrade() {
		if (!Game.isAndroid())
			return;
		
		if (!"1.1.3".equals(Game.getVersion().toString()))
			return;
		
		Dialog dlg = new Dialog();
		dlg.addButton("ok", R.strings.ok);
		dlg.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogClosed() {
				Game.exitListener = null;
				Game.exit();
			}
			
			@Override
			public void onDialogButtonClicked(String id) {
				Game.openUrl(Ichigu.getStoreUrl());
				Game.exitListener = null;
				Game.exit();
			}
		});
		dlg.open(Ichigu.getString(R.strings.forceUpgrade));
	}

	private void loginWithFacebook() {
		if (Facebook.canLogin() && !Facebook.isLoggedIn()) {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
				}

				@Override
				public void onFail(Throwable t) {
				}
			});
		}
	}

	@Override
	protected boolean onBeforeDeactivate() {
		languageBar.deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected void onFormActivated(Form activatedForm) {
		ensureBackButton(activatedForm);
		super.onFormActivated(activatedForm);
	}

	private static void ensureBackButton(Form currentForm) {
		if (currentForm.getId().equals(R.game.forms.playMenu)) {
			IchiguToolbar.getInstance().activateBackButton();
		}
		else if (currentForm.getId().equals(R.game.forms.mainMenu)) {
			IchiguToolbar.getInstance().deactivateBackButton();
		}
	}
}