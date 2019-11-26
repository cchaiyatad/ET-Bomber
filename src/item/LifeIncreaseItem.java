package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class LifeIncreaseItem extends Item implements PowerUp{

	public LifeIncreaseItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "lifeItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setHp(player.getHp() + 1);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.LIFEINCREASEITEM;
	}
}
