package ai;

import java.util.Random;
import controller.GameController;
import controller.ObjectInGame;
import player.PlayerState;

public class Action {

	private static GameController gameController;
	private static PathFinding pathFinding;
	private static Random random;

	public static void setGameController(GameController gameController) {
		if (Action.gameController == null || pathFinding == null || random == null) {
			Action.gameController = gameController;
			Action.pathFinding = new PathFinding(gameController);
			Action.random = new Random();
		}
	}

	public static void dead(AIBase ai) {
		ai.getAiStatus().isDead = ai.getHp() == 0;
	}

	public static void vanish(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		if (ai.getPlayerNumber() != 4) {
			return;
		}

		long currentTime = gameController.getRemainingTime();

		if (currentTime == ai.getAiStatus().nextVanishTime && !ai.getAiStatus().isVanish) {
			ai.getAiStatus().nextShowTime = ai.getAiStatus().nextVanishTime - ai.getAiStatus().vanishTime;
			ai.getAiStatus().isVanish = true;
			ai.getAiStatus().isMakeAction = false;
		}
		if (currentTime == ai.getAiStatus().nextShowTime && ai.getAiStatus().isVanish) {
			ai.getAiStatus().nextVanishTime = ai.getAiStatus().nextShowTime - ai.getAiStatus().showTime;
			ai.getAiStatus().isVanish = false;
			ai.getAiStatus().isMakeAction = false;
		}

		if (!ai.getAiStatus().isMakeAction) {
			ai.getAiStatus().isMakeAction = true;
			ai.setImageShow(!ai.getAiStatus().isVanish);
		}
	}

	public static void spawnMinion(Boss ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}

		int time = 0;
		if (gameController.getRemainingTime() > 60) {
			time = 15;
		} else if (gameController.getRemainingTime() > 30) {
			time = 10;
		} else if (gameController.getRemainingTime() >= 0) {
			time = 8;
		}

		String minionImagePath = "minion";
		switch (ai.getPlayerNumber()) {
		case 2:
			minionImagePath += "One";
			break;
		case 3:
			minionImagePath += "Two";
			break;
		case 4:
			minionImagePath += "Three";
			break;
		}

		if (gameController.getRemainingTime() % time == 0 && !ai.getAiStatus().isSpawnMinion) {
			ai.getAiStatus().isSpawnMinion = true;
			gameController.getMinions()
					.add(new Minion(ai.getxPosition() / 50 * 50, ai.getyPosition() / 50 * 50, minionImagePath,
							gameController.getGamePage().getGameFieldItemPane(), ai.getPlayerNumber(), gameController,
							ai.getPlayer()));
		}

		if (gameController.getRemainingTime() % time != 0) {
			ai.getAiStatus().isSpawnMinion = false;
		}

	}

	public static void escapeBomb(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0) && (ai.getAiStatus().moveToX != -1)) {
			return;
		}
		if (!ai.getAiStatus().bombNearBy) {
			return;
		}

		int hideChoice = -1;

		boolean[] canMove = { true, true, true, true };
		for (int i = 0; i < 4; i++) {
			canMove[i] = !ai.getAiStatus().bombDirection[i];
		}
		boolean hasWay = true;
		for (int i = 0; i < 4; i++) {
			hasWay = canMove[i] && ai.getAiStatus().ways[i];
			if (hasWay) {
				break;
			}
		}

		while (hasWay) {
			int count = 0;
			for (int i = 0; i < 4; i++) {
				count = canMove[i] ? count + 1 : count;
			}
			if (count == 4) {
				return;
			}
			hideChoice = random.nextInt(4);
			if (!canMove[hideChoice]) {
				continue;
			}
			canMove[hideChoice] = false;
			if (ai.getAiStatus().ways[hideChoice]) {
				break;
			}

		}

		if (hideChoice != -1) {
			int[] newxy = AIBase.calCulatePosition(ai, hideChoice * 2);
			ai.getAiStatus().moveToX = newxy[0];
			ai.getAiStatus().moveToY = newxy[1];
		}

	}

	public static void checkForBomb(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		int saveRange = 2;

		ai.getAiStatus().bombNearBy = false;
		ai.getAiStatus().bombRange[4] = -1;
		ai.getAiStatus().bombDirection[4] = false;
		for (int i = 0; i < 4; i++) {
			ai.getAiStatus().bombRange[i] = -1;
			ai.getAiStatus().bombDirection[i] = false;

			if (ai.objectInSightPlayer[i] == ObjectInGame.BOMB) {
				if ((i % 2 == 0 && ai.objectRangeInSightPlayer[i] <= saveRange)
						|| (i % 2 == 1 && ai.objectRangeInSightPlayer[i] <= saveRange + 1)) {
					ai.getAiStatus().bombNearBy = true;
					ai.getAiStatus().bombDirection[i] = true;
					ai.getAiStatus().bombRange[i] = ai.objectRangeInSightPlayer[i];
				}
			}
		}
		for (int i = 0; i < 9; i++) {
			if (ai.objectAroundPlayer[i] == ObjectInGame.BOMB) {
				ai.getAiStatus().bombNearBy = true;
				if (i % 2 == 0) {
					ai.getAiStatus().bombRange[i / 2] = 1;
					ai.getAiStatus().bombDirection[i / 2] = true;
				}
			}
		}
	}

	public static void collectItem(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		if (ai.getAiStatus().bombNearBy) {
			return;
		}
		int hasItem = -1;
		for (int i = 0; i < 4; i++) {
			if (hasItem == -1 && ai.getAiStatus().items[i]) {
				hasItem = i;
			}
		}
		if (hasItem != -1) {
			int[] xy = AIBase.calCulatePosition(ai, hasItem * 2);
			ai.getAiStatus().moveToX = xy[0];
			ai.getAiStatus().moveToY = xy[1];
		}
	}

	public static void randomWalking(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		if (ai.getAiStatus().bombNearBy && ai.objectAroundPlayer[8] != ObjectInGame.BOMB) {
			return;
		}

		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0)
				|| (ai.getAiStatus().moveToX != -2 && ai.getAiStatus().moveToY != -2) || ((!ai.getAiStatus().bombNearBy
						&& (ai.getAiStatus().moveToX == -1 && ai.getAiStatus().moveToY == -1)))) {
			if (!ai.getAiStatus().isFinishMoving) {
				return;
			}
		}
		ai.getAiStatus().isFinishMoving = false;
		int currentMoveWay = -1;
		int nextWay = -1;
		int countWay = 0;
		boolean randomWay = false;

		switch (ai.getAiStatus().moveDirection) {
		case MOVEUP:
			currentMoveWay = 0;
			break;
		case MOVELEFT:
			currentMoveWay = 1;
			break;
		case MOVEDOWN:
			currentMoveWay = 2;
			break;
		case MOVERIGHT:
			currentMoveWay = 3;
			break;
		case IDLE:
			randomWay = true;
			break;
		default:
			break;
		}

		for (int i = 0; i < 4; i++) {
			countWay = ai.getAiStatus().ways[i] ? countWay + 1 : countWay;
		}

		if (countWay == 1) {
			int position = 0;
			for (int i = 0; i < 4; i++) {
				if (ai.getAiStatus().ways[i]) {
					position = i;
					break;
				}
			}
			nextWay = Math.abs((currentMoveWay + 2) % 4);
			nextWay = position % 2 == nextWay % 2 ? nextWay : (nextWay + 1) % 4;

		} else if (countWay == 2) {
			if (((ai.getAiStatus().ways[0] && ai.getAiStatus().ways[2] && (currentMoveWay == 0 || currentMoveWay == 2))
					|| (ai.getAiStatus().ways[1] && ai.getAiStatus().ways[3]
							&& (currentMoveWay == 1 || currentMoveWay == 3)))
					&& currentMoveWay != -1) {
				nextWay = currentMoveWay;
			} else {
				randomWay = true;
			}
		}

		if (countWay != 0 && (countWay > 2 || randomWay)) {
			int i = 5;
			nextWay = random.nextInt(4);
			while (!ai.getAiStatus().ways[nextWay] || nextWay == Math.abs((currentMoveWay + 2) % 4)) {
				nextWay = random.nextInt(4);
				i--;
				if (i == 0) {
					break;
				}
			}
		}

		PlayerState newMoveState = PlayerState.IDLE;
		switch (nextWay) {
		case 0:
			newMoveState = PlayerState.MOVEUP;
			break;
		case 1:
			newMoveState = PlayerState.MOVELEFT;
			break;
		case 2:
			newMoveState = PlayerState.MOVEDOWN;
			break;
		case 3:
			newMoveState = PlayerState.MOVERIGHT;
			break;
		default:
			break;
		}
		ai.getAiStatus().moveDirection = newMoveState;

	}

	public static void checkForWayAndItem(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		for (int i = 0; i < 4; i++) {
			ai.getAiStatus().ways[i] = canMove(ai, ai.objectAroundPlayer[i * 2]);
			ai.getAiStatus().items[i] = isItem(ai, ai.objectAroundPlayer[i * 2]);
		}
	}

	private static boolean canMove(AIBase ai, ObjectInGame objectInGame) {
		if (ai.getPlayerNumber() == 3) {
			return objectInGame != null && objectInGame != ObjectInGame.BOMB && objectInGame != ObjectInGame.WALL;
		}
		return objectInGame != null && objectInGame != ObjectInGame.BOMB && objectInGame != ObjectInGame.WALL
				&& objectInGame != ObjectInGame.OBSTACLE;
	}

	private static boolean isItem(AIBase ai, ObjectInGame objectInGame) {
		if (ai.getPlayerNumber() == 3) {
			return canMove(ai, objectInGame) && objectInGame != ObjectInGame.EMPTY
					&& objectInGame != ObjectInGame.OBSTACLE;
		}
		return canMove(ai, objectInGame) && objectInGame != ObjectInGame.EMPTY;
	}

	public static void goTo(AIBase ai) {
		if (ai.getAiStatus().isDead) {
			return;
		}
		int x = ai.getAiStatus().moveToX;
		int y = ai.getAiStatus().moveToY;

		if (x == -2 && y == -2) {
			return;
		}

		boolean isFinish = false;

		if ((x == -1 && y == -1)) {
			isFinish = true;
		} else if ((Math.abs(ai.getxPosition() - x * 50) < ai.getSpeed())
				&& Math.abs(ai.getyPosition() - y * 50) < ai.getSpeed()) {
			isFinish = true;
		}
		if (isFinish) {
			ai.getAiStatus().moveDirection = PlayerState.IDLE;
			ai.getAiStatus().isMoving = false;
			ai.getAiStatus().isFinishMoving = true;
			ai.getAiStatus().moveToX = ai.getAiStatus().bombNearBy ? -1 : -2;
			ai.getAiStatus().moveToY = ai.getAiStatus().bombNearBy ? -1 : -2;
			return;
		}

		if (ai.getGameController().checkMove(x * 50, y * 50, ai) || ai.getAiStatus().isMoving == true) {
			ai.getAiStatus().isFinishMoving = false;
			ai.getAiStatus().isMoving = true;

			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = pathFinding.findPath((ai.getxPosition() + 20) / 50, (ai.getyPosition() + 20) / 50, x, y, ai);

					if (path[0] == (ai.getxPosition() + 20) / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
						if (ai.getAiStatus().moveDirection == PlayerState.MOVELEFT
								|| ai.getAiStatus().moveDirection == PlayerState.MOVERIGHT) {
							if (Math.abs(path[1] * 50 - ai.getyPosition()) > ai.getSpeed()) {
								ai.getAiStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
										: PlayerState.MOVEUP;
							}
						} else {
							ai.getAiStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
									: PlayerState.MOVEUP;
						}
					} else if (path[1] == (ai.getyPosition() + 20) / 50
							&& Math.abs(path[1] * 50 - ai.getyPosition()) < 20) {
						ai.getAiStatus().moveDirection = ai.getxPosition() / 50 < path[0] ? PlayerState.MOVERIGHT
								: PlayerState.MOVELEFT;
					}
				} catch (CannotReachDestinateException e) {
					System.out.println("cannot reach " + ai.getPlayerNumber() + " " + ai.getAiStatus().moveToX + " "
							+ ai.getAiStatus().moveToY);
					ai.getAiStatus().moveToX = -2;
					ai.getAiStatus().moveToY = -2;
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
					ai.getAiStatus().isMoving = false;
				}
			}
		}

	}

}
