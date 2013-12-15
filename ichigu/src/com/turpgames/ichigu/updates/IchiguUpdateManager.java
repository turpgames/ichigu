package com.turpgames.ichigu.updates;

import com.turpgames.framework.v0.impl.UpdateProcessor;

public final class IchiguUpdateManager {
	public static void runUpdates() {
		UpdateProcessor p = UpdateProcessor.instance;		
		
		p.addProcess(new V11_IchiguBank());
		p.addProcess(new V112_HiScoreFix());
		
		p.execute();
	}
	
	private IchiguUpdateManager() {
		
	}
}
