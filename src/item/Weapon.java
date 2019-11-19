package item;

import player.Player;
import gameobject.GameObject;

public interface Weapon {
	int getDamageRange(Player player);
	boolean canMakeDamageToobject(GameObject targetobj);
	boolean makeDamageToPlayer(Player target);
}
