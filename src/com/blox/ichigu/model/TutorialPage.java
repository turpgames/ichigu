package com.blox.ichigu.model;

import java.util.ArrayList;
import java.util.List;

import com.blox.framework.v0.IView;
import com.blox.framework.v0.impl.GameObject;
import com.blox.framework.v0.impl.Text;
import com.blox.framework.v0.util.Game;
import com.blox.ichigu.utils.R;

public class TutorialPage implements IView {
	private final String id;
	private final List<Text> pageTexts;
	private final List<GameObject> images;

	public TutorialPage(String id) {
		this.id = id;
		this.pageTexts = new ArrayList<Text>();
		this.images = new ArrayList<GameObject>();
	}

	public Text addInfo(String info, int halign, float marginTop) {
		Text text = new Text();
		text.setWidth(Game.getVirtualWidth());
		text.setHeight(Game.getVirtualHeight());
		text.setHorizontalAlignment(halign);
		text.setVerticalAlignment(Text.VAlignTop);
		text.setText(info);
		text.setFontScale(R.fontSize.medium);

		if (pageTexts.size() > 0) {
			Text lastText = pageTexts.get(pageTexts.size() - 1);
			marginTop += lastText.getPadY() + lastText.getTextAreaHeight();
		}

		text.setPadY(marginTop);

		pageTexts.add(text);

		return text;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void draw() {
		for (int i = 0; i < pageTexts.size(); i++)
			pageTexts.get(i).draw();

		for (int i = 0; i < images.size(); i++)
			images.get(i).draw();
	}

	@Override
	public void activate() {

	}

	@Override
	public boolean deactivate() {
		return true;
	}

	public void addImage(GameObject obj) {
		images.add(obj);
	}
}
