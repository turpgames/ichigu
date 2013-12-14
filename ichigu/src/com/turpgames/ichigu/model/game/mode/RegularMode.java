package com.turpgames.ichigu.model.game.mode;

import com.turpgames.ichigu.model.game.table.RegularGameTable;

public abstract class RegularMode extends IchiguMode {

	@Override
	protected RegularGameTable getTable() {
		return (RegularGameTable) super.getTable();
	}
	

	public final void ichiguFound() {
		getTable().afterIchiguFound();
		concreteIchiguFound();
	}
	
	public final void invalidIchiguSelected() {
		getTable().afterInvalidIchiguSelected();
		concreteInvalidIchiguSelected();
	}
	
	abstract public void concreteInvalidIchiguSelected();
	
	abstract public void concreteIchiguFound(); 
	
}
