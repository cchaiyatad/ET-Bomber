package item;

import javafx.scene.layout.Pane;
import player.Player;
import weapon.WeaponType;

public class PoisonDartItem extends Item implements PowerUp{

	public PoisonDartItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "poisonDart", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setCurrentWeapon(WeaponType.POISONDART);
	}

}
