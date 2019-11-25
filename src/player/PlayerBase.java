package player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import controller.GameController;
import gameobject.Destroyable;
import gameobject.GameObject;
import gameobject.Moveable;
import javafx.scene.layout.Pane;
import weapon.Bomb;
import weapon.WeaponType;

public abstract class PlayerBase extends GameObject implements Moveable, Destroyable {
	private final long shieldDuration = 5;

	protected int hp;
	protected int bombCount;
	protected int bombRange;
	protected int speed;
	protected int score;

	protected WeaponType currentWeapon;
	protected long shieldTime;
	protected boolean CanPushBomb;
	protected boolean canUseWeapon;

	protected PlayerState currentPlayerState;
	protected int playerNumber;
	protected GameController gameController;
	protected Queue<Bomb> countBomb;

	public abstract void useWeapon();

	public PlayerBase(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer);
		this.playerNumber = playerNumber;
		this.gameController = gameController;
		setDefaultPlayer();
	}

	protected void setDefaultPlayer() {
		setHp(3);
		setBombRange(3);
		setBombCount(1);
		countBomb = new LinkedList<Bomb>();
		setCanUseWeapon();
		shieldTime = 0;

		setSpeed(defaultMoveSpeed);
		setCurrentWeapon(WeaponType.BOMB);
		currentPlayerState = PlayerState.IDLE;

	}

	public boolean isCanUseWeapon() {
		return canUseWeapon;
	}

	public void setCanUseWeapon() {
		this.canUseWeapon = countBomb.size() < this.getBombCount();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp < this.hp && isHasShield()) {
			return;
		}
		if (hp <= 0) {
			hp = 0;
			this.layer.getChildren().remove(this.imageView);
			currentPlayerState = PlayerState.DEAD;
		}
		this.hp = hp;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		bombCount = bombCount > 9 ? 9 : bombCount;
		this.bombCount = bombCount;
	}

	public int getBombRange() {
		return bombRange;
	}

	public void setBombRange(int bombRange) {
		bombRange = bombRange > 9 ? 9 : bombRange;
		this.bombRange = bombRange;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		speed = speed > 10 ? 10 : speed;
		this.speed = speed;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public WeaponType getCurrentWeapon() {
		return currentWeapon;
	}

	public void setCurrentWeapon(WeaponType currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public boolean isHasShield() {
		return shieldTime > TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public void setShield() {
		this.shieldTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) + shieldDuration;
	}

	public boolean isCanPushBomb() {
		return CanPushBomb;
	}

	public void setCanPushBomb(boolean canPushBomb) {
		CanPushBomb = canPushBomb;
	}

	public PlayerState getCurrentPlayerState() {
		return currentPlayerState;
	}

	public void setCurrentPlayerState(PlayerState currentPlayerState) {
		this.currentPlayerState = currentPlayerState;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public boolean isDead() {
		return currentPlayerState == PlayerState.DEAD;
	}

	public Queue<Bomb> getCountBomb() {
		return countBomb;
	}

}
