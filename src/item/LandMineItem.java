package item;

import javafx.scene.layout.Pane;
import player.Player;
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
	public void onPlayerGetItem(Player player) {
		player.setCurrentWeapon(WeaponType.LANDMINE);
	}

}
