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

public abstract class PlayerBase extends GameObject implements Moveable, Destroyable {
	private long shieldDuration = 5;

	protected int hp;
	protected int bombCount;
	protected int bombRange;
	protected int speed;

	protected long shieldTime;
	protected boolean canUseWeapon;

	protected PlayerState currentPlayerState;
	protected int playerNumber;
	protected GameController gameController;
	protected Queue<Bomb> countBomb;

	public PlayerBase(int xPosition, int yPosition, String imagePath, Pane layer, int playerNumber,
			GameController gameController) {
		super(xPosition, yPosition, imagePath, layer);
		this.playerNumber = playerNumber;
		this.gameController = gameController;
		setDefaultPlayer();
	}

	protected void setDefaultPlayer() {
		setHp(3);
		setBombRange(2);
		setBombCount(1);
		countBomb = new LinkedList<Bomb>();
		setCanUseWeapon();
		shieldTime = 0;

		setSpeed(defaultMoveSpeed);
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

	public boolean isHasShield() {
		return shieldTime > TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public void setShield() {
		this.shieldTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) + shieldDuration;
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

	@Override
	public void move() {
		int xDirectionSpeed = 0;
		int yDirectionSpeed = 0;

		switch (currentPlayerState) {
		case MOVEUP:
		case MOVEDOWN:
			if (xPosition % 50 != 0) {
				int xMid = (xPosition / 50 + 1) * 50;
				if (Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50))) {
					currentPlayerState = PlayerState.MOVELEFT;
					int dx = xPosition % 50;
					xDirectionSpeed = dx < speed ? dx * -1 : speed * -1;
				} else {
					currentPlayerState = PlayerState.MOVERIGHT;
					int dx = 50 - (xPosition % 50);
					xDirectionSpeed = dx < speed ? dx : speed;
				}
				xPosition += xDirectionSpeed;
				SetPositionOnScreen();
				return;
			}
			break;

		case MOVERIGHT:
		case MOVELEFT:
			if (yPosition % 50 != 0) {
				int yMid = (yPosition / 50 + 1) * 50;
				if (Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50))) {
					currentPlayerState = PlayerState.MOVEUP;
					int dy = yPosition % 50;
					yDirectionSpeed = dy < speed * -1 ? dy : speed * -1;
				} else {
					currentPlayerState = PlayerState.MOVEDOWN;
					int dy = 50 - (yPosition % 50);
					yDirectionSpeed = dy < speed ? dy : speed;
				}
				yPosition += yDirectionSpeed;
				SetPositionOnScreen();
				return;
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

		int[] xy = gameController.isMoveAble(xPosition + xDirectionSpeed, yPosition + yDirectionSpeed, this);
		xPosition += xy[0];
		yPosition += xy[1];
		SetPositionOnScreen();
	}

	public void useWeapon() {
		if (!canUseWeapon) {
			return;
		}
		int x = xPosition / 50;
		int y = yPosition / 50;
		if (xPosition % 50 != 0) {
			int xMid = (xPosition / 50 + 1) * 50;
			x = Math.abs(xMid - xPosition) >= Math.abs(xMid - (xPosition + 50)) ? x : x + 1;
		} else if (yPosition % 50 != 0) {
			int yMid = (yPosition / 50 + 1) * 50;
			y = Math.abs(yMid - yPosition) >= Math.abs(yMid - (yPosition + 50)) ? y : y + 1;
		}

		if (this.gameController.canSetObject(x, y)) {
			this.gameController.setObjectInGame(x, y,
					new Bomb(x * 50, y * 50, this.getGameController().getGamePage().getGameFieldItemPane(),
							getBombRange(), this, this.getGameController()));
		}
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}
}
