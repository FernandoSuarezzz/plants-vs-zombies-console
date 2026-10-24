package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.plantDescription;

public abstract class Plant extends GameObject {
	
	protected Plant() {
		super();
	}
	
	public String getDescription() {
		return plantDescription(getObjectShortcut(), getObjectCost(), getObjectDmg(), getObjectEndurance());
	}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		if (!isAlive()) return false;
		this.life-=damage;
		return true;
	}

	@Override
	public abstract void update();

	@Override
	public boolean receivePlantAttack(int damage, boolean Explosion) {
		return false;
	}
	
	@Override
	public boolean fillPosition() {
		return true;
	}
		
	abstract String getObjectShortcut();
	
	public abstract int getObjectCost();
	
	abstract int getObjectDmg();
	
	abstract int getObjectEndurance();
	
	abstract String getObjectSymbol();
	
	abstract String getObjectName();
	
	public abstract void onEnter();

	public abstract void onExit();
	
}
