package item;

import controller.GameController;
import controller.ObjectInGame;
import javafx.scene.layout.Pane;
import player.PlayerBase;
import weapon.WeaponType;

public class LandMineItem extends Item implements PowerUp{

	public LandMineItem(int xPosition, int yPosition, Pane layer,GameController gameController) {
		super(xPosition, yPosition, "landMine", layer,gameController);
	}

	@Override
	public void onObjectIsDestroyed() {
		this.layer.getChildren().remove(this.imageView);
		this.getGameController().removeItem(getxPosition()/50, getyPosition()/50);
	}

	@Override
	public void onPlayerGetItem(PlayerBase player) {
		player.setCurrentWeapon(WeaponType.LANDMINE);
	}

	@Override
	public ObjectInGame getObjectInGame() {
		return ObjectInGame.LANDMINEITEM;
	}
	
}
