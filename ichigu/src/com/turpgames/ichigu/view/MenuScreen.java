package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
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
			public void onLanguageMenuActivated() {
				getCurrentForm().disable();
				IchiguToolbar.getInstance().disable();		
			}
			
			@Override
			public void onLanguageMenuDeactivated() {
				getCurrentForm().enable();
				IchiguToolbar.getInstance().enable();
			}
		});
		registerDrawable(languageBar, Utils.LAYER_SCREEN);
		languageBar.listenInput(true);
		
		Dialog.clickSound = Game.getResourceManager().getSound(R.game.sounds.flip);
		
		Game.exitListener = this;
		
		Logo logo = new Logo();
		logo.getColor().a = 0.35f;
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
			@Override
			public void registerSelf() {
				
			}
		};
		bg.setWidth(Game.getScreenWidth());
		bg.setHeight(Game.getScreenHeight());
		bg.getColor().set(R.colors.ichiguYellow);
		registerDrawable(bg, Utils.LAYER_BACKGROUND);
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
	protected void onAfterActivate() {
		super.onAfterActivate();
		registerDrawable(languageBar, Utils.LAYER_SCREEN);
		languageBar.listenInput(true);
		if (getCurrentForm().getId().equals(R.game.forms.playMenu)) {
			IchiguToolbar.getInstance().activateBackButton();
//			unregisterDrawable(languageBar);
//			languageBar.listenInput(false);
		}
		else if (getCurrentForm().getId().equals(R.game.forms.mainMenu)) {
			IchiguToolbar.getInstance().deactivateBackButton();
//			registerDrawable(languageBar, 1);
//			languageBar.listenInput(true);
		}
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		unregisterDrawable(languageBar);
		languageBar.listenInput(false);
		return super.onBeforeDeactivate();
	}
	
	@Override
	protected void onFormActivated(Form activatedForm) {
		if (activatedForm.getId().equals(R.game.forms.playMenu)) {
			IchiguToolbar.getInstance().activateBackButton();
//			unregisterDrawable(languageBar);
//			languageBar.listenInput(false);
		}
		else if (activatedForm.getId().equals(R.game.forms.mainMenu)) {
			IchiguToolbar.getInstance().deactivateBackButton();
//			registerDrawable(languageBar, 1);
//			languageBar.listenInput(true);
		}
		super.onFormActivated(activatedForm);
	}
}