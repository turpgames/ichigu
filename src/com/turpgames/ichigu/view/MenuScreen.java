package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.Logo;
import com.turpgames.ichigu.model.Toolbar;
import com.turpgames.ichigu.utils.R;

public class MenuScreen extends FormScreen implements IGameExitListener {
	private Dialog exitConfirm;
	
	@Override
	public void init() {
		super.init();

		Dialog.clickSound = Game.getResourceManager().getSound(R.game.sounds.flip);
		
		Game.exitListener = this;
		
		Logo logo = new Logo();
		logo.getColor().a = 0.25f;
		registerDrawable(logo, 1);
		registerDrawable(IchiguGame.getToolbar(), 1);
		
		exitConfirm = new Dialog();
		exitConfirm.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogButtonClicked(String id) {
				if ("Yes".equals(id)) {
					Game.exitListener = null;
					Game.exit();		
				}
			}
		});

		setForm(R.game.forms.mainMenu, false);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		if (getCurrentForm().getId().equals(R.game.forms.playMenu))
			Toolbar.getInstance().activateBackButton();
		else if (getCurrentForm().getId().equals(R.game.forms.mainMenu))
			Toolbar.getInstance().deactivateBackButton();
	}
	
	@Override
	public boolean onGameExit() {
		exitConfirm.open("Are you sure you want to exit?");
		return false;
	}
	
	@Override
	protected void onFormActivated(Form activatedForm) {
		if (activatedForm.getId().equals(R.game.forms.playMenu))
			Toolbar.getInstance().activateBackButton();
		else if (activatedForm.getId().equals(R.game.forms.mainMenu))
			Toolbar.getInstance().deactivateBackButton();
		super.onFormActivated(activatedForm);
	}
}