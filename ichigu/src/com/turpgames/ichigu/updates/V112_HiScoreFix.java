package com.turpgames.ichigu.updates;

import com.turpgames.framework.v0.IUpdateProcess;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.ichigu.utils.R;

public class V112_HiScoreFix implements IUpdateProcess {
	private final Version version = new Version("1.1.2");

	@Override
	public void execute() {
		Settings.putInteger(R.settings.hiscores.normaltime, 0);
	}

	@Override
	public Version getVersion() {
		return version;
	}
}
