package ai;

import java.util.BitSet;
import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import player.PlayerState;

public class AI extends PlayerBase {
	private AIStatusCheckList aiStatus = new AIStatusCheckList();
	private ObjectInGame[] objectInSightPlayer = new ObjectInGame[4]; // (wasd)
	private int[] objectRangeInSightPlayer = new int[4]; // (wasd)
	private ObjectInGame[] objectAroundPlayer = new ObjectInGame[8]; // (wqazxcde)
	private PlayerBase[] playerList = new PlayerBase[3];
	private int[] playerInSightRangeList = new int[4]; // (wasd)

	public AI(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase playerOne, PlayerBase playerTwo) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);
		playerList[0] = playerOne;
		playerList[1] = playerTwo;
	}

	public void setOtherAI(PlayerBase ai) {
		this.playerList[2] = ai;
	}

	public void checkStatus() {
//		setCurrentPlayerState(PlayerState.MOVELEFT);
//		checkPlayerInSightObject();

		Action.GoTo(this, 1, 13);
		System.out.println(aiStatus.moveDirection);
		setCurrentPlayerState(aiStatus.moveDirection);
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
			objectRangeInSightPlayer[i] = Math.abs(x - xPosition / 50) + Math.abs(y - yPosition / 50);
		}

//		if (playerNumber == 3) {
//			System.out.println(objectInSightPlayer[0] + " " + playerNumber + " W " + objectRangeInSightPlayer[0]);
//			System.out.println(objectInSightPlayer[1] + " " + playerNumber + " A " + objectRangeInSightPlayer[1]);
//			System.out.println(objectInSightPlayer[2] + " " + playerNumber + " S " + objectRangeInSightPlayer[2]);
//			System.out.println(objectInSightPlayer[3] + " " + playerNumber + " D " + objectRangeInSightPlayer[3]);
//		}

		for (int i = 0; i < 4; i++) {
			playerInSightRangeList[i] = -1;
		}

		for (int i = 0; i < 3; i++) {
			if ((xPosition / 50 == playerList[i].getxPosition() / 50)
					&& (yPosition / 50 == playerList[i].getyPosition() / 50)) {
				continue;
			}
			if (xPosition / 50 == playerList[i].getxPosition() / 50) {
				int diff = (playerList[i].getyPosition() / 50) - (yPosition / 50);
				int index = diff > 0 ? 2 : 0;
				playerInSightRangeList[index] = playerInSightRangeList[index] == -1
						|| Math.abs(diff) < playerInSightRangeList[index] ? Math.abs(diff)
								: playerInSightRangeList[index];

			} else if (yPosition / 50 == playerList[i].getyPosition() / 50) {
				int diff = (playerList[i].getxPosition() / 50) - (xPosition / 50);
				int index = diff > 0 ? 3 : 1;
				playerInSightRangeList[index] = playerInSightRangeList[index] == -1
						|| Math.abs(diff) < playerInSightRangeList[index] ? Math.abs(diff)
								: playerInSightRangeList[index];
			}
		}
//		if (playerNumber == 3) {
//			System.out.println(playerInSightRangeList[0] + " " + playerNumber + " W ");
//			System.out.println(playerInSightRangeList[1] + " " + playerNumber + " A ");
//			System.out.println(playerInSightRangeList[2] + " " + playerNumber + " S ");
//			System.out.println(playerInSightRangeList[3] + " " + playerNumber + " D ");
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

	public AIStatusCheckList getAiStatus() {
		return aiStatus;
	}
}
