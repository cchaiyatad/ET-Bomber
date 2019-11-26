package weapon;

import controller.GameController;
import gameobject.Destroyable;
import gameobject.GameObject;
import gameobject.Wall;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.Player;
import player.PlayerBase;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	private GameController gameController;
	Thread thread;
	BombArea area;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, Player player, GameController gameController) {
		super(xPosition, yPosition, "bomb", layer);
		if (player != null) {
			System.out.println("Hit");
			player.getCountBomb().add(this);
			player.setCanUseWeapon();
		}
		this.range = range;
		this.gameController = gameController;

		thread = new Thread(() -> {
			try {
				Thread.sleep(3000);
				area = new BombArea(this);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						area.setIsCanShowRange();
						onObjectIsDestroyed();
						makeDamageToObject();
						area.showRange();
						if (player != null) {
							player.getCountBomb().poll();
							player.setCanUseWeapon();
						}
					}
				});
				Thread.sleep(1500);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						area.removeRange();
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
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
			if (object instanceof PlayerBase) {
				((PlayerBase) object).setHp(((PlayerBase) object).getHp()-1);
			}
			if (!area.getIsCanShowTop()[i - 1] && canMakeDamageToobject(object)) {
				this.layer.getChildren().remove(object.getImageView());
				getGameController().removeItem(xPos, Math.max(yPos - i, 0));
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(xPos, Math.min(yPos + i, 14));
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowBot()[i - 1] && canMakeDamageToobject(object)) {
				this.layer.getChildren().remove(object.getImageView());
				getGameController().removeItem(xPos, Math.min(yPos + i, 14));
				break;

			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(Math.max(xPos - i, 0), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowLeft()[i - 1] && canMakeDamageToobject(object)) {
				this.layer.getChildren().remove(object.getImageView());
				getGameController().removeItem(Math.max(xPos - i, 0), yPos);
				break;
			}
		}
		for (int i = 1; i < getDamageRange() + 1; i++) {
			GameObject object = getGameController().getObjecyInGame(Math.min(xPos + i, 14), yPos);
			if (object instanceof Wall)
				break;
			if (!area.getIsCanShowRight()[i - 1] && canMakeDamageToobject(object)) {
				this.layer.getChildren().remove(object.getImageView());
				getGameController().removeItem(Math.min(xPos + i, 14), yPos);
				break;
			}
		}

	}

}
