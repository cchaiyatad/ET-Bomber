package weapon;

import gameobject.GameObject;
import item.Weapon;
import javafx.scene.layout.Pane;
import player.Player;

public class LandMine extends GameObject implements Weapon {

<<<<<<< HEAD
	public LandMine(int xPosition, int yPosition, String imagePath,Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
||||||| fa6c769
	public LandMine() {
		// TODO Auto-generated constructor stub
	}

	public LandMine(int xPosition, int yPosition, String imagePath) {
		super(xPosition, yPosition, imagePath);
=======
	public LandMine(int xPosition, int yPosition, String imagePath, Pane layer) {
		super(xPosition, yPosition, imagePath, layer);
>>>>>>> 677e5561742b3f238696b80f06f0f9a255130a7c
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDamageRange(Player player) {
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
