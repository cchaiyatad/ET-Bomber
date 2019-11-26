package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import controller.ObjectInGame;
import player.PlayerState;

public class Action {

	public static void Dead(AI ai) {
		ai.getAiStatus().isDead = true;
	}

	public static void PlaceBomb(AI ai) {
		ai.useWeapon();
	}

	public static void EscapeBomb(AI ai, int range) {
		if (ai.getAiStatus().bombNearBy) {
			if (ai.getAiStatus().bombNearbyLocation == -1) {
				ai.getAiStatus().isEscapeComplete = true;
				return;
			}
			int[] xy = AI.calCulatePosition(ai, ai.getAiStatus().bombNearbyLocation);
			int x = xy[0];
			int y = xy[1];
			ai.getAiStatus().isEscapeComplete = false;
			int aiX = ai.getxPosition() / 50;
			int aiY = ai.getyPosition() / 50;
			if (aiX == x || aiY == y) {
				Random random = new Random();
//				int escapeWay = random.nextInt(2);
				int escapeWay = 1;
				for (int i = 0; i < 2; i++) {
					switch (escapeWay) {
					// In the same line
					case 0:

						break;
					// Find way to hide
					case 1:
						int[] hideList = new int[0];
						if (aiX == aiY) {
							hideList = new int[] { 1, 3, 5, 7 };
						} else if (aiX == x) {
							if (Math.abs(aiY - y) == 1) {
								hideList = aiY > y ? new int[] { 2, 3, 5, 6 } : new int[] { 1, 2, 6, 7 };
							} else {
								hideList = new int[] { 1, 2, 3, 5, 6, 7 };
							}
						} else if (aiY == y) {
							if (Math.abs(aiX - x) == 1) {
								hideList = aiX > x ? new int[] { 0, 4, 5, 7 } : new int[] { 0, 1, 3, 4 };
							} else {
								hideList = new int[] { 1, 2, 3, 5, 6, 7 };
							}
						}
						List<Integer> shuffleList = new ArrayList<Integer>();
						for (int j = 0; j < hideList.length; j++) {
							shuffleList.add(hideList[j]);
						}
						Collections.shuffle(shuffleList);
						while (!shuffleList.isEmpty()) {
							int index = shuffleList.get(0);

							int[] newxy = AI.calCulatePosition(ai, index);
							if (ai.getPlayerNumber() == 3) {
								System.out.println("Hit " + index + " " + newxy[0] + " " + newxy[1] + " "
										+ ai.getxPosition() + " " + ai.getyPosition());
							}
							ai.getAiStatus().moveToX = newxy[0];
							ai.getAiStatus().moveToY = newxy[1];
							break;
//							shuffleList.remove(0);
//							if (ai.getAiStatus().isMoveComplete == true) {
//								ai.getAiStatus().isEscapeComplete = true;
//								break;
//							}
						}
						break;
					}
					escapeWay = (escapeWay + 1) % 2;
				}
			}
		}
	}

	public static void CheckForBomb(AI ai) {
		for (int i = 0; i < 9; i++) {
			if (ai.objectAroundPlayer[i] == ObjectInGame.BOMB) {
				ai.getAiStatus().bombNearBy = true;
				ai.getAiStatus().bombNearbyLocation = i;
				return;
			}
		}
		ai.getAiStatus().bombNearBy = false;
	}

	public static void RandomWalking(AI ai) {
		AIStatusCheckList aiStatus = ai.getAiStatus();
		// condition
		// method

	}

	public static void GoTo(AI ai) {
		ai.getAiStatus().isMoveComplete = false;
		int x = ai.getAiStatus().moveToX;
		int y = ai.getAiStatus().moveToY;

		if (x == -1 && y == -1) {
			ai.getAiStatus().moveDirection = PlayerState.IDLE;
			ai.getAiStatus().isMoveComplete = true;
			ai.getAiStatus().isMoving = false;
			return;
		}

		if (ai.getGameController().checkMove(x * 50, y * 50) || ai.getAiStatus().isMoving == true) {
			AStar astar = new AStar(ai.getGameController());
			ai.getAiStatus().isMoving = true;
			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = astar.findPath(ai.getxPosition() / 50, ai.getyPosition() / 50, x, y);
					if (path[0] * 50 == ai.getxPosition() * 50 && path[1] * 50 == ai.getyPosition() * 50) {
						ai.getAiStatus().moveDirection = PlayerState.IDLE;
						ai.getAiStatus().isMoveComplete = true;
						ai.getAiStatus().isMoving = false;
						ai.getAiStatus().moveToX = -1;
						ai.getAiStatus().moveToY = -1;

					} else if (path[0] == ai.getxPosition() / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
						ai.getAiStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
								: PlayerState.MOVEUP;
					} else if (path[1] == ai.getyPosition() / 50 && Math.abs(path[1] * 50 - ai.getyPosition()) < 20) {
						ai.getAiStatus().moveDirection = ai.getxPosition() / 50 < path[0] ? PlayerState.MOVERIGHT
								: PlayerState.MOVELEFT;
					}
				} catch (CannotReachDestinateException e) {
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
					ai.getAiStatus().isMoveComplete = false;
					ai.getAiStatus().isMoving = false;
				}
			}
		}

	}

}
