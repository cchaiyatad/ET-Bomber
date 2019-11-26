package player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import controller.GameController;
import javafx.scene.layout.Pane;
import weapon.*;
public class Player extends PlayerBase {
	
	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer, playerNumber, gameController);	
	}
	
	@Override
	public void useWeapon() {
		int x = xPosition / 50;
		int y = yPosition / 50;
		if (xPosition % 50 != 0) {
			int xMid = (xPosition / 50 + 1) * 50;
			x = Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50)) ? x : x + 1;
		} else if (yPosition % 50 != 0) {
			int yMid = (yPosition / 50 + 1) * 50;
			y = Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50)) ? y : y + 1;
		}

		switch (getCurrentWeapon()) {
		case BOMB:
			if (this.gameController.canSetObject(x, y)) {
				this.gameController.setObjectInGame(x, y,
						new Bomb(x * 50, y * 50, this.getGameController().getGamePage().getGameFieldItemPane(),
								getBombRange(), this, this.getGameController()));
			}
			break;
		default:
			break;
		}
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
		this.shieldTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) + getShieldDuration();
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

	public Queue<Bomb> getCountBomb() {
		return countBomb;
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

}