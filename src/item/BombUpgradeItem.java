package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class BombUpgradeItem extends Item implements PowerUp{

	public BombUpgradeItem(int xPosition, int yPosition, Pane layer,GameController gameController) {
		super(xPosition, yPosition, "bombItem", layer, gameController);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.BOMBUPGRADEITEM;
	}

	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setBombCount(player.getBombCount() + 1);
		player.setCanUseWeapon();
	}

}
