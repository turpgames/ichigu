package com.turpgames.ichigu.model.hiscores;

import com.turpgames.ichigu.social.Facebook;

public class HiScores implements IHiScores {
	private IHiScores hiscores = IHiScores.NULL;

	@Override
	public void activate() {
		hiscores = Facebook.isLoggedIn() ? OnlineHiScores.instance : OfflineHiScores.instance;
		hiscores.activate();
	}

	@Override
	public void deactivate() {
		hiscores.deactivate();
	}

	@Override
	public void draw() {
		hiscores.draw();
	}

	@Override
	public void onLanguageChanged() {
		// IHiScore objesi kendi handle ediyor zaten
	}
}