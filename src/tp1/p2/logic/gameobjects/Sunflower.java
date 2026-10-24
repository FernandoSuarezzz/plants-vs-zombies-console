package tp1.p2.logic.gameobjects;

import tp1.p2.view.Messages;


public class Sunflower extends Plant {
	private static final int SUNFLOWER_ENDURANCE = 1;
	private static final int SUNFLOWER_COST = 20;
	private static final int SUNFLOWER_FREQUENCY = 3;
	private static final int SUNFLOWER_DAMAGE = 0;
	private int cyclesRemaining;
	
	protected Sunflower() {
		super();
		cyclesRemaining = 0;
	}

	protected String getObjectSymbol() {
		return Messages.SUNFLOWER_SYMBOL;
	}
	
	protected String getObjectShortcut() {
		return Messages.SUNFLOWER_NAME_SHORTCUT;
	}
	
	public int getObjectCost() {
		return SUNFLOWER_COST;
	}
	
	protected int getObjectDmg() {
		return SUNFLOWER_DAMAGE;
	}
	
	int getObjectEndurance() {
		return SUNFLOWER_ENDURANCE;
	}
	
	String getObjectName() {
		return Messages.SUNFLOWER_NAME;
	}
	
	public void update() {
		if(this.cyclesRemaining < SUNFLOWER_FREQUENCY) {
			++this.cyclesRemaining;
		}
		else {
			this.cyclesRemaining = 1;
			game.addSun();
		} 
	}
	
	@Override
	public boolean catchObject() {
		return false;
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onExit() {
	}
}



