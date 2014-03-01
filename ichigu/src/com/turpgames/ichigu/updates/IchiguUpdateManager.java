package com.turpgames.ichigu.updates;

import com.turpgames.framework.v0.impl.UpdateProcessor;

public final class IchiguUpdateManager {
	public static void runUpdates() {
		UpdateProcessor p = UpdateProcessor.instance;		
		
		p.addProcess(new V1_1_0());
		p.addProcess(new V1_1_2());
		p.addProcess(new V1_2_0());
		
		p.execute();
	}
	
	private IchiguUpdateManager() {
		
	}
}
