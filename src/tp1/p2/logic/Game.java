package tp1.p2.logic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import tp1.p2.logic.Record;
import tp1.p2.control.Command;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.control.Level;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.view.Messages;

public class Game implements GameStatus, GameWorld {
	
	private long seed;

	private Level level;
	
	private int cycles;

	private GameObjectContainer container;
	
	private ZombiesManager zombiesM;
	
	private SunsManager sunsM;
	
    private Random rand;
	
	private boolean playerQuits;
	
	private boolean zombieHasWon;

	private Deque<GameAction> actions;
	
	private Record r;
	
	private boolean newRecord;
	
	private int score;
	
	private static final int EXPLOSIVE_SCORE=20;
	private static final int NOT_EXPLOSIVE_SCORE=10;


	public Game(long seed, Level level) throws GameException {
		this.seed = seed;
		this.level = level;
		reset();
	}
	
	public int getCycle() {
		return this.cycles;
	}
	
	public void pushAction(GameAction gameAction) {
	    this.actions.addLast(gameAction);
	}
	
	public int getRemainingZombies() {
		return zombiesM.getRemainingZombies(); // zombies that have not appeared yet
	}

	public int getZombiesCont() {
		return zombiesM.getZombiesAlived(); // zombies alive on the board
	}
	
	/**
	 * Resets the game.
	 */
	@Override

	public void reset() throws GameException { // same level and seed
		reset(this.level, this.seed);
	}

	/**
	 * Resets the game with the provided level and seed.
	 * 
	 * @param level {@link Level} Used to initialize the game.
	 * @param seed Random seed Used to initialize the game.
	 * @throws  
	 * @throws  
	 */
	@Override
	public void reset(Level level, long seed) throws GameException {
		this.newRecord=false;
		this.score=0;
		r= Record.loadRecord(level); // record of the current level
		this.cycles = 0;
		this.seed = seed;
		this.level = level;
		this.rand=new Random(seed);
		this.container = new GameObjectContainer();	
		this.zombiesM=new ZombiesManager(this,this.level,this.rand);
		this.sunsM = new SunsManager(this, this.rand);
		this.playerQuits = false;
		this.zombieHasWon = false;
		this.actions = new ArrayDeque<>(); // pending actions (explosions)
		Command.newCycle(); // a new game starts with a new cycle (e.g. a sun can be caught again)
	}


	/**
	 * Executes the game actions and update the game objects in the board.
	 * @throws  
	 * 
	 */
	@Override
	public void update() throws GameException {

		// 1. Execute pending actions
		executePendingActions();

		// 2. Execute game Actions
		

		// 3. Game object updates
		zombiesM.update();
		sunsM.update();
		container.updateObjects();


		

		// 4. & 5. Remove dead and execute pending actions
		boolean deadRemoved = true;
		while (deadRemoved || areTherePendingActions()) {
			// 4. Remove dead
			deadRemoved = this.container.removeDead(); // repeat while something died: an explosion may kill more objects

			// 5. execute pending actions
			executePendingActions();
		}

		this.cycles++;

		// 6. Notify commands that a new cycle started
		Command.newCycle();
		
		// 7. Update record
		if(isFinished()) { // when the game ends, update and save the record
			newRecord = r.update(this.score);
			r.save();
		}

	}

	private void executePendingActions() {
		while (!this.actions.isEmpty()) {
			GameAction action = this.actions.removeLast();
			action.execute(this);
		}
	}

	private boolean areTherePendingActions() {
		return this.actions.size() > 0;
	}

	public boolean addItem(GameItem item) {
		container.addItem((GameObject) item);
		return true;
	}
			
	public boolean isPositionEmpty(int col, int row) {
		if(container.itemsInPosition(col, row).size() ==  0) return true;
		else return false;
	}
	
	public boolean isFullyOccupied(int col, int row) { // true if the cell is blocked (suns do not block it)
		return container.isFullyOccupied(col, row);
	}

	public boolean execute(Command command) throws GameException{ // returns true if the board must be printed
			boolean a = command.execute(this);
			return a;
	}
		
	public void printIni() {
		System.out.println(Messages.CONFIGURED_LEVEL.formatted(level));
		System.out.println(Messages.CONFIGURED_SEED.formatted(seed));
	}

	public boolean isPlayerQuits() {
		return this.playerQuits;
	}

	public void playerQuits() {
		this.playerQuits = true;		
	}
	
	public void killZombie() {
		zombiesM.killZombie();
	}
		
	public void zombieSpawned() {
		zombiesM.zombieAlive();
	}

	public boolean isFinished() {
		return zombieHasWon || playerWins();
	}
	
	public void zombiesWin() {
		zombieHasWon = true;
	}
	
	public boolean playerWins() {
		return zombiesM.getRemainingZombies()==0 && zombiesM.getZombiesAlived()==0;
	}
	
	public List<GameItem> getGameItemsInPosition(int col, int row) {
		return container.itemsInPosition(col, row);
	}
		 		 
	public void printDescription(GameObject o) {
		System.out.println(o.getDescription());			
	}

	public String positionToString(int col, int row) {
		return container.positionToString(col, row);
	}
		
	public void substractCoins(int coins) {
		sunsM.subsSunCoins(coins);
	}
	
	@Override
	public int getSuncoins() {
		return sunsM.getSunCoins();
	}

	@Override
	public void addSun() { // adds a sun in a random cell (used by Sunflower)
		sunsM.addSun();
	}

	@Override
	public void tryToCatchObject(int col, int row)throws GameException{
		int suns=container.catchSuns(col,row);
		if(suns!=0) {
			sunsM.addSunCoins(suns);
		}else {
			throw new NotCatchablePositionException(col,row);
		}
	}

	@Override
	public int getGeneratedSuns() {
		return sunsM.getGeneratedSuns();
	}

	@Override
	public int getCaughtSuns() {
		return sunsM.getCatchedSuns();
	}

	@Override
	public void tryToBuy(int cost) throws GameException {
		if(cost>sunsM.getSunCoins()) {
			throw new NotEnoughCoinsException();
		}
		
	}

	@Override
	public void checkValidPlantPosition(int col, int row) throws GameException {
		if(isFullyOccupied(col,row)||col<0 ||col>GameWorld.NUM_COLS-1||row<0||row>GameWorld.NUM_ROWS-1) {
			throw new InvalidPositionException(col,row);
		}
	}

	public int getScore() {
		return this.score;
	}
	
	public void IncreaseScore(boolean Score) { // true: killed by an explosion (more points)
		if(Score) {
			score+=EXPLOSIVE_SCORE;
		}else {
			score+=NOT_EXPLOSIVE_SCORE;
		}
	}
	
	public void checkValidZombiePosition(int col, int row) throws GameException{ // zombies can also be added in the column outside the board
		if(isFullyOccupied(col,row)||col<0 ||col>GameWorld.NUM_COLS||row<0||row>GameWorld.NUM_ROWS-1) {
			throw new InvalidPositionException(col,row);
		}
	}

	public int getRecord() {
		return r.getRecord();
	}
	
	public boolean isNewRecord() {
		return this.newRecord;
	}
	
	public String getLevelName() {
		return this.level.toString();
	}
}
