package com.turpgames.ichigu.model.tutorial;

import com.turpgames.framework.v0.IDrawable;

public class TutorialMode implements IDrawable {
	
	private Tutorial tutorial;
	protected ITutorialModeListener modeListener;
	
	public TutorialMode() {
		tutorial = new Tutorial(new ITutorialModeListener() {
			@Override
			public void onTutorialEnd() {
				notifyTutorialEnd();
			}
		});
	}

	public void setModeListener(ITutorialModeListener modeListener) {
		this.modeListener = modeListener;
	}
	
	private ITutorialModeListener getModeListener() {
		return modeListener;
	}

	private void notifyTutorialEnd() {
		if (getModeListener() != null)
			getModeListener().onTutorialEnd();
	}
	
	public void beginTutorial() {
		tutorial.start();
	}
	
	public void endTutorial() {
		tutorial.end();
		// Change to practice screen
	}

	@Override
	public void draw() {
		tutorial.draw();
	}
}
