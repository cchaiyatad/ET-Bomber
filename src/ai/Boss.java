package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Boss extends AIBase {
	
	public Boss(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController, player);
		if(playerNumber == 4) {
			aiStatus.vanishTime = 3;
			aiStatus.showTime = 2;
			aiStatus.nextVanishTime = gameController.getRemainingTime() - 5;
		}
	}

	public void checkForAction() {
		Action.dead(this);
		Action.spawnMinion(this);
		Action.vanish(this);
		checkPlayerAndObjectInSight();
		Action.checkForBomb(this);
		Action.checkForWayAndItem(this);
		Action.escapeBomb(this);
		Action.collectItem(this);
		Action.randomWalking(this);
		Action.goTo(this);
		setCurrentPlayerState(aiStatus.moveDirection);
	}
}
