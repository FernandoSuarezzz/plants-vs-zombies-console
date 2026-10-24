package tp1.p2.logic.actions;

import tp1.p2.logic.GameItem;


public class PlantExplosionAction extends ExplosionAction{
	
	public PlantExplosionAction(int col, int row,int damage) {
		super(col,row,damage);
	}
	
	@Override
	void damageFromExplosion(GameItem item, int damage) {
		item.receivePlantAttack(damage,true);
	}
}
