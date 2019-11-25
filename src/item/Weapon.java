package item;

import player.Player;
import gameobject.GameObject;

public interface Weapon {
	int getDamageRange();
	boolean canMakeDamageToobject(GameObject targetobj);
	boolean makeDamageToPlayer(Player target);
}
