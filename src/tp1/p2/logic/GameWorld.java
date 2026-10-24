package tp1.p2.logic;


import java.util.List;

import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.control.Level;


public interface GameWorld {

	public static final int NUM_ROWS = 4;

	public static final int NUM_COLS = 8;
	
	void killZombie();
	
	void zombieSpawned();
	
	void playerQuits();
	
	void update() throws GameException;
	
	int getSuncoins();
	
	void substractCoins(int coins);
		
	boolean addItem(GameItem item);
	
	void printDescription(GameObject p);

	List<GameItem> getGameItemsInPosition(int col, int row);
	
	boolean isPositionEmpty(int i, int row);
	
	void zombiesWin();

	/**
	 * Resets the game.
	 * @throws GameException 
	 */
	void reset() throws GameException;

	/**
	 * Resets the game with the provided level and seed.
	 * 
	 * @param level {@link Level} Used to initialize the game.
	 * @param seed Random seed Used to initialize the game.
	 */
	void reset(Level level, long seed) throws GameException;
	
	void tryToBuy(int cost) throws GameException;

	void checkValidPlantPosition(int col, int row) throws GameException;
	
	public void pushAction(GameAction gameAction);

	boolean isFullyOccupied(int i, int row);

	void addSun();

	void tryToCatchObject(int col, int row) throws GameException;

	void printIni();

	void IncreaseScore(boolean Score);

	int getRecord();

	void checkValidZombiePosition(int col, int row) throws GameException;

	String getLevelName();

}
