package gamecontroller;

import java.util.ArrayList;
import java.util.List;

import gameobject.Moveable;
import gameobject.Wall;
import gui.GameScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.Player;
import player.PlayerState;
import setting.Setting;

public class GameController {
	private boolean[][] objectsArray = new boolean[15][15]; 
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

	public AnimationTimer GameLoop() {
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (Player player : players) {
					CheckPlayerMoveAndSetState(player);
				}
				players.forEach(Moveable -> Moveable.Move());
			}
		};
		return gameLoop;

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

		if (up && !right && !down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVEUP);
		} else if (!up && right && !down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVERIGHT);
		} else if (!up && !right && down && !left) {
			player.setCurrentPlayerState(PlayerState.MOVEDOWN);
		} else if (!up && !right && !down && left) {
			player.setCurrentPlayerState(PlayerState.MOVELEFT);
		} else {
			player.setCurrentPlayerState(PlayerState.IDLE);
		}
	}

	public boolean isMoveAble(int x, int y, Player player) {
		int x2 = (x + 50 - player.getSpeed())/50;
		int y2 = (y + 50 - player.getSpeed())/50;
		x /= 50;
		y /= 50;
		return !objectsArray[x][y] && !objectsArray[x2][y2];
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
					objectsArray[j][i] = true;
				}
			}
			walls.add(new Wall(0, 50 * i, gameScene.GetGameFieldPane()));
			walls.add(new Wall(50 * 14, 50 * i, gameScene.GetGameFieldPane()));
			objectsArray[0][i] = true;
			objectsArray[14][i] = true;
		}
//		for(int i = 0; i <= 14;i++) {
//			for(int j = 0; j <= 14; j++) {
//				System.out.print(objectsArray[i][j] + " ");
//			}
//			System.out.println("");
//		}
	}

	private void CreatePlayer(int numberOfPlayer) {
		players = new ArrayList<Player>();
		for (int i = 0; i < numberOfPlayer; i++) {
			Player player = new Player(300, 300, "", gameScene.GetGameFieldPane(), 1, this);
			players.add(player);
		}
	}

}
