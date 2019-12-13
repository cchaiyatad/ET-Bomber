package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class SpeedUpgradeItem extends Item implements PowerUp{

	public SpeedUpgradeItem(int xPosition, int yPosition, Pane layer, GameController gameController) {
		super(xPosition, yPosition, "speedItem", layer, gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setSpeed(player.getSpeed() + 1);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.SPEEDUPGRADEITEM;
	}
}
