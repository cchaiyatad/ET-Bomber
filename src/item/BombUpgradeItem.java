package item;

import javafx.scene.layout.Pane;
import player.Player;

public class BombUpgradeItem extends Item implements PowerUp{

	public BombUpgradeItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "bombItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setBombCount(player.getBombCount() + 1);
		player.setCanUseWeapon();
	}

}
