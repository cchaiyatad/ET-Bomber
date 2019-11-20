package controller;

import java.util.ArrayList;
import java.util.List;

import gameobject.Obstacle;
import gameobject.Wall;
import gui.GamePage;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.Player;
import player.PlayerState;
import setting.Setting;

public class GameController extends Controller {
	private boolean[][] objectsArray = new boolean[15][15];
	private List<Wall> backgrounds;
	private List<Wall> walls;
	private List<Obstacle> obstacles;
	private List<Player> players;

	private GamePage gamePage;
	private InputInGame inputInGame;
	private AnimationTimer gameLoop;
	private boolean isPlaying;

	@Override
	protected Scene createScene() {
		gamePage = new GamePage(this);
		createBackground();
		createInitWall();
		createInitObstacle();
		createPlayer(1);
		this.scene = new Scene(gamePage, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		inputInGame = new InputInGame(scene);
		inputInGame.addListeners();
		isPlaying = false;
		return scene;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Wall> getWalls() {
		return walls;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public AnimationTimer gameLoop() {
		if (this.gameLoop == null) {
			this.gameLoop = new AnimationTimer() {
				@Override
				public void handle(long now) {
					for (Player player : players) {
						checkPlayerMoveAndSetState(player);
					}
					players.forEach(Moveable -> Moveable.move());
				}
			};
		}
		return gameLoop;
	}

	private void checkPlayerMoveAndSetState(Player player) {
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
		boolean up = inputInGame.isKeyPress(buttonUp);
		boolean right = inputInGame.isKeyPress(buttonRight);
		boolean down = inputInGame.isKeyPress(buttonDown);
		boolean left = inputInGame.isKeyPress(buttonLeft);

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
		int x2 = (x + 50 - player.getSpeed()) / 50;
		int y2 = (y + 50 - player.getSpeed()) / 50;
		x /= 50;
		y /= 50;
		return !objectsArray[x][y] && !objectsArray[x][y2] && !objectsArray[x2][y] && !objectsArray[x2][y2];
	}

	private void createBackground() {
		backgrounds = new ArrayList<Wall>();
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 13; j++) {
				backgrounds.add(new Wall(50 * j, 50 * i, "background", gamePage.getGameFieldPane()));
			}
		}
	}

	private void createInitWall() {
		walls = new ArrayList<Wall>();

		for (int i = 0; i <= 14; i++) {
			if (i == 0 || i == 14) {
				for (int j = 1; j <= 13; j++) {
					walls.add(new Wall(50 * j, 50 * i, gamePage.getGameFieldPane()));
					objectsArray[j][i] = true;
				}
			} else if (i % 2 == 0) {
				for (int j = 2; j <= 12; j += 2) {
					walls.add(new Wall(50 * j, 50 * i, gamePage.getGameFieldPane()));
					objectsArray[j][i] = true;
				}
			}
			walls.add(new Wall(0, 50 * i, gamePage.getGameFieldPane()));
			walls.add(new Wall(50 * 14, 50 * i, gamePage.getGameFieldPane()));
			objectsArray[0][i] = true;
			objectsArray[14][i] = true;
		}

	}

	private void createInitObstacle() {
		obstacles = new ArrayList<Obstacle>();

		obstacles.add(new Obstacle(350, 300, "obstacle", gamePage.getGameFieldPane()));
		objectsArray[7][6] = true;
	}

	private void createPlayer(int numberOfPlayer) {
		players = new ArrayList<Player>();
		for (int i = 0; i < numberOfPlayer; i++) {
			Player player = new Player(50, 50, "", gamePage.getGameFieldPane(), 1, this);
			players.add(player);
			gamePage.getScoreBoard().getPlayerStatusBoardViaIndex(i).linkToPlayer(player);
		}
	}

}
