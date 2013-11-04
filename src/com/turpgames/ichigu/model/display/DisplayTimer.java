package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.effects.flash.FlashEffect;
import com.turpgames.framework.v0.effects.flash.IFlashEffectSubject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Utils;

public class DisplayTimer implements IDrawable, IFlashEffectSubject {
	private FlashEffect flashEffect;
	
	private Text colon;
	private Text minutes;
	private Text seconds;
	
	private int colonAlignment;
	
	public DisplayTimer(Color flashColor, int flashPerSecond, float duration) {
		flashEffect = new FlashEffect(this, flashColor, flashPerSecond);
		flashEffect.setDuration(duration);
		
		colon = new Text();
		minutes = new Text();
		seconds = new Text();
		colon.setWidth(Game.getVirtualWidth());
		colon.setHeight(Game.getVirtualHeight());
		minutes.setWidth(Game.getVirtualWidth());
		minutes.setHeight(Game.getVirtualHeight());
		seconds.setWidth(Game.getVirtualWidth());
		seconds.setHeight(Game.getVirtualHeight());
		
		colon.setText(":");
		colonAlignment = Text.HAlignCenter;
		colon.setHorizontalAlignment(colonAlignment);
		minutes.setHorizontalAlignment(Text.HAlignRight);
		seconds.setHorizontalAlignment(Text.HAlignLeft);
	}
	
	public void setTimeText(int time) {
		String s = Utils.getTimeString(time);
		minutes.setText(s.substring(0,2));
		seconds.setText(s.substring(3));
	}
	
	public void locate(int halign, int valign) {
		colonAlignment = halign;
		colon.setHorizontalAlignment(halign);
		colon.setVerticalAlignment(valign);
		minutes.setVerticalAlignment(valign);
		seconds.setVerticalAlignment(valign);
	}

	public void setPadding(float padX, float padY) {
		switch (colonAlignment) {
		case Text.HAlignCenter:
			minutes.setPadX((Game.getVirtualWidth() + colon.getTextAreaWidth()) / 2);
			seconds.setPadX((Game.getVirtualWidth() + colon.getTextAreaWidth())/ 2);
			break;
		case Text.HAlignRight:
			padX += 50;
			colon.setPadX(padX);
			minutes.setPadX(padX + colon.getTextAreaWidth());
			seconds.setPadX(Game.getVirtualWidth() - padX);
			break;
		default:
			padX += 50;
			colon.setPadX(padX);
			minutes.setPadX(Game.getVirtualWidth() - padX);
			seconds.setPadX(padX + colon.getTextAreaWidth());			
			break;
		}
		
		colon.setPadY(padY);
		minutes.setPadY(padY);
		seconds.setPadY(padY);
	}
	
	public void start() {
		flashEffect.start();
	}
	
	public void stop() {
		flashEffect.stop();
	}
	
	@Override
	public void draw() {
		colon.draw();
		minutes.draw();
		seconds.draw();
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		colon.getColor().set(r,g,b,a);
		minutes.getColor().set(r,g,b,a);
		seconds.getColor().set(r,g,b,a);
	}

	@Override
	public Color getColor() {
		return colon.getColor();
	}
}
