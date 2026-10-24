package tp1.p2.logic.gameobjects;

import tp1.p2.logic.actions.ZombieExplosionAction;
import tp1.p2.view.Messages;

public class ExplosiveZombie extends Zombie{
	
	private static final int EXPLOSIVE_ZOMBIE_ENDURANCE = 5;
	private static final int EXPLOSIVE_ZOMBIE_SPEED=2;
	private static final int EXPLOSION_DAMAGE=3;

	public ExplosiveZombie() {
		super();
	}
		
	@Override
	public void onExit() {
		game.killZombie();
		game.pushAction(new ZombieExplosionAction(col, row, EXPLOSION_DAMAGE));
		game.IncreaseScore(ExplosionAttack);
	}
	
	@Override
	protected int getEndurance() {
		return EXPLOSIVE_ZOMBIE_ENDURANCE;
	}
	
	@Override
	protected String getObjectSymbol() {
		return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}
	
	@Override
	protected int getZombieSpeed() {
		return EXPLOSIVE_ZOMBIE_SPEED;
	}

	@Override
	protected String getObjectName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}
}
