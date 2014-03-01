package com.turpgames.ichigu.model.game.table;


public abstract class RegularGameTable extends Table {

	public void afterIchiguFound() {
		
	}
	
	public void afterInvalidIchiguSelected() {
		
	}
	
	public abstract boolean isIchiguAttempted();

	public abstract boolean isIchiguFound();
}
