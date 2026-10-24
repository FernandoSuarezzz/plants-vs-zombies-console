package tp1.p2.logic.gameobjects;

import tp1.p2.view.Messages;

public class Sporty extends Zombie {
	
	private static final int SPORTY_ENDURANCE = 2;
	private static final int SPORTY_SPEED=1;
	
	public Sporty() {
		super();
	}
		
	@Override
	int getEndurance() {
		return SPORTY_ENDURANCE;
	}
	
	@Override
	String getObjectSymbol() {
		return Messages.SPORTY_ZOMBIE_SYMBOL;
	}
	
	@Override
	protected int getZombieSpeed() {
		return SPORTY_SPEED;
	}

	@Override
	protected String getObjectName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}


}
