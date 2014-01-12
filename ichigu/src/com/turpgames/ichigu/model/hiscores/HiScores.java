package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.IViewFinder;
import com.turpgames.framework.v0.IViewSwitcher;
import com.turpgames.framework.v0.impl.FadingViewSwitcher;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.ichigu.social.Facebook;

public class HiScores implements IHiScores {
	private IHiScores hiscores = IHiScores.NULL;

	private final IHiScores onlineHiScores;
	private final IHiScores offlineHiScores;
	private final IViewSwitcher viewSwitcher;

	public HiScores() {
		onlineHiScores = new OnlineHiScores(this);
		offlineHiScores = new OfflineHiScores(this);

		viewSwitcher = new FadingViewSwitcher(0.5f);
		viewSwitcher.setViewFinder(viewFinder);
		updateView();
	}

	@Override
	public void activate() {
		if (!Facebook.isLoggedIn() && Facebook.canLogin()) {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					updateView();
				}

				@Override
				public void onFail(Throwable t) {
					updateView();;
				}
			});
		}
		else {
			updateView();
		}
	}

	@Override
	public boolean deactivate() {
		return hiscores.deactivate();
	}

	@Override
	public void draw() {
		viewSwitcher.draw();
	}

	void updateView() {
		hiscores = Facebook.isLoggedIn() ? onlineHiScores : offlineHiScores;
		viewSwitcher.switchTo(hiscores.getId(), hiscores == offlineHiScores);
	}

	@Override
	public void onLanguageChanged() {
		// IHiScore objesi kendi handle ediyor zaten
	}

	@Override
	public String getId() {
		return "HiScores";
	}

	private IViewFinder viewFinder = new IViewFinder() {
		@Override
		public IView findView(String id) {
			if (id.equals(offlineHiScores.getId()))
				return offlineHiScores;
			if (id.equals(onlineHiScores.getId()))
				return onlineHiScores;
			return IHiScores.NULL;
		}
	};
}