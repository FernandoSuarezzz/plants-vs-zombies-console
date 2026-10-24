package tp1.p2.logic;

import java.util.Random;

import tp1.p2.logic.gameobjects.Sun;

public class SunsManager {

	private static final int COOLDOWN_RANDOM_SUN = 5;
	
	private static final int INITIAL_SUN_COINS = 50;
	
	private static final int SUN_COINS_VALUE = 10;
	
	private GameWorld game;

	private Random rand;

	private int cooldown;
	
	private int sunCoins;

	public SunsManager(GameWorld game, Random rand) {
		this.game = game;
		this.rand = rand;
		cooldown = COOLDOWN_RANDOM_SUN;
		sunCoins = INITIAL_SUN_COINS;
		Sun.reset();
	}
	
	public void update() { // adds a random sun every COOLDOWN_RANDOM_SUN cycles
		if (cooldown == 0) {
			addSun();
			cooldown = COOLDOWN_RANDOM_SUN;
		} else {
			cooldown--;
		}
	}

	private int getRandomInt(int bound) {
		return this.rand.nextInt(bound);
	}

	public void addSun() {
		int col = getRandomInt(GameWorld.NUM_COLS);
		int row = getRandomInt(GameWorld.NUM_ROWS);
		game.addItem(new Sun(this.game, col, row));
	}

	void subsSunCoins(int coins) {
		sunCoins-=coins;
	}
	
	void addSunCoins(int sunsCatched) {
		sunCoins += sunsCatched * SUN_COINS_VALUE;
	}
	
	int getCatchedSuns() {
		return Sun.getCatchedSuns();
	}
	
	int getSunCoins() {
		return sunCoins;
	}

	int getGeneratedSuns() {
		return Sun.getGeneratedSuns();
	}
}