package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Minion extends AIBase {

	public Minion(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController, player);
		setHp(1);
	}

	@Override
	public void checkStatus() {
		Action.Dead(this);
		checkPlayerAndObjectInSight();
		Action.CheckForWayAndItem(this);
		Action.RandomWalking(this);
		Action.GoTo(this);
		setCurrentPlayerState(aiStatus.moveDirection);
	}

}
