package ai;

import player.PlayerState;

public class AIStatusCheckList {
	public boolean isDead = false;
	public PlayerState moveDirection = PlayerState.IDLE;
	public PlayerState previousMoveDirection = null;
	public int bombCount;
	public boolean[] playerDead = new boolean[3];
	public boolean bombNearBy = false;
	public boolean hasAction = false;
}
