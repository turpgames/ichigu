package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.effects.fading.FadeOutEffect;
import com.turpgames.framework.v0.effects.fading.IFadingEffectSubject;
import com.turpgames.framework.v0.impl.AttachedText;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Utils;
import com.turpgames.ichigu.model.game.CardAttributes;
import com.turpgames.ichigu.utils.R;

public class FadingPointsInfo extends GameObject implements IFadingEffectSubject {
	private Timer timer;
	private FadeOutEffect fadeOutEffect;
	private boolean isActive;
	private float millis = 4000;

	class PointInfo extends GameObject implements IFadingEffectSubject {
		static final int pointImageSize = 64;
		ITexture texture;
		AttachedText text;
		AttachedText extraText;

		PointInfo() {
			this.text = new AttachedText(this);
			this.text.setHorizontalAlignment(Text.HAlignCenter);
//			this.text.setVerticalAlignment(Text.VAlignCenter);
//			this.text.setPadX(30);
			this.text.setPadY(-15);
			this.text.setWrapped(false);
			
			this.extraText = new AttachedText(this);
			this.extraText.setPadY(-15);
			this.extraText.setText(" +");
			
			setWidth(pointImageSize);
			setHeight(pointImageSize);
		}

		public void setText(String text) {
			this.text.setText(text);
			this.extraText.setPadX(60);			
		}
		
		public void setExtraText(String text) {
			this.extraText.setText(text);
		}

		public void setTexture(ITexture texture) {
			this.texture = texture;
		}

		public float getTotalWidth() {
			return 60 + this.extraText.getTextAreaWidth();
		}
		
		@Override
		public void draw() {
			TextureDrawer.draw(texture, this);
			this.text.draw();
			this.extraText.draw();
		}

		@Override
		public void setAlpha(float alpha) {
			this.text.getColor().a = alpha;
			this.extraText.getColor().a = alpha;
			getColor().a = alpha;
		}

		@Override
		public void registerSelf() {
			Game.getInputManager().register(this, Utils.LAYER_INFO);
		}
	}

	int points;
	PointInfo shapeInfo;
	PointInfo colorInfo;
	PointInfo patternInfo;
	PointInfo countInfo;

	private void setPoints(CardAttributes[] attr) {
		points = 0;
		
		if (CardAttributes.isSameColor(attr[0], attr[1], attr[2])) {
			points += 1;
			colorInfo.setText("1");
			colorInfo.setTexture(Game.getResourceManager().getTexture(R.game.textures.points.colorone));
			colorInfo.getColor().set(
					attr[0].getColor() == CardAttributes.Color_Blue ? R.colors.ichiguBlue : attr[0].getColor() == CardAttributes.Color_Green ? R.colors.ichiguGreen : R.colors.ichiguRed);
		} else {
			points += 3;
			colorInfo.setText("3");
			colorInfo.setTexture(Game.getResourceManager().getTexture(R.game.textures.points.colorall));
			colorInfo.getColor().set(1, 1, 1);
		}
		
		if (CardAttributes.isSameShape(attr[0], attr[1], attr[2])) {
			points += 1;
			shapeInfo.setText("1");
			shapeInfo.setTexture(Game.getResourceManager().getTexture(
					attr[0].getShape() == CardAttributes.Shape_Circle ? R.game.textures.points.shapecircle : attr[0].getShape() == CardAttributes.Shape_Square ? R.game.textures.points.shaperectangle
							: R.game.textures.points.shapetriangle));
		} else {
			points += 3;
			shapeInfo.setText("3");
			shapeInfo.setTexture(Game.getResourceManager().getTexture(R.game.textures.points.shapeall));
		}
		
		if (CardAttributes.isSamePattern(attr[0], attr[1], attr[2])) {
			points += 1;
			patternInfo.setText("1");
			patternInfo.setTexture(Game.getResourceManager().getTexture(
					attr[0].getPattern() == CardAttributes.Pattern_Empty ? R.game.textures.points.fillempty : attr[0].getPattern() == CardAttributes.Pattern_Filled ? R.game.textures.points.fillfull
							: R.game.textures.points.fillstriped));
		} else {
			points += 3;
			patternInfo.setText("3");
			patternInfo.setTexture(Game.getResourceManager().getTexture(R.game.textures.points.fillall));
		}

		if (CardAttributes.isSameCount(attr[0], attr[1], attr[2])) {
			points += 1;
			countInfo.setText("1");
			countInfo.setTexture(Game.getResourceManager().getTexture(
					attr[0].getCount() == CardAttributes.Count_1 ? R.game.textures.points.countone : attr[0].getCount() == CardAttributes.Count_2 ? R.game.textures.points.counttwo
							: R.game.textures.points.countthree));
		} else {
			points += 3;
			countInfo.setText("3");
			countInfo.setTexture(Game.getResourceManager().getTexture(R.game.textures.points.countall));
		}
		countInfo.setExtraText(" = " + points);
		
		setPointInfosPositions();
	}

	public FadingPointsInfo() {
		this.setWidth(Game.getVirtualWidth());
		this.setHeight(PointInfo.pointImageSize + 10);
		this.getLocation().set(0, Game.getVirtualHeight() - this.getHeight());
		this.getColor().set(R.colors.ichiguRed);

		timer = new Timer();
		timer.setTickListener(timerListener);

		fadeOutEffect = new FadeOutEffect(this);
		fadeOutEffect.setMinAlpha(0);
		fadeOutEffect.setMaxAlpha(1);
		fadeOutEffect.setListener(effectListener);

		initPointInfos();
	}

	public void initPointInfos() {
		colorInfo = new PointInfo();

		shapeInfo = new PointInfo();
		shapeInfo.getColor().set(R.colors.ichiguYellow);
		
		patternInfo = new PointInfo();
		patternInfo.getColor().set(R.colors.ichiguYellow);

		countInfo = new PointInfo();
		countInfo.getColor().set(R.colors.ichiguYellow);
	}
	
	public void setPointInfosPositions() {
		int totalWidth = (int) (3 * PointInfo.pointImageSize + countInfo.getTotalWidth());
		int x = (int) (Game.getVirtualWidth() - totalWidth) / 2;
		int y = (int) this.getLocation().y;
		colorInfo.getLocation().set(x, y);
		shapeInfo.getLocation().set(x + PointInfo.pointImageSize, y);
		patternInfo.getLocation().set(x + 2 * PointInfo.pointImageSize, y);
		countInfo.getLocation().set(x + 3 * PointInfo.pointImageSize, y);
	}

	public void show(CardAttributes[] attr) {
		if (isActive) {
			timer.start();
			fadeOutEffect.stop();
			setAlpha(1);
		}

		this.isActive = true;

		setPoints(attr);

		timer.setInterval(millis / 1000f);

		fadeOutEffect.setDuration(millis / 1000f);
		fadeOutEffect.start();

		this.listenInput(true);
		Drawer.getCurrent().register(this, Utils.LAYER_INFO);
	}

	public void dispose() {
		hide();
		fadeOutEffect.stop();
	}

	public void hide() {
		if (!isActive)
			return;

		this.isActive = false;
		timer.stop();

		this.listenInput(false);
	}

	private void effectEnd() {
		if (isActive)
			timer.start();
		else if (Drawer.getCurrent() != null)
			Drawer.getCurrent().unregister(this);
	}

	private Timer.ITimerTickListener timerListener = new Timer.ITimerTickListener() {
		@Override
		public void timerTick(Timer timer) {
			hide();
		}
	};

	private IEffectEndListener effectListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			effectEnd();
			return true;
		}
	};

	@Override
	public void draw() {
		shapeInfo.draw();
		colorInfo.draw();
		countInfo.draw();
		patternInfo.draw();
	}

	@Override
	protected boolean onTap() {
		hide();
		return true;
	}

	@Override
	public void setAlpha(float alpha) {
		getColor().a = alpha;
		shapeInfo.setAlpha(alpha);
		colorInfo.setAlpha(alpha);
		countInfo.setAlpha(alpha);
		patternInfo.setAlpha(alpha);
	}

	@Override
	public void registerSelf() {
		Game.getInputManager().register(this, Utils.LAYER_INFO);
	}
}
