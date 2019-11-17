package item;

import player.Player;
import gameobject.GameObject;

public interface Weapon {
	void GetDamageRange(Player player);
	boolean CanMakeDamageToobject(GameObject targetobj);
	boolean MakeDamageToPlayer(Player target);
}
