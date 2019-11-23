package item;

import javafx.scene.layout.Pane;
import player.Player;

public class Shield extends Item implements PowerUp {

	public Shield(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "shield", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setShield();
	}

}
