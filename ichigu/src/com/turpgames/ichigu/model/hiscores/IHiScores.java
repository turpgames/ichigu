package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.IView;

interface IHiScores extends IView, ILanguageListener {
	public final static IHiScores NULL = new IHiScores() {
		
		@Override
		public void onLanguageChanged() {
			
		}
		
		@Override
		public void draw() {
			
		}
		
		@Override
		public boolean deactivate() {
			return true;
		}
		
		@Override
		public void activate() {
			
		}

		@Override
		public String getId() {
			return "null hiscores";
		}
	};
}
