package weapon;

import controller.GameController;
import gameobject.Destroyable;
import gameobject.GameObject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.Player;
import player.PlayerBase;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	private GameController gameController;
	Thread thread;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, Player player, GameController gameController) {
		super(xPosition, yPosition, "bomb", layer);
		if (player != null) {
			System.out.println("Hit");
			player.getCountBomb().add(this);
			player.setCanUseWeapon();
		}
		this.range = range;
		this.gameController = gameController;
		BombArea area = new BombArea(this);

		thread = new Thread(() -> {
			try {
				Thread.sleep(1000);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						onObjectIsDestroyed();
						area.showRange();
						if (player != null) {
							player.getCountBomb().poll();
							player.setCanUseWeapon();
						}
					}
				});
				Thread.sleep(1000);
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
		return false;
	}

	@Override
	public boolean makeDamageToPlayer(PlayerBase target) {
		return false;
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		gameController.removeItem(xPosition / 50, yPosition / 50);
	}

	public GameController getGameController() {
		return gameController;
	}

}
