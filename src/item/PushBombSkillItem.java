package item;

import javafx.scene.layout.Pane;
import player.Player;

public class PushBombSkillItem extends Item implements PowerUp{

	public PushBombSkillItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "pushBombItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(Player player) {
		player.setCanPushBomb(true);
	}

}
