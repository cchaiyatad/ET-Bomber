package item;

import gameobject.Destroyable;
import gameobject.GameObject;
import javafx.scene.layout.Pane;
import player.Player;

public abstract class Item extends GameObject implements Destroyable {

	public Item(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
		// TODO Auto-generated constructor stub
	}

}
