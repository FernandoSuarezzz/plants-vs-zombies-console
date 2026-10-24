package tp1.p2.logic.actions;

import tp1.p2.logic.GameItem;


public class ZombieExplosionAction extends ExplosionAction{
	
	public ZombieExplosionAction(int col, int row, int damage) {
		super(col,row,damage);
	}

	@Override
	void damageFromExplosion(GameItem item, int damage) {
		item.receiveZombieAttack(damage);
	}
}

