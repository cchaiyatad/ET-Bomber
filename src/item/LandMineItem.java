package item;

import javafx.scene.layout.Pane;
import player.Player;

public class LandMineItem extends Item implements PowerUp{

	public LandMineItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "landMine", layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onObjectIsDestroyed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerGetItem(Player player) {
		// TODO Auto-generated method stub
		
	}

}