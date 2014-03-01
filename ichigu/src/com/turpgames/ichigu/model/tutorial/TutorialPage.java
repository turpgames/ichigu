package com.turpgames.ichigu.model.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class TutorialPage implements IView {
	private final String id;
	private final List<Text> texts;
	private final List<GameObject> objects;
	private final float fontScale;

	public TutorialPage(String id, float fontScale) {
		this.id = id;
		this.texts = new ArrayList<Text>();
		this.objects = new ArrayList<GameObject>();
		this.fontScale = fontScale;
	}

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public Text addText(String info, int halign, float marginTop) {
		Text text = new Text();
		text.setWidth(Game.getVirtualWidth());
		text.setHeight(Game.getVirtualHeight());
		text.setHorizontalAlignment(halign);
		text.setVerticalAlignment(Text.VAlignTop);
		text.setText(info);
		text.setFontScale(this.fontScale);

		if (texts.size() > 0) {
			Text lastText = texts.get(texts.size() - 1);
			marginTop += lastText.getPadY() + lastText.getTextAreaHeight();
		}

		text.setPadY(marginTop);

		texts.add(text);

		return text;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void draw() {
		for (int i = 0; i < texts.size(); i++)
			texts.get(i).draw();

		for (int i = 0; i < objects.size(); i++)
			objects.get(i).draw();
	}

	@Override
	public void activate() {

	}

	@Override
	public boolean deactivate() {
		return true;
	}
}
