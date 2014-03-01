package com.turpgames.ichigu.model.hiscores;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class OptionsButton implements IDrawable, ILanguageListener {
	private final List<String> options;
	private final List<IButtonListener> optionListeners;
	private final TextButton button;

	private int activeOptionIndex = 0;

	public OptionsButton() {
		options = new ArrayList<String>();
		optionListeners = new ArrayList<IButtonListener>();

		button = new TextButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		button.setLocation(Button.AlignSW, 0, 0);
		button.setFontScale(R.fontSize.small);
		button.setListener(new IButtonListener() {			
			@Override
			public void onButtonTapped() {
				activateNextOption();
			}
		});
		button.listenInput(false);

		Game.getLanguageManager().register(this);
	}

	public void addOption(String optionText, IButtonListener listener) {
		options.add(optionText);
		optionListeners.add(listener);
	}

	private void activateNextOption() {
		activeOptionIndex = (activeOptionIndex + 1) % options.size();		
		setLanguageSensitiveInfo();
		optionListeners.get(activeOptionIndex).onButtonTapped();
	}

	public float getWidth() {
		return button.getWidth();
	}

	public void setLocation(float x, float y) {
		button.getLocation().set(x, y);
	}

	public void activate() {
		button.listenInput(true);
		setLanguageSensitiveInfo();
	}

	public void deactivate() {
		activeOptionIndex = 0;
		button.listenInput(false);
	}

	private void setLanguageSensitiveInfo() {
		button.setText(Ichigu.getString(options.get(activeOptionIndex)));
	}

	@Override
	public void draw() {
		button.draw();
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}
}