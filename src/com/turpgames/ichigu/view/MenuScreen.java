package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.display.IchiguDialog;
import com.turpgames.ichigu.model.display.IchiguLanguageMenu;
import com.turpgames.ichigu.model.display.IchiguToolbar;
import com.turpgames.ichigu.model.display.Logo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class MenuScreen extends FormScreen implements IGameExitListener {
	private Dialog exitConfirm;
	private LanguageMenu languageBar;
	
	@Override
	public void init() {
		super.init();

		languageBar = new IchiguLanguageMenu();
		languageBar.setListener(new LanguageMenu.ILanguageMenuListener() {
			
			@Override
			public void onLanguageMenuDeactivated() {
				getCurrentForm().enable();
				IchiguToolbar.getInstance().enable();
			}
			
			@Override
			public void onLanguageMenuActivated() {
				getCurrentForm().disable();
				IchiguToolbar.getInstance().disable();		
			}
		});
		
		Dialog.clickSound = Game.getResourceManager().getSound(R.game.sounds.flip);
		
		Game.exitListener = this;
		
		Logo logo = new Logo();
		logo.getColor().a = 0.25f;
		registerDrawable(logo, Utils.LAYER_BACKGROUND);
		registerDrawable(IchiguGame.getToolbar(), Utils.LAYER_SCREEN);
		
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
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (getCurrentForm().getId().equals(R.game.forms.playMenu)) {
			IchiguToolbar.getInstance().activateBackButton();
			unregisterDrawable(languageBar);
			languageBar.listenInput(false);
		}
		else if (getCurrentForm().getId().equals(R.game.forms.mainMenu)) {
			IchiguToolbar.getInstance().deactivateBackButton();
			registerDrawable(languageBar, 1);
			languageBar.listenInput(true);
		}
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		unregisterDrawable(languageBar);
		languageBar.listenInput(false);
		return super.onBeforeDeactivate();
	}
	
	@Override
	public boolean onGameExit() {
		if (languageBar.isActive()) {
			languageBar.deactivate();
			return false;
		}
		else {
			exitConfirm.open(Ichigu.getString(R.strings.exitProgramConfirm));
			return false;
		}
	}
	
	@Override
	protected void onFormActivated(Form activatedForm) {
		if (activatedForm.getId().equals(R.game.forms.playMenu)) {
			IchiguToolbar.getInstance().activateBackButton();
			unregisterDrawable(languageBar);
			languageBar.listenInput(false);
		}
		else if (activatedForm.getId().equals(R.game.forms.mainMenu)) {
			IchiguToolbar.getInstance().deactivateBackButton();
			registerDrawable(languageBar, 1);
			languageBar.listenInput(true);
		}
		super.onFormActivated(activatedForm);
	}
}