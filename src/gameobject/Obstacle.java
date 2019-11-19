package gameobject;

import item.Item;
import javafx.scene.layout.Pane;

public class Obstacle extends GameObject implements Destroyable {
	
	private Item itemInObstacle;
	
	public Obstacle(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDestroyObject() {
		// TODO Auto-generated method stub
		
	}

}
