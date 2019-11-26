package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
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
	public void onPlayerGetItem(PlayerBase player) {
		player.setCurrentWeapon(WeaponType.ROCKETLAUNCHER);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.ROCKETLAUNCHERITEM;
	}
}
