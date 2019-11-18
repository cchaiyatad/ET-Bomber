package player;

import gamecontroller.GameController;
import gameobject.GameObject;
import gameobject.Moveable;
import javafx.scene.layout.Pane;

public class Player extends GameObject implements Moveable {
	private int speed;
	private int playerNumber;
	private PlayerState currentPlayerState;
	private GameController gameController;

	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber, GameController gameController) {
		super(xPosition, yPosition, imagePath, layer);
		this.speed = DefaultMoveSpeed;
		currentPlayerState = PlayerState.IDLE;
		this.playerNumber = playerNumber;
		this.gameController = gameController;
	}

	@Override
	public void Move() {
		int xDirectionSpeed = 0;
		int yDirectionSpeed = 0;

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

		
		if(gameController.isMoveAble(xPosition + xDirectionSpeed, yPosition + yDirectionSpeed,this)) {
			xPosition += xDirectionSpeed;
			yPosition += yDirectionSpeed;	
		}
		SetPositionOnScreen();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public PlayerState getCurrentPlayerState() {
		return currentPlayerState;
	}

	public void setCurrentPlayerState(PlayerState currentPlayerState) {
		this.currentPlayerState = currentPlayerState;
	}

}