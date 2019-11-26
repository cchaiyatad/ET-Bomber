package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import weapon.WeaponType;

public class GrenadeItem extends Item implements PowerUp{

	public GrenadeItem(int xPosition, int yPosition, Pane layer,GameController gameController) {
		super(xPosition, yPosition, "grenade", layer,gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setCurrentWeapon(WeaponType.GRENADE);
	}
	
	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.GRENADEITEM;
	}
}
