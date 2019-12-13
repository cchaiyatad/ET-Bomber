package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Boss extends AIBase {
	
	public int bossNumber;
	public Boss(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player,int bossNumber) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController, player);
		this.bossNumber = bossNumber;
	}

	

	public void checkStatus() {
		Action.Dead(this);
		Action.SpawnMinion(this);
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
