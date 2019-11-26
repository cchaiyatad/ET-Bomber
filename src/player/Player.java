package player;

import controller.GameController;
import javafx.scene.layout.Pane;
import weapon.*;
public class Player extends PlayerBase {
	
	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);	
	}
	
	@Override
	public void useWeapon() {
		int x = xPosition / 50;
		int y = yPosition / 50;
		if (xPosition % 50 != 0) {
			int xMid = (xPosition / 50 + 1) * 50;
			x = Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50)) ? x : x + 1;
		} else if (yPosition % 50 != 0) {
			int yMid = (yPosition / 50 + 1) * 50;
			y = Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50)) ? y : y + 1;
		}

		switch (getCurrentWeapon()) {
		case BOMB:
			if (this.gameController.canSetObject(x, y)) {
				this.gameController.setObjectInGame(x, y,
						new Bomb(x * 50, y * 50, this.getGameController().getGamePage().getGameFieldItemPane(),
								getBombRange(), this, this.getGameController()));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

}