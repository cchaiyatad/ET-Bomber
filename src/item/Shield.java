package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Shield extends Item implements PowerUp {

	public Shield(int xPosition, int yPosition, Pane layer, GameController gameController) {
		super(xPosition, yPosition, "shield", layer,gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		setImageShow(false);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setShield();
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.SHIELDITEM;
	}
}
