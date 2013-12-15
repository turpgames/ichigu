package com.turpgames.ichigu.model.game.mode;

import com.turpgames.ichigu.model.game.table.RegularGameTable;

public abstract class RegularMode extends IchiguMode {

	abstract public void concreteIchiguFound();
	

	abstract public void concreteInvalidIchiguSelected();
	
	public final void ichiguFound() {
		getTable().afterIchiguFound();
		concreteIchiguFound();
	}
	
	public final void invalidIchiguSelected() {
		getTable().afterInvalidIchiguSelected();
		concreteInvalidIchiguSelected();
	}
	
	@Override
	protected RegularGameTable getTable() {
		return (RegularGameTable) super.getTable();
	} 
	
}
