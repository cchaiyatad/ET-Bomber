package ai;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class AIBase extends PlayerBase {
	private AIStatusCheckList aiStatus = new AIStatusCheckList();

	public ObjectInGame[] objectInSightPlayer = new ObjectInGame[4]; // (wasd)
	public int[] objectRangeInSightPlayer = new int[4]; // (wasd)
	public ObjectInGame[] objectAroundPlayer = new ObjectInGame[9]; // (wqazxcdes)

	@SuppressWarnings("unused")
	private PlayerBase player;
//	private int[] playerInSightRangeList = new int[4]; // (wasd)

	public AIBase(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController, PlayerBase player) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);
		this.player = player;
		Action.setGameController(gameController);
	}

	public void checkStatus() {
		Action.Dead(this);
		aiStatus.canUseWeapon = this.canUseWeapon;
		checkPlayerAndObjectInSight();
		Action.CheckForBomb(this);
		Action.CheckForWayAndItem(this);
		Action.EscapeBomb(this);
		Action.CollectItem(this);
		Action.RandomWalking(this);
		Action.GoTo(this);
		setCurrentPlayerState(aiStatus.moveDirection);
		if (getPlayerNumber() == 3) {
//			System.out.println(getAiStatus().moveDirection);
//			System.out.println("Current x " + getxPosition() + " " + getxPosition() / 50);
//			System.out.println("Current y " + getyPosition() + " " + getyPosition() / 50);
//			System.out.println("Move x " + getAiStatus().moveToX);
//			System.out.println("Move y " + getAiStatus().moveToY);
//			System.out.println("Bombnearby : " + getAiStatus().bombNearBy);
//			System.out.println("-----");
			System.out.println(this.objectAroundPlayer[8]);
		}

	}

	public void checkPlayerAndObjectInSight() {
		for (int i = 0; i < 4; i++) {
			int x = (xPosition) / 50;
			int y = (yPosition) / 50;
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

//		for (int i = 0; i < 4; i++) {
//			playerInSightRangeList[i] = -1;
//		}

//		for (int i = 0; i < 3; i++) {
//			if ((xPosition / 50 == playerList[i].getxPosition() / 50)
//					&& (yPosition / 50 == playerList[i].getyPosition() / 50)) {
//				continue;
//			}
//			if (xPosition / 50 == playerList[i].getxPosition() / 50) {
//				int diff = (playerList[i].getyPosition() / 50) - (yPosition / 50);
//				int index = diff > 0 ? 2 : 0;
//				playerInSightRangeList[index] = playerInSightRangeList[index] == -1
//						|| Math.abs(diff) < playerInSightRangeList[index] ? Math.abs(diff)
//								: playerInSightRangeList[index];
//
//			} else if (yPosition / 50 == playerList[i].getyPosition() / 50) {
//				int diff = (playerList[i].getxPosition() / 50) - (xPosition / 50);
//				int index = diff > 0 ? 3 : 1;
//				playerInSightRangeList[index] = playerInSightRangeList[index] == -1
//						|| Math.abs(diff) < playerInSightRangeList[index] ? Math.abs(diff)
//								: playerInSightRangeList[index];
//			}
//		}

		for (int i = 0; i < 9; i++) {
			int[] xy = calCulatePosition(this, i);
			objectAroundPlayer[i] = gameController.getObjectOnPositionXY(xy[0], xy[1]);
		}
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	public AIStatusCheckList getAiStatus() {
		return aiStatus;
	}

	public static int[] calCulatePosition(AIBase ai, int i) {
		int x = (ai.getxPosition()) / 50;
		int y = (ai.getyPosition()) / 50;
		int dx = 0;
		int dy = 0;
		switch (i) {
		case 0:
			dx = 0;
			dy = -1;
			break;
		case 1:
			dx = -1;
			dy = -1;
			break;
		case 2:
			dx = -1;
			dy = 0;
			break;
		case 3:
			dx = -1;
			dy = 1;
			break;
		case 4:
			dx = 0;
			dy = 1;
			break;
		case 5:
			dx = 1;
			dy = 1;
			break;
		case 6:
			dx = 1;
			dy = 0;
			break;
		case 7:
			dx = 1;
			dy = -1;
			break;
		case 8:
			break;
		default:
			break;
		}
		return new int[] { x + dx, y + dy };
	}

	@Override
	public boolean isDead() {
		return getAiStatus().isDead;
	}
}
