package weapon;

import gameobject.GameObject;
import item.Weapon;
import javafx.scene.layout.Pane;
import player.Player;

public class Bomb extends GameObject implements Weapon {
	private int range;
	public Bomb(int xPosition, int yPosition, String imagePath, Pane layer,int range) {
		super(xPosition, yPosition, imagePath, layer);
		// TODO Auto-generated constructor stub
		this.range = range;
	}

	@Override
	public int getDamageRange(Player player) {
		// TODO Auto-generated method stub
		return player.getBombRange();
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
