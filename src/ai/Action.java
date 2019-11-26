package ai;

import player.PlayerState;

public class Action {

	public static void Dead(AI ai) {
		ai.getAiStatus().isDead = true;
	}

	public static void PlaceBomb(AI ai) {
		ai.useWeapon();
	}

	public static void EscapeBomb(AI ai) {

	}

	public static void RandomWalking(AI ai) {
		AIStatusCheckList aiStatus = ai.getAiStatus();
		// condition
		// method

	}

	public static void GoTo(AI ai, int x, int y) {
		if (ai.getGameController().checkMove(x * 50, y * 50)) {
			AStar astar = new AStar(ai.getGameController());
			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path;
				try {
					path = astar.findPath(ai.getxPosition() / 50, ai.getyPosition() / 50, x, y);
					if (path[0] == ai.getxPosition() / 50 && path[1] == ai.getyPosition() / 50) {
						ai.getAiStatus().moveDirection = PlayerState.IDLE;
						ai.getAiStatus().isMoveComplete = true;
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
				}
			}
		}
	}

}
