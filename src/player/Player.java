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