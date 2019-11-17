package gamecontroller;

import java.util.ArrayList;
import java.util.List;

import gameobject.Wall;
import gui.GameScene;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.Player;
import player.PlayerState;
import setting.Setting;

public class GameController {
	private List<Wall> backgrounds;
	private List<Wall> walls;
//	private Obstacle[] obstacles;
	private List<Player> players;

	private GameScene gameScene;
	private InputInGame inputInGame;

	public Scene CreateGameScene() {
		gameScene = new GameScene();
		CreateBackground();
		CreateInitWall();
		CreatePlayer(1);
		Scene scene = new Scene(gameScene, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		inputInGame = new InputInGame(scene);
		inputInGame.AddListeners();
		return scene;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void CheckPlayerMoveAndSetState(Player player) {
		KeyCode buttonUp = null;
		KeyCode buttonRight = null;
		KeyCode buttonDown = null;
		KeyCode buttonLeft = null;
		switch (player.getPlayerNumber()) {
		case 1:
			buttonUp = Setting.PLAYERONE_MOVEUP;
			buttonRight = Setting.PLAYERONE_MOVERIGHT;
			buttonDown = Setting.PLAYERONE_MOVEDOWN;
			buttonLeft = Setting.PLAYERONE_MOVELEFT;
			break;
		}
		boolean up = inputInGame.IsKeyPress(buttonUp);
		boolean right = inputInGame.IsKeyPress(buttonRight);
		boolean down = inputInGame.IsKeyPress(buttonDown);
		boolean left = inputInGame.IsKeyPress(buttonLeft);
		
		if(up && !right && !down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVEUP);
		}
		else if(!up && right && !down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVERIGHT);
		}
		else if(!up && !right && down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVEDOWN);
		}
		else if(up && !right && !down && left) {
			player.setCurrentPlayerState(PlayerState.MOVELEFT);
		}
		else {
			player.setCurrentPlayerState(PlayerState.IDLE);
		}
		
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

	private void CreatePlayer(int numberOfPlayer) {
		players = new ArrayList<Player>();
		for (int i = 0; i < numberOfPlayer; i++) {
			Player player = new Player(300, 300, "", gameScene.GetGameFieldPane(), 1);
			players.add(player);
		}
	}

}
