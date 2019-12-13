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

	

	public void checkStatus() {
		Action.Dead(this);
		Action.SpawnMinion(this);
		Action.Vanish(this);
		checkPlayerAndObjectInSight();
		Action.CheckForBomb(this);
		Action.CheckForWayAndItem(this);
		Action.EscapeBomb(this);
		Action.CollectItem(this);
		Action.RandomWalking(this);
		Action.GoTo(this);
		setCurrentPlayerState(aiStatus.moveDirection);
	}
}
