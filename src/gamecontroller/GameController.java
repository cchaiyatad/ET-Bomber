package gamecontroller;

import java.util.ArrayList;
import java.util.List;

import gameobject.Wall;
import gui.GameScene;
import javafx.scene.Scene;
import player.Player;
import setting.Setting;

public class GameController {
	private List<Wall> backgrounds;
	private List<Wall> walls;
//	private Obstacle[] obstacles;
	private List<Player> players;

	private GameScene gameScene;

	public Scene CreateGameScene() {
		gameScene = new GameScene();
		CreateBackground();
		CreateInitWall();
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

	private void CreateBackground() {
		backgrounds = new ArrayList<Wall>();
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 13; j++) {
				backgrounds.add(new Wall(50 * j, 50 * i, "background", gameScene.GetGameFieldPane()));
			}
		}
	}

	private void CreateInitWall() {
		walls = new ArrayList<Wall>();

		// 15*15
		for (int i = 0; i <= 14; i++) {
			if (i == 0 || i == 14) {
				for (int j = 1; j <= 13; j++) {
					walls.add(new Wall(50 * j, 50 * i, gameScene.GetGameFieldPane()));
				}
			}
			walls.add(new Wall(0, 50 * i, gameScene.GetGameFieldPane()));
			walls.add(new Wall(50 * 14, 50 * i, gameScene.GetGameFieldPane()));
		}

	}

}
