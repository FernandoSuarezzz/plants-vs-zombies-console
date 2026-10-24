package tp1.p2.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.view.Messages;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}
	
	void addItem(GameObject item) {
		gameObjects.add(item);
		item.onEnter();
	}
	
	void updateObjects() {
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject g = gameObjects.get(i);
			if(g.isAlive()) {
				g.update();
			}
		}
	}
	
	boolean removeDead() {
		boolean notAlive=false;
		boolean deleted; // if an object is removed, i is not advanced: the next object moves to position i
		int i = 0;
		while(i<gameObjects.size()) {
			deleted = false;
			GameObject o = gameObjects.get(i);
			if(!o.isAlive()){
				o.onExit(); // e.g. zombies update the score, explosive objects push an explosion
				gameObjects.remove(o);
				deleted = true;
				notAlive = true;
			}
			if(!deleted)i++;
		}
		return notAlive;
	}
	
	boolean isFullyOccupied(int col, int row) {
		int i=0;
		boolean fullyOccupied = false;

		while (i<gameObjects.size() && !fullyOccupied) {
			GameObject g = gameObjects.get(i);
			if (g.isAlive() && g.isInPosition(col, row)) {
				fullyOccupied = g.fillPosition();
			}
			i++;
		}
		return fullyOccupied;
	}
		
	List<GameItem> itemsInPosition (int col, int row) {
		List<GameItem> list = new ArrayList<>();
		for(GameObject o : gameObjects) {
			if(o.isInPosition(col, row))list.add(o);
		}
		return list;
	}
	
	int catchSuns(int col, int row) {
		int sunsCatched = 0;
		for(GameObject o:gameObjects) {
			if(o.isInPosition(col,row)&&o.catchObject()) { // only suns can be caught
				++sunsCatched;
			}
		}
		removeDead();
		return sunsCatched;
	}
	
	String positionToString(int col, int row) { // symbol and life of the objects in the cell (one sun at most)
		StringBuilder buffer = new StringBuilder();
		boolean sunPainted = false;
		boolean sunAboutToPaint = false;

		for (GameObject g : gameObjects) {
			if(g.isAlive() && g.getCol() == col && g.getRow() == row) {
				String objectText = g.toString();
				sunAboutToPaint = objectText.indexOf(Messages.SUN_SYMBOL) >= 0;
				if (sunAboutToPaint) {
					if (!sunPainted) {
						buffer.append(objectText);
						sunPainted = true;
					}
				} else {
					buffer.append(objectText);
				}
			}
		}

		return buffer.toString();
	}
	
}
