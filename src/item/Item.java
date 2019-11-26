package item;

import controller.ObjectInGame;
import gameobject.Destroyable;
import gameobject.GameObject;
import javafx.scene.layout.Pane;

public abstract class Item extends GameObject implements Destroyable {

	public Item(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
	}
	
	public abstract ObjectInGame getObjectInGame();

}
