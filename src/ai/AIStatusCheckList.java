package ai;

import player.PlayerState;

public class AIStatusCheckList {
	// CheckGame
	public boolean isDead = false;
	public boolean[] playerDead = new boolean[3];

	// Moving
	// -2 is random 
	// -1 has nowhere to go
	public int moveToX = -2;
	public int moveToY = -2;
	public PlayerState moveDirection = PlayerState.IDLE;
	public PlayerState previousMoveDirection = null;
	public boolean isMoving = false;
	public boolean[] ways = new boolean[4];
	public boolean[] items = new boolean[4];

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
