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
	public boolean canMakeDamageToobject(GameObject targetobj) {
		if (targetobj instanceof Wall) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getDamageRange() {
		return this.range;
	}

	public GameController getGameController() {
		return gameController;
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.BOMB;
	}

	public boolean isinRange(int x, int y, int xMin, int xMax, int yMin, int yMax) {
		if (((xPosition / 50 - xMin) * 50 <= x) && (x <= (xPosition / 50 + xMax) * 50) && (yPosition <= y)
				&& (y <= (yPosition / 50 + 1) * 50)) {
			return true;
		}
		// check y axis
		else if ((xPosition <= x) && (x <= (xPosition / 50 + 1) * 50) && ((yPosition / 50 - yMin) * 50 <= y)
				&& (y <= (yPosition / 50 + yMax) * 50)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void makeDamageToObject() {
		int xPos = getxPosition() / 50;
		int yPos = getyPosition() / 50;
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getGameObject(xPos, Math.max(yPos - i, 0));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowTop()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getGameObject(xPos, Math.min(yPos + i, 14));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowBot()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;

			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getGameObject(Math.max(xPos - i, 0), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowLeft()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getGameObject(Math.min(xPos + i, 14), yPos);
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
			int x1 = player.getxPosition();
			int x2 = x1 + 50;
			int x3 = x2;
			int x4 = x1;
			int y1 = player.getyPosition();
			int y2 = y1;
			int y3 = y1 + 50;
			int y4 = y3;
			if (y3 == yPosition || y3 == (yPosition / 50 - yMin) * 50) {
				y3--;
				y4--;
			}
			if (y1 == yPosition + 50 || y1 == (yPosition / 50 + yMax) * 50) {
				y1++;
				y2++;
			}
			if (x2 == xPosition || x2 == (xPosition / 50 - xMin) * 50) {
				x2--;
				x3--;
			}
			if (x1 == xPosition + 50 || x1 == (xPosition / 50 + xMax) * 50) {
				x1++;
				x4++;
			}
			boolean TLinbomb = isinRange(x1, y1, xMin, xMax, yMin, yMax);
			boolean TRinbomb = isinRange(x2, y2, xMin, xMax, yMin, yMax);
			boolean BRinbomb = isinRange(x3, y3, xMin, xMax, yMin, yMax);
			boolean BLinbomb = isinRange(x4, y4, xMin, xMax, yMin, yMax);
			System.out.println(TLinbomb + " " + TRinbomb + " " + BLinbomb + " " + BRinbomb);
			System.out.print(yMin + " ");
			System.out.print(yMax + " ");
			System.out.print(xMin + " ");
			System.out.print(xMax);

			if (TLinbomb && BLinbomb && TRinbomb && BRinbomb) {
				player.setHp(player.getHp() - 1);
			} else if (!TLinbomb && !BLinbomb && TRinbomb && BRinbomb) {
				player.setHp(player.getHp() - 1);
			} else if (TLinbomb && BLinbomb && !TRinbomb && !BRinbomb) {
				player.setHp(player.getHp() - 1);
			} else if (!TLinbomb && BLinbomb && !TRinbomb && BRinbomb) {
				player.setHp(player.getHp() - 1);
			} else if (TLinbomb && !BLinbomb && TRinbomb && !BRinbomb) {
				player.setHp(player.getHp() - 1);
			}
		}
		for (Minion minion : this.gameController.getMinions()) {
			int x1 = minion.getxPosition();
			int x2 = x1 + 50;
			int x3 = x2;
			int x4 = x1;
			int y1 = minion.getyPosition();
			int y2 = y1;
			int y3 = y1 + 50;
			int y4 = y3;
			if (y3 == yPosition || y3 == (yPosition / 50 - yMin) * 50) {
				y3--;
				y4--;
			}
			if (y1 == yPosition + 50 || y1 == (yPosition / 50 + yMax) * 50) {
				y1++;
				y2++;
			}
			if (x2 == xPosition || x2 == (xPosition / 50 - xMin) * 50) {
				x2--;
				x3--;
			}
			if (x1 == xPosition + 50 || x1 == (xPosition / 50 + xMax) * 50) {
				x1++;
				x4++;
			}

			boolean TLinbomb = isinRange(x1, y1, xMin, xMax, yMin, yMax);
			boolean TRinbomb = isinRange(x2, y2, xMin, xMax, yMin, yMax);
			boolean BRinbomb = isinRange(x3, y3, xMin, xMax, yMin, yMax);
			boolean BLinbomb = isinRange(x4, y4, xMin, xMax, yMin, yMax);

			// check minion's position is in Range of bomb
			if (TLinbomb && BLinbomb && TRinbomb && BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			} else if (!TLinbomb && !BLinbomb && TRinbomb && BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			} else if (TLinbomb && BLinbomb && !TRinbomb && !BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			} else if (!TLinbomb && BLinbomb && !TRinbomb && BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			} else if (TLinbomb && !BLinbomb && TRinbomb && !BRinbomb) {
				minion.setHp(minion.getHp() - 1);
			}
		}
	}
	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
		gameController.removeItem(xPosition / 50, yPosition / 50);
		this.explodeThread.interrupt();
	}

}
