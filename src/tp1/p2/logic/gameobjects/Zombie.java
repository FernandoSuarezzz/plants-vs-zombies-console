package tp1.p2.logic.gameobjects;
import static tp1.p2.view.Messages.zombieDescription;

import java.util.List;

import tp1.p2.logic.Game;
import tp1.p2.logic.GameItem;
import tp1.p2.view.Messages;

public class Zombie extends GameObject {

	private static final int ZOMBIE_ENDURANCE = 5;
	private static final int ZOMBIES_DAMAGE = 1; // all zombies deal the same damage
	private static final int ZOMBIE_SPEED = 2;
	private int speed;
	protected boolean ExplosionAttack;
		
	protected Zombie() {
		super();
		speed = getZombieSpeed();
		ExplosionAttack=false;
	}

	public String getDescription() {
		return zombieDescription(getObjectName(), getZombieSpeed(), ZOMBIES_DAMAGE, getEndurance());
	}
	
	String getObjectName() {
		return Messages.ZOMBIE_NAME;
	}

	int getEndurance() {
		return ZOMBIE_ENDURANCE;
	}
	
	String getObjectSymbol() {
		return Messages.ZOMBIE_SYMBOL;
	}
	
	int getZombieSpeed() {
		return ZOMBIE_SPEED;
	}
	
	@Override
	public boolean fillPosition() {
		return true;
	}

	@Override
	public void update() { // moves every "speed" cycles (if the cell in front is free) and attacks what is in front
		if(this.speed == 0) {
			this.speed=getZombieSpeed()-1;
			if(!game.isFullyOccupied(col-1, row)) {
				--this.col;
				if(col == -1) game.zombiesWin();			
		    }
		}
		else this.speed--;
		
		List<GameItem> list = game.getGameItemsInPosition(this.col-1, this.row);
		if(list.size() != 0) {
			for(int j = 0; j<list.size();j++) {
				list.get(j).receiveZombieAttack(ZOMBIES_DAMAGE);
			}
		}
		ExplosionAttack=false; // it survived the explosion
	}
	
	@Override
	public void onEnter() {
		game.zombieSpawned();
	}

	@Override
	public void onExit() {
		game.killZombie();
		game.IncreaseScore(ExplosionAttack);
	}
	
	@Override
	public boolean receivePlantAttack(int damage, boolean Explosion) { // remembers whether the damage came from an explosion
		if (!isAlive()) return false; // already dead: the attack goes to the next object
		if(col != Game.NUM_COLS) life-=damage;
		ExplosionAttack=Explosion;
		return true;
	}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}

	@Override
	public boolean catchObject() {
		return false;
	}
	


	
}
