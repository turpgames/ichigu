package com.turpgames.ichigu.model.game.table;


public abstract class RegularGameTable extends Table {

	public void afterIchiguFound() {
		
	}
	
	public void afterInvalidIchiguSelected() {
		
	}
	
	abstract public boolean isIchiguAttempted();

	abstract public boolean isIchiguFound();
}
