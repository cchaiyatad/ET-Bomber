package weapon;

import gameobject.Destroyable;
import gameobject.GameObject;
import item.Weapon;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	private Player player;
	Thread thread;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, Player player) {
		super(xPosition, yPosition, "bomb", layer);
		this.player = player;
		this.range = range;
		player.getCountBomb().add(this);
		player.setCanUseWeapon();

		thread = new Thread(() -> {
			try {
				Thread.sleep(3000);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						onObjectIsDestroyed();
						player.getCountBomb().poll();
						player.setCanUseWeapon();
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
		player.getGameController().removeItem(xPosition / 50, yPosition / 50);
	}

}
