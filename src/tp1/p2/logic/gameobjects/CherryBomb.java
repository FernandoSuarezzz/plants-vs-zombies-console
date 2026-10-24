package tp1.p2.logic.gameobjects;

import tp1.p2.logic.actions.PlantExplosionAction;
import tp1.p2.view.Messages;


public class CherryBomb extends Plant {

	private static final int CHERRY_BOMB_ENDURANCE= 2;
	private static final int CHERRY_BOMB_DAMAGE=10;
	private static final int CHERRY_BOMB_COST = 50;
	private static final int CHERRY_BOMB_CYCLE=2;
	
	private int ActualCycle;
	
	public CherryBomb() {
		super();
		ActualCycle = 0;
	}
	
	@Override
	public boolean catchObject() {
		return false;
	}

	@Override
	public void update() { // explodes after CHERRY_BOMB_CYCLE cycles
		if(ActualCycle<CHERRY_BOMB_CYCLE) {
			ActualCycle++;
		}else {
			this.life=0;
		}
	 }

	@Override
	String getObjectSymbol() {
		if(ActualCycle<CHERRY_BOMB_CYCLE) {
			return Messages.CHERRY_BOMB_SYMBOL;
		}
		return "C"; // about to explode
	}
	
	@Override
	String getObjectShortcut() {
		return Messages.CHERRY_BOMB_NAME_SHORTCUT;
	}

	@Override
	public int getObjectCost() {
		return CHERRY_BOMB_COST;
	}


	@Override
	int getObjectEndurance() {
		return CHERRY_BOMB_ENDURANCE;
	}

	@Override
	String getObjectName() {
		return Messages.CHERRY_BOMB_NAME;
	}

	@Override
	int getObjectDmg() {
		return CHERRY_BOMB_DAMAGE;
	}
	@Override
	public void onEnter() {}
	
	@Override
	public void onExit() {
		game.pushAction(new PlantExplosionAction(col,row,CHERRY_BOMB_DAMAGE));
	}
}
