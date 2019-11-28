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

//	public static void EscapeBomb(AI ai, int range) {
//		if (ai.getAiStatus().bombNearBy) {
//			if (ai.getAiStatus().bombNearbyLocation == -1) {
//				ai.getAiStatus().isEscapeComplete = true;
//				return;
//			}
//			int[] xy = AI.calCulatePosition(ai, ai.getAiStatus().bombNearbyLocation);
//			int x = xy[0];
//			int y = xy[1];
//			ai.getAiStatus().isEscapeComplete = false;
//			int aiX = ai.getxPosition() / 50;
//			int aiY = ai.getyPosition() / 50;
//			if (aiX == x || aiY == y) {
//				Random random = new Random();
////				int escapeWay = random.nextInt(2);
//				int escapeWay = 1;
//				for (int i = 0; i < 2; i++) {
//					switch (escapeWay) {
//					// In the same line
//					case 0:
//
//						break;
//					// Find way to hide
//					case 1:
//						int[] hideList = new int[0];
//						if (ai.getPlayerNumber() == 3) {
//							System.out.println(aiX + " " + x + " " + aiY + " " + y);
//						}
//
//						if (aiX == x && aiY == y) {
//							hideList = new int[] { 1, 3, 5, 7 };
//						} else if (aiX == x) {
//							if (Math.abs(aiY - y) == 1) {
//								hideList = aiY > y ? new int[] { 2, 3, 5, 6 } : new int[] { 1, 2, 6, 7 };
//							} else {
//								hideList = new int[] { 1, 2, 3, 5, 6, 7 };
//							}
//						} else if (aiY == y) {
//							if (Math.abs(aiX - x) == 1) {
//								hideList = aiX > x ? new int[] { 0, 4, 5, 7 } : new int[] { 0, 1, 3, 4 };
//							} else {
//								hideList = new int[] { 1, 2, 3, 5, 6, 7 };
//							}
//						}
//
//						List<Integer> shuffleList = new ArrayList<Integer>();
//						for (int j = 0; j < hideList.length; j++) {
//							shuffleList.add(hideList[j]);
//						}
//						Collections.shuffle(shuffleList);
//						while (!shuffleList.isEmpty()) {
//							int index = shuffleList.get(0);
//
//							int[] newxy = AI.calCulatePosition(ai, index);
//							ai.getAiStatus().moveToX = newxy[0];
//							ai.getAiStatus().moveToY = newxy[1];
//							break;
//						}
//						break;
//					}
//					escapeWay = (escapeWay + 1) % 2;
//				}
//			}
//		}
//	}
//
	public static void EscapeBomb(AI ai) {
		if (!ai.getAiStatus().bombNearBy) {
			return;
		}
		int saveRange = 2;
		int hideChoice = -1;
		for (int i = 0; i < 5; i++) {
			if (ai.getAiStatus().bombRange[i] > saveRange) {
				ai.getAiStatus().bombDirection[i] = false;
			}
		}
		if (ai.getAiStatus().bombDirection[4] == true) {
			// TODO
			hideChoice = random.nextInt(4);
		}

		if (hideChoice != -1) {

			int[] newxy = AI.calCulatePosition(ai, hideChoice * 2);
			ai.getAiStatus().moveToX = newxy[0];
			ai.getAiStatus().moveToY = newxy[1];

			System.out.println(ai.getAiStatus().moveToX + " " + ai.getAiStatus().moveToY);
		}

	}

	public static void CheckForBomb(AI ai) {
		ai.getAiStatus().bombNearBy = false;
		ai.getAiStatus().bombRange[4] = -1;
		ai.getAiStatus().bombDirection[4] = false;
		for (int i = 0; i < 4; i++) {
			ai.getAiStatus().bombRange[i] = -1;
			ai.getAiStatus().bombDirection[i] = false;

			if (ai.objectInSightPlayer[i] == ObjectInGame.BOMB) {
				ai.getAiStatus().bombNearBy = true;
				ai.getAiStatus().bombDirection[i] = true;
				ai.getAiStatus().bombRange[i] = ai.objectRangeInSightPlayer[i];
			}
		}
		for (int i = 0; i < 9; i += 2) {
			if (ai.objectAroundPlayer[i] == ObjectInGame.BOMB) {
				ai.getAiStatus().bombNearBy = true;
				ai.getAiStatus().bombRange[i / 2] = 1;
				ai.getAiStatus().bombDirection[i / 2] = true;
			}
		}
	}

	public static void CollectItem(AI ai) {
		int hasItem = -1;
		for (int i = 0; i < 4; i++) {
			if (hasItem == -1 && ai.getAiStatus().items[i]) {
				hasItem = i;
			}
		}
		if (hasItem != -1) {
			System.out.println("Hit");
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

		if ((ai.getxPosition() % 50 != 0 || ai.getyPosition() % 50 != 0)
				|| (ai.getAiStatus().moveToX != -2 && ai.getAiStatus().moveToY != -2)) {
			if (!ai.getAiStatus().isFinishMoving) {
				return;
			}
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
			int temp = 0;
			int i = 5;
			nextWay = random.nextInt(4);
			while (!ai.getAiStatus().ways[nextWay] || nextWay == Math.abs((currentMoveWay + 2) % 4)) {
				nextWay = random.nextInt(4);
				i--;
				if(i == 0) {
					break;
				}
				System.out.println(ai.getAiStatus().moveToX + " " + ai.getAiStatus().moveToY + " "
						+ !ai.getAiStatus().ways[nextWay] + " " + (ai.getPlayerNumber()) + " "
						+ (countWay > 1 && nextWay == Math.abs((currentMoveWay + 2) % 4)) + " " + temp + " "
						+ countWay);
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
		return objectInGame != ObjectInGame.BOMB && objectInGame != ObjectInGame.WALL
				&& objectInGame != ObjectInGame.OBSTACLE;
	}

	private static boolean isItem(ObjectInGame objectInGame) {
		return canMove(objectInGame) && objectInGame != null;
	}

	public static void GoTo(AI ai) {
		int x = ai.getAiStatus().moveToX;
		int y = ai.getAiStatus().moveToY;

		if (x == -2 && y == -2) {
			return;
		}

		if ((x == -1 && y == -1) || (Math.abs(ai.getxPosition() - x * 50) < ai.getSpeed()
				&& Math.abs(ai.getyPosition() - y * 50) < ai.getSpeed())) {
			ai.getAiStatus().moveDirection = PlayerState.IDLE;
			ai.getAiStatus().isMoving = false;
			ai.getAiStatus().isFinishMoving = true;
			ai.getAiStatus().moveToX = -2;
			ai.getAiStatus().moveToY = -2;
			System.out.println("Hello");
			return;
		}

		if (ai.getGameController().checkMove(x * 50, y * 50) || ai.getAiStatus().isMoving == true) {
			ai.getAiStatus().isFinishMoving = false;
			ai.getAiStatus().isMoving = true;
			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = astar.findPath(ai.getxPosition() / 50, ai.getyPosition() / 50, x, y);
					if (path[0] == ai.getxPosition() / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
						ai.getAiStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
								: PlayerState.MOVEUP;
					} else if (path[1] == ai.getyPosition() / 50 && Math.abs(path[1] * 50 - ai.getyPosition()) < 20) {
						ai.getAiStatus().moveDirection = ai.getxPosition() / 50 < path[0] ? PlayerState.MOVERIGHT
								: PlayerState.MOVELEFT;
					}
				} catch (CannotReachDestinateException e) {
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
					ai.getAiStatus().isMoving = false;
				}
			}
		}

	}

}
