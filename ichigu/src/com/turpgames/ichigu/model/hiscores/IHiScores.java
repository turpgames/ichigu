package com.turpgames.ichigu.model.hiscores;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;

interface IHiScores extends IDrawable, ILanguageListener {
	public final static IHiScores NULL = new IHiScores() {
		
		@Override
		public void onLanguageChanged() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void draw() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deactivate() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void activate() {
			// TODO Auto-generated method stub
			
		}
	};
	
	void activate();

	void deactivate();
}
