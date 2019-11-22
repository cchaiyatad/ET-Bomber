package weapon;

import gameobject.Destroyable;
import gameobject.GameObject;
import item.Weapon;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon, Destroyable {
	private int range;
	Thread thread;
	public Bomb(int xPosition, int yPosition, Pane layer,int range,Player player) {
		super(xPosition, yPosition, "bomb", layer);
		this.range = range;
		player.getCountBomb().add(this);
		player.setCanUseWeapon();
		// TODO Auto-generated constructor stub
		thread = new Thread(() -> {
				try {
					Thread.sleep(3000);
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							onObjectIsDestroyed();
							player.getCountBomb().poll();
							player.setCanUseWeapon();
						}
					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		thread.start();
	}

	@Override
	public int getDamageRange(Player player) {
		// TODO Auto-generated method stub
		return player.getBombRange();
	}

	@Override
	public boolean canMakeDamageToobject(GameObject targetobj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean makeDamageToPlayer(Player target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onObjectIsDestroyed() {
		// TODO Auto-generated method stub
		this.layer.getChildren().remove(this.imageView);
		
	}
	

}
