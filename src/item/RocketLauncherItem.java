package item;

import javafx.scene.layout.Pane;
import player.Player;
import weapon.WeaponType;

public class RocketLauncherItem extends Item implements PowerUp{

	public RocketLauncherItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "rocketLauncher", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setCurrentWeapon(WeaponType.ROCKETLAUNCHER);
	}

}
