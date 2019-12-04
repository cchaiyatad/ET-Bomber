package player;

import controller.GameController;
import javafx.scene.layout.Pane;
public class Player extends PlayerBase {
	
	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);	
	}
	
	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

}