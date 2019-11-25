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
	public void move() {
		int xDirectionSpeed = 0;
		int yDirectionSpeed = 0;

		switch (currentPlayerState) {
		case MOVEUP:
		case MOVEDOWN:
			if (xPosition % 50 != 0) {
				int xMid = (xPosition / 50 + 1) * 50;
				if (Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50))) {
					currentPlayerState = PlayerState.MOVELEFT;
					int dx = xPosition % 50;
					xDirectionSpeed = dx < speed ? dx * -1 : speed * -1;
				} else {
					currentPlayerState = PlayerState.MOVERIGHT;
					int dx = 50 - (xPosition % 50);
					xDirectionSpeed = dx < speed ? dx : speed;
				}
				xPosition += xDirectionSpeed;
				SetPositionOnScreen();
				return;
			}
			break;

		case MOVERIGHT:
		case MOVELEFT:
			if (yPosition % 50 != 0) {
				int yMid = (yPosition / 50 + 1) * 50;
				if (Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50))) {
					currentPlayerState = PlayerState.MOVEUP;
					int dy = yPosition % 50;
					yDirectionSpeed = dy < speed * -1 ? dy : speed * -1;
				} else {
					currentPlayerState = PlayerState.MOVEDOWN;
					int dy = 50 - (yPosition % 50);
					yDirectionSpeed = dy < speed ? dy : speed;
				}
				yPosition += yDirectionSpeed;
				SetPositionOnScreen();
				return;
			}
			break;
		default:
			break;
		}

		switch (currentPlayerState) {
		case MOVEUP:
			yDirectionSpeed = speed * -1;
			break;
		case MOVERIGHT:
			xDirectionSpeed = speed;
			break;
		case MOVELEFT:
			xDirectionSpeed = speed * -1;
			break;
		case MOVEDOWN:
			yDirectionSpeed = speed;
			break;
		default:
			break;
		}

		int[] xy = gameController.isMoveAble(xPosition + xDirectionSpeed, yPosition + yDirectionSpeed, this);
		xPosition += xy[0];
		yPosition += xy[1];
		SetPositionOnScreen();
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