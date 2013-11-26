package com.turpgames.ichigu.view;

import com.turpgames.framework.v0.component.ITutorialListener;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.tutorial.TutorialMode;
import com.turpgames.ichigu.utils.R;

public class TutorialModeScreen extends IchiguScreen {
	private TutorialMode tutorial;

	@Override
	public void init() {
		super.init();
		tutorial = new TutorialMode();
		tutorial.beginTutorial();
		tutorial.setModeListener(new ITutorialListener() {
			@Override
			public void onModeEnd() {
				ScreenManager.instance.switchTo(R.game.screens.practice, true);
			}
		});
		registerDrawable(tutorial, Utils.LAYER_GAME);
	}

	@Override
	protected boolean onBeforeActivate() {
		tutorial.beginTutorial();
		return super.onBeforeActivate();
	}

	@Override
	protected void onAfterDeactivate() {
		tutorial.endTutorial();
		super.onAfterDeactivate();
	}
}