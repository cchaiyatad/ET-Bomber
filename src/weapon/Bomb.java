package weapon;

import gameobject.GameObject;
import item.Weapon;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon {

	public Bomb(int xPosition, int yPosition, String imagePath,Pane layer) {
		super(xPosition, yPosition, imagePath,layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void GetDamageRange(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean CanMakeDamageToobject(GameObject targetobj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean MakeDamageToPlayer(Player target) {
		// TODO Auto-generated method stub
		return false;
	}

}
