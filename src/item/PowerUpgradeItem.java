package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class PowerUpgradeItem extends Item implements PowerUp {

	public PowerUpgradeItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "powerItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setBombRange(player.getBombRange() + 1);
	}
	
	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.POWERUPGRADEITEM;
	}

	
}
