package weapon;

import gameobject.GameObject;
import player.PlayerBase;

public interface Weapon {
	int getDamageRange();
	boolean canMakeDamageToobject(GameObject targetobj);
	void makeDamageToObject();
}
