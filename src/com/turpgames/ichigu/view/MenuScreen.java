package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.model.display.Logo;
import com.turpgames.ichigu.model.display.Toolbar;
import com.turpgames.ichigu.model.game.IchiguDialog;
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
		
		exitConfirm = new IchiguDialog();
		exitConfirm.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogButtonClicked(String id) {
				if (R.strings.yes.equals(id)) {
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
		exitConfirm.open(Game.getResourceManager().getString(R.strings.exitProgramConfirm));
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
	
//	@Override
//	public boolean keyDown(int keycode) {
//		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
//			if (formHistory.size() == 0) {
//				if ("true".equals(Game.getParam("allow-exit-on-back"))) {
//					Game.exit();
//				}
//			}
//			else {
//				setForm(formHistory.peek(), true);
//				return true;
//			}
//		}
//		return super.keyDown(keycode);
//	}
}