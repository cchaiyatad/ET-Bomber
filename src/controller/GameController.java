package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import gameobject.Obstacle;
import gameobject.Wall;
import gui.GamePage;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.Player;
import player.PlayerState;
import setting.Setting;
import weapon.Bomb;

public class GameController extends Controller {
	private ObjectInGame[][] objectsArray = new ObjectInGame[15][15];
	private List<Player> players;

	private GamePage gamePage;
	private InputInGame inputInGame;
	private AnimationTimer gameLoop;
	private boolean isPlaying;
	private long remainingTime = Setting.GAME_TIME;
	private long startTime;

	@Override
	protected Scene createScene() {
		LevelGenerator levelGenerator = new LevelGenerator();
		objectsArray = levelGenerator.generateLevel();
		gamePage = new GamePage(this);
		
		createBackground();
		createGame();

		createPlayer(1);
		this.scene = new Scene(gamePage, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);

		inputInGame = new InputInGame(scene);
		inputInGame.addListeners();

		isPlaying = false;
		return scene;
	}

	@Override
	public Scene getScene() {
		if (this.scene != null) {
			inputInGame.addListeners();
		}
		return super.getScene();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public void setStartTime(long startTime) {
		this.startTime = TimeUnit.SECONDS.convert(startTime, TimeUnit.NANOSECONDS);
	}

	public AnimationTimer gameLoop() {
		if (this.gameLoop == null) {
			startTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			this.gameLoop = new AnimationTimer() {
				int count = 0;

				@Override
				public void handle(long now) {

					if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime == 1) {
						remainingTime -= TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
						startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
					} else if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) != startTime) {
						startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
					}

					gamePage.getScoreBoard().setTimer(remainingTime);

					for (Player player : players) {
						checkPlayerMoveAndSetState(player);
					}
					players.forEach(Moveable -> Moveable.move());
					for (Player player : players) {
						if (isPressSpace()) {
							player.setCanUseWeapon(count);
							if (player.isCanUseWeapon()) {
								Bomb bomb = new Bomb(player.getxPosition() / 50 * 50, player.getyPosition() / 50 * 50,
										"bomb", gamePage.getGameFieldPane(), player.getBombRange());
								count++;
								inputInGame.changeBitset();
							}
						}
					}

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

	public boolean isPressSpace() {
		return inputInGame.isKeyPress(Setting.PLAYERONE_PLACEBOMB);
	}

	public boolean isMoveAble(int x, int y, Player player) {
		int x2 = (x + 50 - player.getSpeed()) / 50;
		int y2 = (y + 50 - player.getSpeed()) / 50;
		x /= 50;
		y /= 50;
		return objectsArray[x][y] == ObjectInGame.EMPTY && objectsArray[x][y2] == ObjectInGame.EMPTY
				&& objectsArray[x2][y] == ObjectInGame.EMPTY && objectsArray[x2][y2] == ObjectInGame.EMPTY;
	}

	public void onRemoveScene() {
		inputInGame.removeListeners();
		inputInGame.clearKeyBoardCheck();
		this.scene = null;
		remainingTime = Setting.GAME_TIME;
	}

	private void createBackground() {
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 13; j++) {
				new Wall(50 * j, 50 * i, "background", gamePage.getGameFieldPane());
			}
		}
	}

	private void createGame() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				switch (objectsArray[i][j]) {
				case WALL:
					new Wall(i * 50, j * 50, gamePage.getGameFieldPane());
					break;
				case EMPTYOBSTACLE:
					new Obstacle(i * 50, j * 50, "obstacle", gamePage.getGameFieldPane(), null);
					break;
				}
			}
		}
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
