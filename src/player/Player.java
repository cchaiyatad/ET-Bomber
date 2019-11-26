package player;

import java.util.LinkedList;
import java.util.Queue;

import controller.GameController;
import javafx.scene.layout.Pane;
import weapon.*;
public class Player extends PlayerBase {
	
	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);	
	}
	
	protected void setDefaultPlayer() {
		setHp(3);
		setBombRange(1);
		setBombCount(1);
		countBomb = new LinkedList<Bomb>();
		setCanUseWeapon();
		shieldTime = 0;

		setSpeed(defaultMoveSpeed);
		setCurrentWeapon(WeaponType.BOMB);
		currentPlayerState = PlayerState.IDLE;
	}

	public Queue<Bomb> getCountBomb() {
		return countBomb;
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

}