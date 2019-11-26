package item;

import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class PushBombSkillItem extends Item implements PowerUp{

	public PushBombSkillItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "pushBombItem", layer);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setCanPushBomb(true);
	}
	
	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.PUSHBOMBSKILLITEM;
	}

}
