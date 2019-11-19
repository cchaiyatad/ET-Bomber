package item;

import player.Player;
import gameobject.GameObject;

public interface Weapon {
	int GetDamageRange(Player player);
	boolean CanMakeDamageToobject(GameObject targetobj);
	boolean MakeDamageToPlayer(Player target);
}
