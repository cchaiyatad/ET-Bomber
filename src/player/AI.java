package player;

import controller.GameController;
import javafx.scene.layout.Pane;

public class AI extends PlayerBase{

	public AI(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void useWeapon() {
		// TODO Auto-generated method stub
		
	}
}
