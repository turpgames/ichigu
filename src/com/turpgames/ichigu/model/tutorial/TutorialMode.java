package com.turpgames.ichigu.model.tutorial;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.ITutorialListener;

public class TutorialMode implements IDrawable {
	
	private IchiguTutorial tutorial;
	protected ITutorialListener modeListener;
	
	public TutorialMode() {
		tutorial = new IchiguTutorial(new ITutorialListener() {
			@Override
			public void onTutorialEnd() {
				notifyTutorialEnd();
			}
		});
	}

	public void setModeListener(ITutorialListener modeListener) {
		this.modeListener = modeListener;
	}
	
	private ITutorialListener getModeListener() {
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
