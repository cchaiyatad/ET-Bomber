package weapon;

import gameobject.GameObject;
import item.Weapon;
import javafx.scene.layout.Pane;
import player.Player;

public class Grenade extends GameObject implements Weapon {

	public Grenade(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDamageRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canMakeDamageToobject(GameObject targetobj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean makeDamageToPlayer(Player target) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
