package com.turpgames.ichigu.updates;

import com.turpgames.framework.v0.IUpdateProcess;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.ichigu.utils.IchiguSettings;

public class V1_1_0 implements IUpdateProcess {
	private final Version version = new Version("1.1");
	
	@Override
	public void execute() {
		IchiguSettings.setIchiguBalance(100);
		IchiguSettings.setSingleHintCount(5);
	}

	@Override
	public Version getVersion() {
		return version;
	}
}