package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Minion extends AIBase {

	public Minion(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController, player);
		setHp(1);
		this.setSpeed(this.getSpeed() - 1);
		if(playerNumber == 4) {
			aiStatus.vanishTime = 1;
			aiStatus.showTime = 2;
			aiStatus.nextVanishTime = gameController.getRemainingTime() - 5;
		}
	}

	@Override
	public void checkStatus() {
		Action.Dead(this);
		Action.Vanish(this);
		checkPlayerAndObjectInSight();
		Action.CheckForWayAndItem(this);
		Action.RandomWalking(this);
		Action.GoTo(this);
		setCurrentPlayerState(aiStatus.moveDirection);
	}

}
