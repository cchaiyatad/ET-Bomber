package player;

import gameobject.GameObject;
import gameobject.Moveable;
import javafx.scene.layout.Pane;

public class Player extends GameObject implements Moveable {
	private int playerNumber;
	private PlayerState currentPlayerState;

	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber) {
		super(xPosition, yPosition, imagePath, layer);
		currentPlayerState = PlayerState.IDLE;
		this.playerNumber = playerNumber;
	}

	@Override
	public void Move() {
		int xDirectionSpeed = 0;
		int yDirectionSpeed = 0;

		switch (currentPlayerState) {
		case MOVEUP:
			yDirectionSpeed = DefaultMoveSpeed * -1;
			break;
		case MOVERIGHT:
			xDirectionSpeed = DefaultMoveSpeed;
			break;
		case MOVELEFT:
			xDirectionSpeed = DefaultMoveSpeed * -1;
			break;
		case MOVEDOWN:
			yDirectionSpeed = DefaultMoveSpeed;
			break;
		default:
			break;
		}

		xPosition += xDirectionSpeed;
		yPosition += yDirectionSpeed;
		SetPositionOnScreen();
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