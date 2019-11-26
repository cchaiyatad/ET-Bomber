package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class Shield extends Item implements PowerUp {

	public Shield(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "shield", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
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
