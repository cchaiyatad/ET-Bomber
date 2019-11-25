package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class AI extends PlayerBase{
	private boolean[] status = {};
	
	public AI(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);
	}
	
	public void checkStatus() {
		
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
