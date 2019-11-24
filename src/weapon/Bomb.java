package weapon;

import controller.GameController;
import gameobject.Destroyable;
import gameobject.GameObject;
import item.Weapon;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	private GameController gameController;
	Thread thread;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, Player player, GameController gameController) {
		super(xPosition, yPosition, "bomb", layer);
		this.range = range;
		this.gameController = gameController;
		if (player != null) {
			player.getCountBomb().add(this);
			player.setCanUseWeapon();
		}

		thread = new Thread(() -> {
			try {
				Thread.sleep(3000);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						onObjectIsDestroyed();
						if (player != null) {
							player.getCountBomb().poll();
							player.setCanUseWeapon();
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	@Override
	public int getDamageRange(Player player) {
		return player.getBombRange();
	}

	@Override
	public boolean canMakeDamageToobject(GameObject targetobj) {
		return false;
	}

	@Override
	public boolean makeDamageToPlayer(Player target) {
		return false;
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		gameController.removeItem(xPosition / 50, yPosition / 50);

	}

}
