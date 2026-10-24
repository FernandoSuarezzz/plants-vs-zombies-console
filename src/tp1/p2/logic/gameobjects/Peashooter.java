package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

import java.util.List;

import tp1.p2.logic.GameItem;

public class Peashooter extends Plant {
	private static final int PEASHOOTER_ENDURANCE = 3;
	private static final int PEASHOOTER_DAMAGE = 1;
	private static final int PEASHOOTER_COST = 50;
	
	public Peashooter() {
		super();
	}
	
	String getObjectName() {
		return Messages.PEASHOOTER_NAME;
	}

	String getObjectSymbol() {
		return Messages.PEASHOOTER_SYMBOL;
	}
	
	String getObjectShortcut() {
		return Messages.PEASHOOTER_NAME_SHORTCUT;
	}
	
	public int getObjectCost() {
		return PEASHOOTER_COST;
	}
	
	int getObjectDmg() {
		return PEASHOOTER_DAMAGE;
	}
	
	int getObjectEndurance() {
		return PEASHOOTER_ENDURANCE;
	}
	
	public void update() { // shoots the first zombie in its row
		int i = 1;
		boolean first = false;
		while(i+this.col<GameWorld.NUM_COLS && !first) {
			List<GameItem> list = game.getGameItemsInPosition(col+i, row);
			if(list.size() != 0) {
				for(int j = 0; j<list.size();j++) {
					if(list.get(j).receivePlantAttack(PEASHOOTER_DAMAGE,false)) first = true;
				}
			}
			i++;
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

