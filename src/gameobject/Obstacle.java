package gameobject;

import item.Item;
import javafx.scene.layout.Pane;

public class Obstacle extends GameObject implements Destroyable {
	
	private Item itemInObstacle;
	
	public Obstacle(int xPosition, int yPosition, Pane layer, Item item) {
		super(xPosition, yPosition, "obstacle", layer);
		itemInObstacle = item;
	}

	@Override
	public void onObjectIsDestroyed() {
		// TODO Auto-generated method stub
	}

}
