package tp1.p2.logic.gameobjects;

import tp1.p2.view.Messages;
import tp1.p2.logic.GameWorld;

public class Sun extends GameObject {
	
	private static final int SUN_ENDURANCE = 11;
	
	private static int catchedSuns;
	
	private static int generatedSuns;
	
	private boolean hasBeenCatched;
	

	public Sun(GameWorld game, int col, int row) {
		super(game, col, row, SUN_ENDURANCE);
		hasBeenCatched = false;
	}
	
	
	@Override
	public boolean catchObject() {
		life = 0;
		hasBeenCatched = true;
		return hasBeenCatched;
	}

	@Override
	public boolean fillPosition() { // zombies can walk over suns
		return false;
	}

	@Override
	public void update() { // suns disappear after SUN_ENDURANCE cycles
		life -= 1;
	}	

	@Override
	String getObjectSymbol() {
		return Messages.SUN_SYMBOL;
	}

	public void onEnter() {
		generatedSuns++;
	}

	@Override
	public void onExit() {
		if(hasBeenCatched) { // only count it if it was caught (not expired)
			catchedSuns++;
		}
	}
	
	public static int getCatchedSuns() {
		return catchedSuns;				
	}
	
	public static int getGeneratedSuns() {
		return generatedSuns;
	}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	@Override
	public boolean receivePlantAttack(int damage, boolean Explosion) {
		return false;
	}


	@Override
	public String getDescription() {
		return null;
	}
	
	public static void reset() {
		generatedSuns=0;
		catchedSuns=0;
	}
}
