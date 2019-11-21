package item;

import javafx.scene.layout.Pane;
import player.Player;

public class SpeedUpgradeItem extends Item implements PowerUp{

	public SpeedUpgradeItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "speedItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setSpeed(player.getSpeed() + 1);
	}

}
