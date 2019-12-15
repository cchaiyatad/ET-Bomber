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
		ai.getAIStatus().isDead = ai.getHp() == 0;
	}

	public static void vanish(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		if (ai.getPlayerNumber() != 4) {
			return;
		}

		long currentTime = gameController.getRemainingTime();

		if (currentTime == ai.getAIStatus().nextVanishTime && !ai.getAIStatus().isVanish) {
			ai.getAIStatus().nextShowTime = ai.getAIStatus().nextVanishTime - ai.getAIStatus().vanishTime;
			ai.getAIStatus().isVanish = true;
			ai.getAIStatus().isMakeAction = false;
		}
		if (currentTime == ai.getAIStatus().nextShowTime && ai.getAIStatus().isVanish) {
			ai.getAIStatus().nextVanishTime = ai.getAIStatus().nextShowTime - ai.getAIStatus().showTime;
			ai.getAIStatus().isVanish = false;
			ai.getAIStatus().isMakeAction = false;
		}

		if (!ai.getAIStatus().isMakeAction) {
			ai.getAIStatus().isMakeAction = true;
			ai.setImageShow(!ai.getAIStatus().isVanish);
		}
	}

	public static void spawnMinion(Boss ai) {
		if (ai.getAIStatus().isDead) {
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

		if (gameController.getRemainingTime() % time == 0 && !ai.getAIStatus().isSpawnMinion) {
			ai.getAIStatus().isSpawnMinion = true;
			gameController.getMinions()
					.add(new Minion(ai.getxPosition() / 50 * 50, ai.getyPosition() / 50 * 50,
							gameController.getGamePage().getGameFieldItemPane(), ai.getPlayerNumber(), gameController,
							ai.getPlayer()));
		}

		if (gameController.getRemainingTime() % time != 0) {
			ai.getAIStatus().isSpawnMinion = false;
		}

	}

	public static void escapeBomb(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0) && (ai.getAIStatus().moveToX != -1)) {
			return;
		}
		if (!ai.getAIStatus().bombNearBy) {
			return;
		}

		int hideChoice = -1;

		boolean[] canMove = { true, true, true, true };
		for (int i = 0; i < 4; i++) {
			canMove[i] = !ai.getAIStatus().bombDirection[i];
		}
		boolean hasWay = true;
		for (int i = 0; i < 4; i++) {
			hasWay = canMove[i] && ai.getAIStatus().ways[i];
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
			if (ai.getAIStatus().ways[hideChoice]) {
				break;
			}

		}

		if (hideChoice != -1) {
			int[] newxy = AIBase.calCulatePosition(ai, hideChoice * 2);
			ai.getAIStatus().moveToX = newxy[0];
			ai.getAIStatus().moveToY = newxy[1];
		}

	}

	public static void checkForBomb(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		int saveRange = 2;

		ai.getAIStatus().bombNearBy = false;
		ai.getAIStatus().bombRange[4] = -1;
		ai.getAIStatus().bombDirection[4] = false;
		for (int i = 0; i < 4; i++) {
			ai.getAIStatus().bombRange[i] = -1;
			ai.getAIStatus().bombDirection[i] = false;

			if (ai.objectInSightPlayer[i] == ObjectInGame.BOMB) {
				if ((i % 2 == 0 && ai.objectRangeInSightPlayer[i] <= saveRange)
						|| (i % 2 == 1 && ai.objectRangeInSightPlayer[i] <= saveRange + 1)) {
					ai.getAIStatus().bombNearBy = true;
					ai.getAIStatus().bombDirection[i] = true;
					ai.getAIStatus().bombRange[i] = ai.objectRangeInSightPlayer[i];
				}
			}
		}
		for (int i = 0; i < 9; i++) {
			if (ai.objectAroundPlayer[i] == ObjectInGame.BOMB) {
				ai.getAIStatus().bombNearBy = true;
				if (i % 2 == 0) {
					ai.getAIStatus().bombRange[i / 2] = 1;
					ai.getAIStatus().bombDirection[i / 2] = true;
				}
			}
		}
	}

	public static void collectItem(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		if (ai.getAIStatus().bombNearBy) {
			return;
		}
		int hasItem = -1;
		for (int i = 0; i < 4; i++) {
			if (hasItem == -1 && ai.getAIStatus().items[i]) {
				hasItem = i;
			}
		}
		if (hasItem != -1) {
			int[] xy = AIBase.calCulatePosition(ai, hasItem * 2);
			ai.getAIStatus().moveToX = xy[0];
			ai.getAIStatus().moveToY = xy[1];
		}
	}

	public static void randomWalking(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		if (ai.getAIStatus().bombNearBy && ai.objectAroundPlayer[8] != ObjectInGame.BOMB) {
			return;
		}

		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0)
				|| (ai.getAIStatus().moveToX != -2 && ai.getAIStatus().moveToY != -2) || ((!ai.getAIStatus().bombNearBy
						&& (ai.getAIStatus().moveToX == -1 && ai.getAIStatus().moveToY == -1)))) {
			if (!ai.getAIStatus().isFinishMoving) {
				return;
			}
		}
		ai.getAIStatus().isFinishMoving = false;
		int currentMoveWay = -1;
		int nextWay = -1;
		int countWay = 0;
		boolean randomWay = false;

		switch (ai.getAIStatus().moveDirection) {
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
			countWay = ai.getAIStatus().ways[i] ? countWay + 1 : countWay;
		}

		if (countWay == 1) {
			int position = 0;
			for (int i = 0; i < 4; i++) {
				if (ai.getAIStatus().ways[i]) {
					position = i;
					break;
				}
			}
			nextWay = Math.abs((currentMoveWay + 2) % 4);
			nextWay = position % 2 == nextWay % 2 ? nextWay : (nextWay + 1) % 4;

		} else if (countWay == 2) {
			if (((ai.getAIStatus().ways[0] && ai.getAIStatus().ways[2] && (currentMoveWay == 0 || currentMoveWay == 2))
					|| (ai.getAIStatus().ways[1] && ai.getAIStatus().ways[3]
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
			while (!ai.getAIStatus().ways[nextWay] || nextWay == Math.abs((currentMoveWay + 2) % 4)) {
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
		ai.getAIStatus().moveDirection = newMoveState;

	}

	public static void checkForWayAndItem(AIBase ai) {
		if (ai.getAIStatus().isDead) {
			return;
		}
		for (int i = 0; i < 4; i++) {
			ai.getAIStatus().ways[i] = canMove(ai, ai.objectAroundPlayer[i * 2]);
			ai.getAIStatus().items[i] = isItem(ai, ai.objectAroundPlayer[i * 2]);
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
		if (ai.getAIStatus().isDead) {
			return;
		}
		int x = ai.getAIStatus().moveToX;
		int y = ai.getAIStatus().moveToY;

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
			ai.getAIStatus().moveDirection = PlayerState.IDLE;
			ai.getAIStatus().isMoving = false;
			ai.getAIStatus().isFinishMoving = true;
			ai.getAIStatus().moveToX = ai.getAIStatus().bombNearBy ? -1 : -2;
			ai.getAIStatus().moveToY = ai.getAIStatus().bombNearBy ? -1 : -2;
			return;
		}

		if (ai.getGameController().checkMove(x * 50, y * 50, ai) || ai.getAIStatus().isMoving == true) {
			ai.getAIStatus().isFinishMoving = false;
			ai.getAIStatus().isMoving = true;

			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = pathFinding.findPath((ai.getxPosition() + 20) / 50, (ai.getyPosition() + 20) / 50, x, y, ai);

					if (path[0] == (ai.getxPosition() + 20) / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
						if (ai.getAIStatus().moveDirection == PlayerState.MOVELEFT
								|| ai.getAIStatus().moveDirection == PlayerState.MOVERIGHT) {
							if (Math.abs(path[1] * 50 - ai.getyPosition()) > ai.getSpeed()) {
								ai.getAIStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
										: PlayerState.MOVEUP;
							}
						} else {
							ai.getAIStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
									: PlayerState.MOVEUP;
						}
					} else if (path[1] == (ai.getyPosition() + 20) / 50
							&& Math.abs(path[1] * 50 - ai.getyPosition()) < 20) {
						ai.getAIStatus().moveDirection = ai.getxPosition() / 50 < path[0] ? PlayerState.MOVERIGHT
								: PlayerState.MOVELEFT;
					}
				} catch (CannotReachDestinateException e) {
					System.out.println("cannot reach " + ai.getPlayerNumber() + " " + ai.getAIStatus().moveToX + " "
							+ ai.getAIStatus().moveToY);
					ai.getAIStatus().moveToX = -2;
					ai.getAIStatus().moveToY = -2;
					ai.getAIStatus().moveDirection = PlayerState.IDLE;
					ai.getAIStatus().isMoving = false;
				}
			}
		}

	}

}
