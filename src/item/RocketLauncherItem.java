package item;

import javafx.scene.layout.Pane;
import player.Player;

public class RocketLauncherItem extends Item implements PowerUp{

	public RocketLauncherItem(int xPosition, int yPosition, Pane layer) {
		super(xPosition, yPosition, "rocketLauncher", layer);
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
