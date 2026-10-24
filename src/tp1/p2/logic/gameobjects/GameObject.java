package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.status;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

/**
 * Base class for game non playable character in the game.
 *
 */

public abstract class GameObject implements GameItem, Cloneable {

	protected GameWorld game;

	protected int col;

	protected int row;
	
	protected int life;
	 
	public GameObject() {
	}

	public GameObject(GameWorld game, int col, int row, int initial_life) {
		this.game = game;
		this.col = col;
		this.row = row;
		this.life = initial_life;
	}

	public boolean isInPosition(int col, int row) {
		return this.col == col && this.row == row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public String getSymbol() {
		return status(getObjectSymbol(), life);
	}
	
	public boolean isAlive(){
		if(life<=0) return false;
		else return true;
	}

	public String toString() {
		if (isAlive()) {
			return getSymbol();
		} else {
			return "";
		}
	}
	
	public Object clone(){  
	    try{  
	        return super.clone();  
	    }catch(Exception e){ 
	        return null; 
	    }
	}
	
	
    public abstract boolean receiveZombieAttack(int damage);
	
    public abstract boolean receivePlantAttack(int damage,boolean Explosion);
    
    public abstract boolean fillPosition(); // false only for suns: zombies can walk over them
    
    abstract String getObjectSymbol();
	
	abstract public String getDescription();
    
	public abstract void update();
	
    public abstract void onEnter();
	
	public abstract void onExit();
}
