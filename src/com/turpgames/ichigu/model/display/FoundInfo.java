package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.component.info.FlashingGameInfo;
import com.turpgames.ichigu.utils.Ichigu;
import com.turpgames.ichigu.utils.R;

public class FoundInfo extends FlashingGameInfo {
	private int ichigusFound;

	public FoundInfo() {
		super(R.colors.ichiguRed, 8, 0.5f);
		reset();
	}

	public int getFound() {
		return this.ichigusFound;
	}

	public void increaseFound() {
		this.ichigusFound++;
		updateText();
	}

	public void reset() {
		this.ichigusFound = 0;
		updateText();
	}

	private void updateText() {
		setText(Ichigu.getString(R.strings.found) + ": " + this.ichigusFound);
	}
}
