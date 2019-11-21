package player;

import controller.GameController;
import gameobject.GameObject;
import gameobject.Moveable;
import javafx.scene.layout.Pane;
import weapon.WeaponType;

public class Player extends GameObject implements Moveable {
	private int hp;
	private int bombCount;
	private int bombRange;
	private int speed;
	private int score;
	
	private WeaponType currentWeapon;
	private boolean hasShield;
	private boolean CanPushBomb;
	private boolean canUseWeapon;
	
	private PlayerState currentPlayerState;
	private int playerNumber;
	private GameController gameController;

	public Player(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer);
		
		setDefaultPlayer();
		
		this.playerNumber = playerNumber;
		this.gameController = gameController;
	}
	
	@Override
	public void move() {
		int xDirectionSpeed = 0;
		int yDirectionSpeed = 0;

		switch (currentPlayerState) {
		case MOVEUP:
		case MOVEDOWN:
			if (xPosition % 50 != 0) {
				int xMid = (xPosition / 50 + 1) * 50;
				currentPlayerState = Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50))
						? PlayerState.MOVELEFT
						: PlayerState.MOVERIGHT;
			}
			break;
		case MOVERIGHT:
		case MOVELEFT:
			if (yPosition % 50 != 0) {
				int yMid = (yPosition / 50 + 1) * 50;
				currentPlayerState = Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50))
						? PlayerState.MOVEUP
						: PlayerState.MOVEDOWN;
			}
			break;
		default:
			break;
		}

		switch (currentPlayerState) {
		case MOVEUP:
			yDirectionSpeed = speed * -1;
			break;
		case MOVERIGHT:
			xDirectionSpeed = speed;
			break;
		case MOVELEFT:
			xDirectionSpeed = speed * -1;
			break;
		case MOVEDOWN:
			yDirectionSpeed = speed;
			break;
		default:
			break;
		}

		if (gameController.isMoveAble(xPosition + xDirectionSpeed, yPosition + yDirectionSpeed, this)) {
			xPosition += xDirectionSpeed;
			yPosition += yDirectionSpeed;
		}
		SetPositionOnScreen();
	}
	
	public boolean isCanUseWeapon() {
		return canUseWeapon;
	}

	public void setCanUseWeapon(int count) {
		this.canUseWeapon = count < this.getBombCount();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getBombRange() {
		return bombRange;
	}

	public void setBombRange(int bombRange) {
		this.bombRange = bombRange;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
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
		return hasShield;
	}

	public void setHasShield(boolean hasShield) {
		this.hasShield = hasShield;
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

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
	private void setDefaultPlayer() {
		setHp(3);
		setBombRange(1);
		setBombCount(3);
		setSpeed(defaultMoveSpeed);
		setCurrentWeapon(WeaponType.BOMB);
		currentPlayerState = PlayerState.IDLE;
	}


}