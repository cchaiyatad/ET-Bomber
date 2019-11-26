package weapon;

import controller.ObjectInGame;
import gameobject.GameObject;
import javafx.scene.layout.Pane;
import player.PlayerBase;

public class LandMine extends GameObject implements Weapon {

	public LandMine(int xPosition, int yPosition, String imagePath,Pane layer) {
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
	public void makeDamageToObject() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ObjectInGame getObjectInGame() {
		//debug
		return ObjectInGame.MINE;
	}



	

}
