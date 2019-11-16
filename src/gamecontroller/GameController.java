package gamecontroller;

import java.util.ArrayList;
import java.util.List;

import gameobject.Wall;
import gui.GameScene;
import javafx.scene.Scene;
import player.Player;
import setting.Setting;

public class GameController {
	private List<Wall> walls;
//	private Obstacle[] obstacles;
	private List<Player> players;
	
	private GameScene gameScene;

	public Scene CreateGameScene() {
		gameScene = new GameScene();
		CreateInitWall();
		System.out.println(walls.get(0).getxPosition());
		Scene scene = new Scene(gameScene, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		InputInGame inputInGame = new InputInGame(scene);
		inputInGame.AddListeners();
		return scene;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Wall> getWalls() {
		return walls;
	}
	
	private void CreateInitWall() {
		walls = new ArrayList<Wall>();
		walls.add(new Wall(300,300,""));
		gameScene.AddItemOnScene(walls.get(0));
		
//		//15*15
//		for(int i = 1; i <= 15;i++) {
//			if(i == 1 || i == 15) {
//				for(int j = 2; j <= 14; j++) {
//					
//				}
//			}
//			walls.add()
//		}
	}
	
	
}
