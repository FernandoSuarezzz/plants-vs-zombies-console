package tp1.p2.logic.gameobjects;


import tp1.p2.view.Messages;

public class WallNut extends Plant {

	private static final int WALL_NUT_ENDURANCE = 10;
	private static final int WALL_NUT_DAMAGE = 0;
	private static final int WALL_NUT_COST = 50;
		
	public WallNut() { 
		super();
	}

	@Override
	public boolean catchObject() {
		return false;
	}

	@Override
	public void update() { // a wall-nut only blocks zombies
	}
	
	@Override
	String getObjectSymbol() {
		return Messages.WALL_NUT_SYMBOL;
	}
	
	@Override
	String getObjectShortcut() {
		return Messages.WALL_NUT_NAME_SHORTCUT;
	}

	@Override
	public int getObjectCost() {
		return WALL_NUT_COST;
	}

	@Override
	int getObjectDmg() {
		return WALL_NUT_DAMAGE;
	}

	@Override
	int getObjectEndurance() {
		return WALL_NUT_ENDURANCE;
	}

	@Override
	String getObjectName() {
		return Messages.WALL_NUT_NAME;
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onExit() {
		
	}
}