package gameobject;

import javafx.scene.layout.Pane;

public class Wall extends GameObject {

	public Wall(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "wall", layer);
	}

	public Wall(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
	}
}
