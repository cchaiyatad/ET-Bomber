package weapon;

import gameobject.GameObject;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class PoisonDart extends GameObject implements Weapon {

	public PoisonDart(int xPosition, int yPosition, String imagePath, Pane layer) {
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
	public boolean makeDamageToPlayer(PlayerBase target) {
		// TODO Auto-generated method stub
		return false;
	}
}
