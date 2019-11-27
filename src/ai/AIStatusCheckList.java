package ai;

import player.PlayerState;

public class AIStatusCheckList {
	// CheckGame
	public boolean isDead = false;
	public boolean[] playerDead = new boolean[3];

	// Moving
	public int moveToX = -1;
	public int moveToY = -1;
	public PlayerState moveDirection = PlayerState.IDLE;
	public PlayerState previousMoveDirection = null;
	public boolean isMoveComplete = true;
	public boolean isMoving = false;

	// DetectBomb
	public boolean bombNearBy = false;
	public boolean[] bombDirection = new boolean[5];// (WAXDS) 02468
	public int[] bombRange = { -1, -1, -1, -1, -1 };

	// EscapeBomb
	public boolean isEscapeComplete = true;
	public boolean isEscape = false;

	// Attack
	public boolean canUseWeapon = false;

	// ---
	public boolean hasAction = false;

}
