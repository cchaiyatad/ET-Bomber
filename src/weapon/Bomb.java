package weapon;

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
		if (targetobj instanceof Wall) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
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
			GameObject object = getGameController().getObjecyInGame(xPos, Math.max(yPos - i, 0));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowTop()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(xPos, Math.min(yPos + i, 14));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowBot()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;

			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(Math.max(xPos - i, 0), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowLeft()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(Math.min(xPos + i, 14), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowRight()[i - 1] && canMakeDamageToobject(object)) {
				((Destroyable) object).onObjectIsDestroyed();
				break;
			}
		}
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.BOMB;
	}
}
