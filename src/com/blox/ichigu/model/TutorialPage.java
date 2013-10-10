package com.blox.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.blox.framework.v0.IDrawingInfo;
import com.blox.framework.v0.IView;
import com.blox.framework.v0.util.FontManager;
import com.blox.framework.v0.util.TextDrawer;

public class TutorialPage implements IView {

	protected String id;
	protected String text;
	protected boolean isVisible;
	protected List<IchiguObject> images;
	
	public TutorialPage(String id, String text) {
		this.id = id;
		this.text = text;
		this.images = new ArrayList<IchiguObject>();
		setVisible(true);
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void render() {
		TextDrawer.draw(FontManager.defaultFont, text, IDrawingInfo.viewport, TextDrawer.AlignN);
		for(IchiguObject obj : images)
			obj.draw();
	}
	
	@Override
	public void activated() {
		setVisible(true);
	}

	@Override
	public void deactivated() {
		setVisible(false);
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	
	public void addImage(IchiguObject obj) {
		images.add(obj);
	}
}
