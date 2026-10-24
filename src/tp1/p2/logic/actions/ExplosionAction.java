package tp1.p2.logic.actions;
import java.util.List;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public abstract class ExplosionAction implements GameAction {

	protected int col;

	protected int row;

	protected int damage;

	public ExplosionAction(int col, int row, int damage) {
		this.col = col;
		this.row = row;
		this.damage = damage;
	}

	@Override
	public void execute(GameWorld game) { // damages every object in the 3x3 area around (col, row)
		 for(int i=row-1;i<row+2;i++) {
			 for(int j=col-1;j<col+2;j++) { 
				 List<GameItem> list = game.getGameItemsInPosition(j,i);
				if(list.size() != 0) {
					for(int k = 0; k<list.size();k++) {
						damageFromExplosion(list.get(k), damage);
					}
				}
		     }
		 }
	}
	
	abstract void damageFromExplosion(GameItem item, int damage);
}