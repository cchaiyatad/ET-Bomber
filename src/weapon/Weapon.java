package weapon;

import controller.ObjectInGame;
import gameobject.GameObject;

public interface Weapon {
	int getDamageRange();

	boolean canMakeDamageToobject(GameObject targetobj);

	void makeDamageToObject();

	void makeDamageToPlayer();

	public abstract ObjectInGame getObjectInGame();
}
