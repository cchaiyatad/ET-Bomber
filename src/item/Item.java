package item;

import controller.GameController;
import controller.ObjectInGame;
import gameobject.Destroyable;
import gameobject.GameObject;
import javafx.scene.layout.Pane;

public abstract class Item extends GameObject implements Destroyable {
	private GameController gameController;
	public Item(int xPosition, int yPosition, String imagePath, Pane layer,GameController gameController) {
		super(xPosition, yPosition, imagePath, layer);
		setGameController(gameController);
	}
	
	public abstract ObjectInGame getObjectInGame();

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
}
