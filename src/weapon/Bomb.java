package weapon;


import gameobject.Destroyable;
import gameobject.GameObject;
import item.Weapon;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private Player player;
	Thread thread;

	public Bomb(int xPosition, int yPosition, Pane layer, int range, Player player) {
		super(xPosition, yPosition, "bomb", layer);
		this.player = player;
		player.getCountBomb().add(this);
		player.setCanUseWeapon();
		BombArea area = new BombArea(this);

		thread = new Thread(() -> {
			try {
				Thread.sleep(1000);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						onObjectIsDestroyed();
						area.showRange();
						player.getCountBomb().poll();
						player.setCanUseWeapon();
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

	public Player getPlayer() {
		return player;
	}
	

}
