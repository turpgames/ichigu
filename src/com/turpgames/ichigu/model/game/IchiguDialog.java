package com.turpgames.ichigu.model.game;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.ichigu.utils.R;

public class IchiguDialog extends Dialog {

	public IchiguDialog() {
		super();

		addButton(R.strings.yes, Game.getResourceManager().getString(R.strings.yes));
		addButton(R.strings.no, Game.getResourceManager().getString(R.strings.no));
	}
}
