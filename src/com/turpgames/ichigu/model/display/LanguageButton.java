package com.turpgames.ichigu.model.display;

import java.util.Map;

import com.turpgames.framework.v0.metadata.GameMetadata;
import com.turpgames.framework.v0.metadata.LanguageMetadata;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class LanguageButton extends ToolbarButton {

	private String id;
	private String country;
	
	protected LanguageButton() {
		super();
		setWidth(Game.scale(R.ui.flagButtonWidth));
		setHeight(Game.scale(R.ui.flagButtonHeight));
	}
	
	public static LanguageButton load(String languageId) {
		LanguageMetadata metadata = GameMetadata.getLanguage(languageId);
		if (metadata == null)
			return null;

		LanguageButton btn = new LanguageButton();
		btn.initAttributes(metadata.getAttributes());
		return btn;
	}
	
	public String getId() {
		return id;
	}
	
	public String getCountry() {
		return country;
	}
	
	protected void setAttribute(String attribute, String value) {
		if ("id".equals(attribute))
			id = value;
		else if ("country".equals(attribute))
			country = value;
		else if ("flag-texture-id".equals(attribute))
			setTexture(value);
	}
	
	private void initAttributes(Map<String, String> attributes) {
		for (String key : attributes.keySet())
			setAttribute(key, attributes.get(key));
	}
	
	@Override
	protected boolean onTap() {
		Game.getLanguageManager().changeLanguage(id, country);
		return super.onTap();
	}
}
