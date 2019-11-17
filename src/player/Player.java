package player;

import gameobject.GameObject;
import javafx.scene.layout.Pane;

public class Player extends GameObject {
	private PlayerState currentPlayerState;
	
	public Player(int xPosition, int yPosition, String imagePath, Pane layer){
		super(xPosition,yPosition,imagePath, layer);
		currentPlayerState = PlayerState.IDLE;
	}
	
}