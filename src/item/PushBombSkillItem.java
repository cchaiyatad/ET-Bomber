package item;

import javafx.scene.layout.Pane;
import player.Player;

public class PushBombSkillItem extends Item implements PowerUp{

	public PushBombSkillItem(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
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
