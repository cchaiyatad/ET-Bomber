package ai;

import java.util.BitSet;

public class Action {

	public static boolean CheckPreCondition(AI ai, byte[] condition) {
		// 0 false
		// 1 true
		// -1 unknown
		BitSet aiStatus = ai.getAiStatus();
		for (int i = 0; i < condition.length; i++) {
			if (condition[i] == -1) {
				continue;
			}
			if ((condition[i] == 1) != aiStatus.get(i)) {
				return false;
			}
		}
		return true;
	}

	public static void SetAfterActivate(AI ai, byte[] condition) {
		// 0 false
		// 1 true
		// -1 unknown
		BitSet aiStatus = ai.getAiStatus();
		for (int i = 0; i < condition.length; i++) {
			if (condition[i] == -1) {
				continue;
			}
			aiStatus.set(i, (condition[i] == 1));
		}
	}
	
	public static void Dead(AI ai) {
		
	}

	public static void PlaceBomb(AI ai) {

	}
	
	public static void EscapeBomb(AI ai) {
		
	}

}
