package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import weapon.WeaponType;

public class LandMineItem extends Item implements PowerUp{

	public LandMineItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "landMine", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setCurrentWeapon(WeaponType.LANDMINE);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.LANDMINEITEM;
	}
	
}
