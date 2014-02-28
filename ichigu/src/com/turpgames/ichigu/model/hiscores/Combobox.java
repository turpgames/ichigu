package com.turpgames.ichigu.model.hiscores;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.ILanguageListener;
import com.turpgames.framework.v0.component.Button;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.GameUtils;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class Combobox implements IDrawable, ILanguageListener, IInputListener {
	static class ComboButton extends TextButton {
		public ComboButton(Color defaultColor, Color touchedColor) {
			super(defaultColor, touchedColor);
		}

		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
			setText(Ichigu.getString(id));
		}
	}

	public static interface IComboboxListener {
		void onSelectedOptionChanged(String id);
	}
	
	private final List<ComboButton> options;
	private IComboboxListener listener;

	private ComboButton activeOption;
	private boolean isExpanded;

	public Combobox() {
		options = new ArrayList<ComboButton>();
		Game.getLanguageManager().register(this);
	}

	public IComboboxListener getListener() {
		return listener;
	}

	public void setListener(IComboboxListener listener) {
		this.listener = listener;
	}

	public void addOption(String id, final IButtonListener listener) {
		final ComboButton button = new ComboButton(R.colors.ichiguYellow, R.colors.ichiguBlack);
		button.setLocation(Button.AlignSW, 0, 0);
		button.setFontScale(R.fontSize.small);
		button.setId(id);
		button.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				onOptionTapped(button);
				listener.onButtonTapped();
			}
		});
		button.listenInput(false);
		options.add(button);
		
		if (activeOption == null)
			activeOption = button;
	}

	private void onOptionTapped(ComboButton option) {
		if (!isExpanded) {
			expandOptions();
		}
		else if (option == activeOption) {
			collapseOptions();
		}
		else {
			selectOption(option);
		}
	}
	
	private void expandOptions() {
		isExpanded = true;
		int i = 1;
		for (ComboButton opt : options) {
			if (opt != activeOption) {
				opt.getLocation().y = activeOption.getLocation().y - i * 30;
				opt.getLocation().x = activeOption.getLocation().x - (opt.getWidth() - activeOption.getWidth()) / 2;
				opt.listenInput(true);
				opt.getColor().a = 0.5f;
				i++;
			}
		}
	}
	
	private void collapseOptions() {
		isExpanded = false;
		for (ComboButton opt : options) {
			if (opt != activeOption) {
				opt.listenInput(false);
			}
		}
	}
	
	private void selectOption(ComboButton option) {
		option.getLocation().y = activeOption.getLocation().y;
		option.getColor().a = 1;
		activeOption = option;
		collapseOptions();
		
		if (listener != null)
			listener.onSelectedOptionChanged(option.getId());
	}

	public float getWidth() {
		return activeOption.getWidth();
	}

	public void setLocation(float x, float y) {
		activeOption.getLocation().set(x, y);
	}

	public void activate() {
		activeOption.listenInput(true);
		setLanguageSensitiveInfo();
		Game.getInputManager().register(this, Game.LAYER_DIALOG);
	}

	public void deactivate() {
		activeOption.listenInput(false);
		Game.getInputManager().unregister(this);
	}
	
	@Override
	public void draw() {
		activeOption.draw();
		if (isExpanded) {
			for (ComboButton opt : options) {
				if (opt != activeOption) {
					opt.draw();
				}
			}
		}
	}

	@Override
	public void onLanguageChanged() {
		setLanguageSensitiveInfo();
	}

	private void setLanguageSensitiveInfo() {
		for (ComboButton opt : options) {
			opt.setText(Ichigu.getString(opt.getId()));
		}
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		for (ComboButton opt : options) {
			if (opt.isListeningInput() && GameUtils.isIn(x, y, opt)) {
				return false;
			}
		}
		collapseOptions();
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float vx, float vy, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float dx, float dy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector p1Start, Vector p2Start, Vector p1End, Vector p2End) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amount) {
		// TODO Auto-generated method stub
		return false;
	}
}