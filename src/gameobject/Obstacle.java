package gameobject;

import controller.GameController;
import item.Item;
import javafx.scene.layout.Pane;

public class Obstacle extends GameObject implements Destroyable {

	private Item itemInObstacle;
	private GameController gameController;

	public Obstacle(int xPosition, int yPosition, Pane layer, Item item, GameController gameController) {
		super(xPosition, yPosition, "obstacle", layer);
		itemInObstacle = item;
		this.gameController = gameController;
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		this.gameController.removeItem(xPosition / 50, yPosition / 50);
		if (itemInObstacle != null) {
			gameController.setObjectInGame(xPosition / 50, yPosition / 50, this.itemInObstacle);
		}
	}

}
