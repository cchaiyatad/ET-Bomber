package ai;

import controller.GameController;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Minion extends AIBase {

	public Minion(int xPosition, int yPosition, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player) {
		super(xPosition, yPosition, "minion", layer, playerNumber, gameController, player);
		setHp(1);
		this.setSpeed(this.getSpeed() - 1);
		if (playerNumber == 4) {
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

	public void checkContactPlayer() {
		if(getAiStatus().isDead) {
			gameController.getMinions().remove(this);
			return;
		}
		int playerX = player.getxPosition();
		int playerY = player.getyPosition();
		boolean isXHit = (playerX <= this.getxPosition() && this.getxPosition() <= playerX + 50)
				|| (playerX <= this.getxPosition() + 50 && this.getxPosition() + 50 <= playerX + 50);
		boolean isYHit = (playerY <= this.getyPosition() && this.getyPosition() <= playerY + 50)
				|| (playerY <= this.getyPosition() + 50 && this.getyPosition() + 50 <= playerY + 50);
		if (isXHit && isYHit) {
			player.setHp(player.getHp() - 1);
			onObjectIsDestroyed();
			gameController.getMinions().remove(this);
		}
	}
	
	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
	}

}
