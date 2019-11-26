package ai;

import java.util.BitSet;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import player.PlayerState;

public class AI extends PlayerBase {
	private BitSet aiStatus = new BitSet();
	private ObjectInGame[] objectInSightPlayer = new ObjectInGame[4]; // (wasd)
	private ObjectInGame[] objectAroundPlayer = new ObjectInGame[8]; // (wqazxcde)

	public AI(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);
	}

	public void checkStatus() {
//		setCurrentPlayerState(PlayerState.MOVELEFT);
		checkPlayerInSightObject();
	}

	public void checkPlayerInSightObject() {
		for (int i = 0; i < 4; i++) {
			int x = xPosition / 50;
			int y = yPosition / 50;
			int dx = 0;
			int dy = 0;
			switch (i) {
			case 0:
				dx = 0;
				dy = -1;
				break;
			case 1:
				dx = -1;
				dy = 0;
				break;
			case 2:
				dx = 0;
				dy = 1;
				break;
			case 3:
				dx = 1;
				dy = 0;
				break;
			}
			x += dx;
			y += dy;
			while (gameController.checkMove(x * 50, y * 50)) {
				x += dx;
				y += dy;
			}
			objectInSightPlayer[i] = gameController.getObjectOnPositionXY(x, y);
		}
//		if (playerNumber == 3) {
			System.out.println(objectInSightPlayer[0] + " " + playerNumber + " W");
			System.out.println(objectInSightPlayer[1] + " " + playerNumber + " A");
			System.out.println(objectInSightPlayer[2] + " " + playerNumber + " S");
			System.out.println(objectInSightPlayer[3] + " " + playerNumber + " D");
//		}
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void useWeapon() {
		// TODO Auto-generated method stub

	}

	public BitSet getAiStatus() {
		return aiStatus;
	}
}
