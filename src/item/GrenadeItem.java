package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import weapon.WeaponType;

public class GrenadeItem extends Item implements PowerUp{

	public GrenadeItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "grenade", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
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
