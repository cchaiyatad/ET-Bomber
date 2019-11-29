package ai;

import java.util.Random;
import controller.GameController;
import controller.ObjectInGame;
import player.PlayerState;

public class Action {

	private static GameController gameController;
	private static AStar astar;
	private static Random random;

	public static void setGameController(GameController gameController) {
		if (Action.gameController == null || astar == null || random == null) {
			Action.gameController = gameController;
			Action.astar = new AStar(gameController);
			Action.random = new Random();
		}
	}

	public static void Dead(AI ai) {
		ai.getAiStatus().isDead = true;
	}

	public static void PlaceBomb(AI ai) {
		ai.useWeapon();
	}

	public static void EscapeBomb(AI ai) {
		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0) && (ai.getAiStatus().moveToX != -1)) {
			return;
		}
		if (!ai.getAiStatus().bombNearBy) {
			return;
		}

		if (ai.getPlayerNumber() == 3) {
			System.out.println("EscapeBomb");
		}
		int hideChoice = -1;

		boolean[] canMove = { true, true, true, true };
		for (int i = 0; i < 4; i++) {
			canMove[i] = !ai.getAiStatus().bombDirection[i];
		}
//		if (ai.getPlayerNumber() == 3) {
//			for (int i = 0; i < 4; i++) {
//				System.out.println(canMove[i]);
//			}
//		}

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
			int[] newxy = AI.calCulatePosition(ai, hideChoice * 2);
			ai.getAiStatus().moveToX = newxy[0];
			ai.getAiStatus().moveToY = newxy[1];
		}

		if (ai.getPlayerNumber() == 3) {
			System.out.println(ai.getAiStatus().moveDirection);
			System.out.println("Current x " + ai.getxPosition() + " " + ai.getxPosition() / 50);
			System.out.println("Current y " + ai.getyPosition() + " " + ai.getyPosition() / 50);
			System.out.println("Move x " + ai.getAiStatus().moveToX);
			System.out.println("Move y " + ai.getAiStatus().moveToY);
			System.out.println("Bombnearby : " + ai.getAiStatus().bombNearBy);
			System.out.println("-----");
		}

	}

	public static void CheckForBomb(AI ai) {
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

	public static void CollectItem(AI ai) {
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
			int[] xy = AI.calCulatePosition(ai, hasItem * 2);
			ai.getAiStatus().moveToX = xy[0];
			ai.getAiStatus().moveToY = xy[1];
		}
	}

	public static void RandomWalking(AI ai) {
//		if (ai.getPlayerNumber() == 3) {
//			System.out.println("--------");
//			System.out.println(ai.getxPosition());
//			System.out.println(ai.getyPosition());
//			System.out.println(ai.getAiStatus().moveToX);
//			System.out.println(ai.getAiStatus().moveToY);
//			System.out.println(ai.getAiStatus().moveDirection);
//		}

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

		if (ai.getPlayerNumber() == 3) {
			System.out.println("Random walk");
		}
//		if (ai.getPlayerNumber() == 3) {
//			System.out.println(ai.objectAroundPlayer[0] + " " + ai.getAiStatus().items[0]);
//			System.out.println(ai.objectAroundPlayer[2] + " " + ai.getAiStatus().items[1]);
//			System.out.println(ai.objectAroundPlayer[4] + " " + ai.getAiStatus().items[2]);
//			System.out.println(ai.objectAroundPlayer[6] + " " + ai.getAiStatus().items[3]);
//		}
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

//		if (ai.getPlayerNumber() == 3) {
//			System.out.println("Next way " + nextWay);
//			System.out.println("Count way " + countWay);
//		}

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

//		if (ai.getPlayerNumber() == 3) {
//			System.out.println("Hit");
//			System.out.println(randomWay);
//			System.out.println("nextWay " + nextWay);
//			System.out.println("opposite " + Math.abs((currentMoveWay + 2) % 4));
//			System.out.println(ai.getAiStatus().moveDirection);
//			System.out.println(countWay);
//			System.out.println("----");
//		}
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

	public static void CheckForWayAndItem(AI ai) {
		for (int i = 0; i < 4; i++) {
			ai.getAiStatus().ways[i] = canMove(ai.objectAroundPlayer[i * 2]);
			ai.getAiStatus().items[i] = isItem(ai.objectAroundPlayer[i * 2]);
		}
	}

	private static boolean canMove(ObjectInGame objectInGame) {
		return objectInGame != null && objectInGame != ObjectInGame.BOMB && objectInGame != ObjectInGame.WALL
				&& objectInGame != ObjectInGame.OBSTACLE;
	}

	private static boolean isItem(ObjectInGame objectInGame) {
		return canMove(objectInGame) && objectInGame != ObjectInGame.EMPTY;
	}

	public static void GoTo(AI ai) {
		int x = ai.getAiStatus().moveToX;
		int y = ai.getAiStatus().moveToY;

		if (x == -2 && y == -2) {
			return;
		}

		if (ai.getPlayerNumber() == 3) {
			System.out.println("Go To " + x + " " + y);
			System.out.println("XPosition : " + ai.getxPosition() + " " + ai.getxPosition() / 50);
			System.out.println("YPosition : " + ai.getyPosition() + " " + ai.getyPosition() / 50);
			System.out.println("Current " + ai.getAiStatus().moveDirection);
		}

		boolean isFinish = false;

		if ((x == -1 && y == -1)) {
			isFinish = true;
		} else if ((Math.abs(ai.getxPosition() - x * 50) < ai.getSpeed())
				&& Math.abs(ai.getyPosition() - y * 50) < ai.getSpeed())
//				&& (ai.getAiStatus().moveDirection == PlayerState.MOVELEFT
//						|| ai.getAiStatus().moveDirection == PlayerState.MOVEUP)) 
		{
			isFinish = true;
		}
//		else if ((Math.abs(ai.getxPosition() + 50 - x * 50) < ai.getSpeed())
//				&& Math.abs(ai.getyPosition() - y * 50) < ai.getSpeed()
//				&& (ai.getAiStatus().moveDirection == PlayerState.MOVERIGHT)) {
//			isFinish = true;
//		} else if ((Math.abs(ai.getxPosition() - x * 50) < ai.getSpeed())
//				&& Math.abs(ai.getyPosition() + 50 - y * 50) < ai.getSpeed()
//				&& (ai.getAiStatus().moveDirection == PlayerState.MOVEDOWN)) {
//			isFinish = true;
//		}

		if (isFinish) {
			System.out.println("Finish");
			ai.getAiStatus().moveDirection = PlayerState.IDLE;
			ai.getAiStatus().isMoving = false;
			ai.getAiStatus().isFinishMoving = true;
			ai.getAiStatus().moveToX = ai.getAiStatus().bombNearBy ? -1 : -2;
			ai.getAiStatus().moveToY = ai.getAiStatus().bombNearBy ? -1 : -2;
			return;
		}

		if (ai.getGameController().checkMove(x * 50, y * 50) || ai.getAiStatus().isMoving == true) {
			ai.getAiStatus().isFinishMoving = false;
			ai.getAiStatus().isMoving = true;

			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = astar.findPath((ai.getxPosition() + 20) / 50, (ai.getyPosition() + 20) / 50, x, y);

					if (path[0] == (ai.getxPosition() + 20) / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
						if (ai.getAiStatus().moveDirection == PlayerState.IDLE) {
							System.out.println("IDLE");
						}
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
					System.out.println("cannot reach");
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
					ai.getAiStatus().isMoving = false;
				}
			}
		}

	}

}
