package gamecontroller;

import gui.GameScene;
import javafx.scene.Scene;
import player.Player;
import setting.Setting;

public class GameController {
//	 - Wall[] walls
//	 - Obstacle[] obstacles
	private Player[] players;

	public Scene CreateGameScene() {
		GameScene gameScene = new GameScene();
		Scene scene = new Scene(gameScene, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		InputInGame inputInGame = new InputInGame(scene);
		inputInGame.AddListeners();
		return scene;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	
	
	
	
}
