package ai;

import player.PlayerState;

public class AIStatusCheckList {
	public boolean isDead = false;
	public PlayerState moveDirection = PlayerState.IDLE;
	public PlayerState previousMoveDirection = null;
	public boolean isMoveComplete = true;
	public boolean isMoving = false;
	public boolean canUseWeapon = false;
	public boolean[] playerDead = new boolean[3];
	public boolean bombNearBy = false;
	public boolean hasAction = false;
	public boolean isEscapeComplete = true;
	public boolean isEscape = false;
	public int bombNearbyLocation = -1;
	public int moveToX = -1;
	public int moveToY = -1;
}
