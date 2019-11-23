package item;

import javafx.scene.layout.Pane;
import player.Player;

public class PowerUpgradeItem extends Item implements PowerUp {

	public PowerUpgradeItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "powerItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setBombRange(player.getBombRange() + 1);
	}

}
