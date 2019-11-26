package ai;

import java.util.BitSet;

import player.PlayerState;

public class Action {

	public static boolean CheckPreCondition(AI ai, int[] condition) {
		// 0 false
		// 1 true
		// -1 unknown
		AIStatusCheckList aiStatus = ai.getAiStatus();
		for (int i = 0; i < condition.length; i++) {
			if (condition[i] == -1) {
				continue;
			}
//			if ((condition[i] == 1) != aiStatus.get(i)) {
//				return false;
//			}
		}
		return true;
	}

	public static void SetAfterActivate(AI ai, int[] condition) {
		// 0 false
		// 1 true
		// -1 unknown
//		BitSet aiStatus = ai.getAiStatus();
//		for (int i = 0; i < condition.length; i++) {
//			if (condition[i] == -1) {
//				continue;
//			}
//			aiStatus.set(i, (condition[i] == 1));
//		}
	}

	public static void Dead(AI ai) {

	}

	public static void PlaceBomb(AI ai) {

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
			System.out.println(((ai.getxPosition()) + " " + x * 50 + " " + (ai.getyPosition()) + " " + y * 50) + " H");
			if ((ai.getxPosition()) != x * 50 || (ai.getyPosition()) != y * 50) {
				int[] path = astar.findPath(ai.getxPosition() / 50, ai.getyPosition() / 50, x, y);
				if (path[0] == ai.getxPosition() / 50 && path[1] == ai.getyPosition() / 50) {
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
				} else if (path[0] == ai.getxPosition() / 50 && Math.abs(path[0] * 50 - ai.getxPosition()) < 20) {
					ai.getAiStatus().moveDirection = ai.getyPosition() / 50 < path[1] ? PlayerState.MOVEDOWN
							: PlayerState.MOVEUP;
				} else if (path[1] == ai.getyPosition() / 50 && Math.abs(path[1] * 50 - ai.getyPosition()) < 20) {
					ai.getAiStatus().moveDirection = ai.getxPosition() / 50 < path[0] ? PlayerState.MOVERIGHT
							: PlayerState.MOVELEFT;
				} else if (path[0] != ai.getxPosition() / 50 && path[1] != ai.getyPosition() / 50) {
					ai.getAiStatus().moveDirection = PlayerState.IDLE;
				}
			}
		}
	}

}
