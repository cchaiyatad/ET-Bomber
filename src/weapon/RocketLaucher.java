package weapon;

import gameobject.GameObject;
import item.Weapon;
import player.Player;

public class RocketLaucher extends GameObject implements Weapon {

	public RocketLaucher() {
		// TODO Auto-generated constructor stub
	}

	public RocketLaucher(int xPosition, int yPosition, String imagePath) {
		super(xPosition, yPosition, imagePath);
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
