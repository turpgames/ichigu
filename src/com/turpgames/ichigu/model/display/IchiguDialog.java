package com.turpgames.ichigu.model.display;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.ichigu.utils.R;

public class IchiguDialog extends Dialog {
	public IchiguDialog() {
		addButton(R.strings.yes, R.strings.yes);
		addButton(R.strings.no, R.strings.no);
	}
}
