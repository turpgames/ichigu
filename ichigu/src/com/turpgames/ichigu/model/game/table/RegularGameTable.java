package com.turpgames.ichigu.model.game.table;


public abstract class RegularGameTable extends Table {

	abstract public boolean isIchiguAttempted();
	
	abstract public boolean isIchiguFound();
	
	public void afterInvalidIchiguSelected() {
		
	}

	public void afterIchiguFound() {
		
	}
}
