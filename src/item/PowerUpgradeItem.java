package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class PowerUpgradeItem extends Item implements PowerUp {

	public PowerUpgradeItem(int xPosition, int yPosition, Pane layer, GameController gameController) {
		super(xPosition, yPosition, "powerItem", layer, gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
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
