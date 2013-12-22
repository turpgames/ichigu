package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.CountDownTimer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.ichigu.utils.R;

public class SingleGameQuestion extends GameObject implements IEffectEndListener {

	class Mark extends GameObject implements IFadingEffectSubject {
		private ITexture texture;
		private FadeOutEffect effect;
		
		protected Mark(IEffectEndListener parent, String path, Color color) {
			this.texture = Game.getResourceManager().getTexture(path);
			getColor().set(color);
			setWidth(R.sizes.questionMarkSize);
			setHeight(R.sizes.questionMarkSize);
			getLocation().set(R.singleGameMode.markPos);
			
			effect = new FadeOutEffect(this);
			effect.setDuration(0.2f);
			effect.setListener(parent);
		}

		@Override
		public void draw() {
			TextureDrawer.draw(texture, this);
		}

		@Override
		public void registerSelf() {
			
		}
		
		@Override
		public void setAlpha(float alpha) {
			getColor().a = alpha;
		}

		public void start() {
			effect.start();
		}
		
		public void stop() {
			effect.stop();
		}
	}
	
	private Mark questionMark;
	private Mark correctMark;
	private Mark incorrectMark;
	
	private CountDownTimer correctEffectStartTimer;
	private CountDownTimer incorrectEffectStartTimer;
	
	public SingleGameQuestion(float correctTime, float incorrectTime) {
		questionMark = new Mark(this, R.game.textures.singlegame.questionmark, R.colors.ichiguYellow);
		correctMark = new Mark(this, R.game.textures.singlegame.correctmark, R.colors.ichiguGreen);
		incorrectMark = new Mark(this, R.game.textures.singlegame.incorrectmark, R.colors.ichiguRed);

		questionMark.setAlpha(1);
		correctMark.setAlpha(0);
		incorrectMark.setAlpha(0);

		correctEffectStartTimer = new CountDownTimer(correctTime);
		incorrectEffectStartTimer = new CountDownTimer(incorrectTime);
	}
	
	@Override
	public void draw() {
		questionMark.draw();
		correctMark.draw();
		incorrectMark.draw();
	}
	
	@Override
	public boolean onEffectEnd(Object obj) {
		questionMark.setAlpha(1);
		correctEffectStartTimer.stop();
		incorrectEffectStartTimer.stop();
		return false;
	}

	@Override
	public void registerSelf() {
		
	}
	
	public void startCorrectEffect() {
		questionMark.setAlpha(0);
		correctMark.setAlpha(1);
		incorrectMark.setAlpha(0);
		incorrectEffectStartTimer.stop();
		incorrectMark.stop();
		correctEffectStartTimer.setCountDownListener(new CountDownTimer.ICountDownListener() {
			@Override
			public void onCountDownEnd(CountDownTimer timer) {
				correctMark.start();
			}
		});
		correctEffectStartTimer.start();
	}

	public void startIncorrectEffect() {
		questionMark.setAlpha(0);
		correctMark.setAlpha(0);
		incorrectMark.setAlpha(1);
		correctEffectStartTimer.stop();
		correctMark.stop();
		incorrectEffectStartTimer.setCountDownListener(new CountDownTimer.ICountDownListener() {
			@Override
			public void onCountDownEnd(CountDownTimer timer) {
				incorrectMark.start();
			}
		});
		incorrectEffectStartTimer.start();
	}

}
