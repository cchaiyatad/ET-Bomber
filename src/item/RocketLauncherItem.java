package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import weapon.WeaponType;

public class RocketLauncherItem extends Item implements PowerUp{

	public RocketLauncherItem(int xPosition, int yPosition, Pane layer,GameController gameController) {
		super(xPosition, yPosition, "rocketLauncher", layer,gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
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
