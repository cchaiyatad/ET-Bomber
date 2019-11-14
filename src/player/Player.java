package player;

import gameobject.GameObject;

public class Player extends GameObject {
	private PlayerState currentPlayerState;
	
	public Player(int xPosition, int yPosition, String imagePath){
		super(xPosition,yPosition,imagePath);
		currentPlayerState = PlayerState.IDLE;
	}
	
}