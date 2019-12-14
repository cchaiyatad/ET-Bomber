package weapon;

import ai.Minion;
import controller.GameController;
import controller.ObjectInGame;
import gameobject.Destroyable;
import gameobject.GameObject;
import gameobject.Wall;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	private GameController gameController;
	private Thread explodeThread;
	private Thread bombAreaThread;
	private BombArea area;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, PlayerBase player, GameController gameController) {
		super(xPosition, yPosition, "bomb", layer);
		if (player != null) {
			player.getCountBomb().add(this);
			player.setCanUseWeapon();
		}
		this.range = range;
		this.gameController = gameController;

		explodeThread = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1500);
					if (gameController.isPlaying()) {
						break;
					}
				}
			} catch (InterruptedException e) {
				onObjectIsDestroyed();
			}
			area = new BombArea(this);

			Platform.runLater(() -> {
				area.setIsCanShowRange();
				onObjectIsDestroyed();
				makeDamageToObject();
				makeDamageToPlayer();
				area.setAfterDestroy();
				area.showRange();
				if (player != null) {
					player.getCountBomb().poll();
					player.setCanUseWeapon();
				}
			});
		});

		bombAreaThread = new Thread(() -> {
			try {
				explodeThread.join();
			} catch (InterruptedException e1) {
//				System.out.println("interrupt join " + this.xPosition + " " + this.yPosition + " " + this.isExplode);
			}

			try {
				while (true) {
					Thread.sleep(500);
					if (gameController.isPlaying()) {
						break;
					}
				}
				Platform.runLater(() -> {
					area.removeRange();
				});

			} catch (InterruptedException e) {
//				System.out.println("interrupt 2 " + this.xPosition + " " + this.yPosition + " " + this.isExplode);
			}
		});
		explodeThread.start();
		bombAreaThread.start();
	}

	@Override
	public int getDamageRange() {
		return this.range;
	}

	@Override
	public boolean canMakeDamageToobject(GameObject targetobj) {
		return !(targetobj instanceof Wall);
	}

	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
		gameController.removeItem(xPosition / 50, yPosition / 50);
		this.explodeThread.interrupt();
	}

	public GameController getGameController() {
		return gameController;
	}

	@Override
	public void makeDamageToObject() {
		int xPos = getxPosition() / 50;
		int yPos = getyPosition() / 50;
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjectInGame(xPos, Math.max(yPos - i, 0));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowTop()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjectInGame(xPos, Math.min(yPos + i, 14));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowBot()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjectInGame(Math.max(xPos - i, 0), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowLeft()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjectInGame(Math.min(xPos + i, 14), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowRight()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
	}

	public void makeDamageToPlayer() {
		int yMax, yMin, xMax, xMin;
		yMax = 0;
		yMin = 0;
		xMax = 0;
		xMin = 0;
		for (int i = getDamageRange() - 1; i >= 0; i--) {
			if (area.getIsCanShowTop()[i]) {
				yMin = i + 1;
				break;
			}
		}

		for (int i = getDamageRange() - 1; i >= 0; i--) {
			if (area.getIsCanShowBot()[i]) {
				yMax = i + 2;
				break;
			}
		}

		for (int i = getDamageRange() - 1; i >= 0; i--) {
			if (area.getIsCanShowLeft()[i]) {
				xMin = i + 1;
				break;
			}
		}

		for (int i = getDamageRange() - 1; i >= 0; i--) {
			if (area.getIsCanShowRight()[i]) {
				xMax = i + 2;
				break;
			}
		}

		for (PlayerBase player : this.gameController.getPlayers()) {
			int x = player.getxPosition();
			int y = player.getyPosition();
			boolean TLinbomb = isinRange(x, y, xMin, xMax, yMin, yMax);
			boolean TRinbomb = isinRange(x + 50, y, xMin, xMax, yMin, yMax);
			boolean BRinbomb = isinRange(x + 50, y + 50, xMin, xMax, yMin, yMax);
			boolean BLinbomb = isinRange(x, y + 50, xMin, xMax, yMin, yMax);

			if (TLinbomb || TRinbomb || BLinbomb || BRinbomb) {
				player.setHp(player.getHp() - 1);
//			} else if (!TLinbomb && !BLinbomb && TRinbomb && BRinbomb) {
//				player.setHp(player.getHp() - 1);
//			} else if (TLinbomb && BLinbomb && !TRinbomb && !BRinbomb) {
//				player.setHp(player.getHp() - 1);
//			} else if (TLinbomb && TRinbomb && !BLinbomb && !BRinbomb) {
//				player.setHp(player.getHp() - 1);
//			} else if (!TLinbomb && !TRinbomb && BLinbomb && !BRinbomb) {
//				player.setHp(player.getHp() - 1);
//			}
			}
		}
		for (Minion minion : this.gameController.getMinions()) {
			int x = minion.getxPosition();
			int y = minion.getyPosition();
			boolean TLinbomb = isinRange(x, y, xMin, xMax, yMin, yMax);
			boolean TRinbomb = isinRange(x + 50, y, xMin, xMax, yMin, yMax);
			boolean BRinbomb = isinRange(x + 50, y + 50, xMin, xMax, yMin, yMax);
			boolean BLinbomb = isinRange(x, y + 50, xMin, xMax, yMin, yMax);

			if (TLinbomb || TRinbomb || BLinbomb || BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			}
		}
	}

	public boolean isinRange(int x, int y, int xMin, int xMax, int yMin, int yMax) {
		if (((xPosition / 50 - xMin) * 50 <= x) && (x <= (xPosition / 50 + xMax) * 50) && ((yPosition / 50) * 50 <= y)
				&& (y <= (yPosition / 50 + 1) * 50)) {
			return true;
		}
		// check y axis
		else if (((xPosition / 50) * 50 <= x) && (x <= (xPosition / 50 + 1) * 50) && ((yPosition / 50 - yMin) * 50 <= y)
				&& (y <= (yPosition / 50 + yMax) * 50)) {
			return true;
		}

		return false;

	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.BOMB;
	}

}
