package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gameobject.Destroyable;
import gameobject.GameObject;
import gameobject.Obstacle;
import gameobject.Wall;
import gui.GamePage;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.Player;
import player.PlayerState;
import setting.Setting;
import weapon.Bomb;

public class GameController extends Controller {
	private ObjectInGame[][] spawnObjectsInfomationArray = new ObjectInGame[15][15];
	private GameObject[][] gameObjectArray = new GameObject[15][15];
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
		spawnObjectsInfomationArray = levelGenerator.generateLevel();
		gamePage = new GamePage(this);

		createBackground();
		createGame();

		createPlayer(2);
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

	public GamePage getGamePage() {
		return this.gamePage;
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

	public void setObjectInGame(int x, int y, GameObject gameObject) {
		if (gameObjectArray[x][y] == null) {
			gameObjectArray[x][y] = gameObject;
		}
	}

	public boolean canSetObject(int x, int y) {
		return gameObjectArray[x][y] == null;
	}

	public AnimationTimer gameLoop() {
		if (this.gameLoop == null) {
			startTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			this.gameLoop = new AnimationTimer() {

				@Override
				public void handle(long now) {

					setTimer(now);
					gamePage.getScoreBoard().setTimer(remainingTime);

					for (Player player : players) {
						checkPlayerMoveAndSetState(player);
					}

					players.forEach(Moveable -> Moveable.move());

					for (Player player : players) {
						checkPlayerGetItem(player);
					}

					gamePage.getScoreBoard().updateStatus();

					for (Player player : players) {
						switch (player.getPlayerNumber()) {
						case 1:
							if (inputInGame.isKeyPress(Setting.PLAYERONE_PLACEBOMB) && player.isCanUseWeapon())
								player.useWeapon();
							break;

						case 2:
							if (inputInGame.isKeyPress(Setting.PLAYERTWO_PLACEBOMB) && player.isCanUseWeapon())
								player.useWeapon();
							break;
						}
						inputInGame.changeBitset(player);
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
		case 2:
			buttonUp = Setting.PLAYERTWO_MOVEUP;
			buttonRight = Setting.PLAYERTWO_MOVERIGHT;
			buttonDown = Setting.PLAYERTWO_MOVEDOWN;
			buttonLeft = Setting.PLAYERTWO_MOVELEFT;
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
//		boolean canMove = true;
//		switch (player.getCurrentPlayerState()) {
//		case MOVEDOWN:
//			canMove = checkMove(x / 50, (y + 50 - player.getSpeed()) / 50);
//			break;
//		case MOVERIGHT:
//			canMove = checkMove((x + 50 - player.getSpeed()) / 50, y / 50);
//			break;
//		case MOVELEFT:
//		case MOVEUP:
//			canMove = checkMove(x / 50, y / 50);
//			break;
//		default:
//			break;
//		}
//		return canMove;
		int x2 = (x + 50 - player.getSpeed()) / 50;
		int y2 = (y + 50 - player.getSpeed()) / 50;
		x /= 50;
		y /= 50;
		return checkMove(x, y) && checkMove(x, y2) && checkMove(x2, y) && checkMove(x2, y2);
	}

	public void onRemoveScene() {
		inputInGame.removeListeners();
		inputInGame.clearKeyBoardCheck();
		this.scene = null;
		remainingTime = Setting.GAME_TIME;
	}

	private void setTimer(long now) {
		if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime == 1) {
			remainingTime -= TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
			startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
		} else if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) != startTime) {
			startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
		}
	}

	private boolean checkMove(int x, int y) {
		return !(gameObjectArray[x][y] instanceof Wall) && !(gameObjectArray[x][y] instanceof Obstacle);
//		return !(gameObjectArray[x][y] instanceof Bomb) && !(gameObjectArray[x][y] instanceof Wall)
//				&& !(gameObjectArray[x][y] instanceof Obstacle);
	}

	private void checkPlayerGetItem(Player player) {
		int x = player.getxPosition();
		int y = player.getyPosition();
		int x2 = (x + 50 - player.getSpeed()) / 50;
		int y2 = (y + 50 - player.getSpeed()) / 50;
		x = x / 50;
		y = y / 50;

		if (x != x2) {
			getItem(x, y, player);
			getItem(x2, y, player);
		} else if (y != y2) {
			getItem(x, y, player);
			getItem(x, y2, player);
		}
	}

	private void getItem(int x, int y, Player player) {
		if (gameObjectArray[x][y] != null && gameObjectArray[x][y] instanceof Item) {
			((PowerUp) gameObjectArray[x][y]).onPlayerGetItem(player);
			((Item) gameObjectArray[x][y]).onObjectIsDestroyed();
			gameObjectArray[x][y] = null;
		}
	}

	public void removeItem(int x, int y) {
		if (gameObjectArray[x][y] != null) {
			gameObjectArray[x][y] = null;
		}
	}

	private void createBackground() {
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 13; j++) {
				new Wall(50 * j, 50 * i, "background", gamePage.getGameFieldItemPane());
			}
		}
	}

	private void createGame() {

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				GameObject gameObject = null;
				switch (spawnObjectsInfomationArray[i][j]) {
				case WALL:
					gameObject = new Wall(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case OBSTACLE:
					gameObject = new Obstacle(i * 50, j * 50, gamePage.getGameFieldItemPane(), null);
					break;
				case BOMBUPGRADEITEM:
					gameObject = new BombUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case POWERUPGRADEITEM:
					gameObject = new PowerUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case SPEEDUPGRADEITEM:
					gameObject = new SpeedUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case LIFEINCREASEITEM:
					gameObject = new LifeIncreaseItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
//					gameObject = new Obstacle(i * 50, j * 50, gamePage.getGameFieldPane(),
//							new LifeIncreaseItem(i * 50, j * 50, gamePage.getGameFieldPane()));
//					spawnObjectsInfomationArray[i][j] = ObjectInGame.OBSTACLE;
					break;
				case SHIELDITEM:
					gameObject = new Shield(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case PUSHBOMBSKILLITEM:
					gameObject = new PushBombSkillItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case LANDMINEITEM:
					gameObject = new LandMineItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case GRENADEITEM:
					gameObject = new GrenadeItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case POISIONDARTITEM:
					gameObject = new PoisonDartItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case ROCKETLAUNCHERITEM:
					gameObject = new RocketLauncherItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				case REMOTEBOMBITEM:
					gameObject = new RemoteBombItem(i * 50, j * 50, gamePage.getGameFieldItemPane());
					break;
				default:
					break;
				}
				gameObjectArray[i][j] = gameObject;
			}
		}
//		for (int i = 0; i < 15; i++) {
//			for (int j = 0; j < 15; j++) {
//				System.out.print(i + " " + j + " " + spawnObjectsInfomationArray[i][j] + "\t");
//			}
//			System.out.println();
//		}

//		for (int i = 0; i < 15; i++) {
//			for (int j = 0; j < 15; j++) {
//				System.out.print(i + " " + j + " " + gameObjectArray[i][j] + "\t");
//			}
//			System.out.println();
//		}
	}

	private void createPlayer(int numberOfPlayer) {
		players = new ArrayList<Player>();
		for (int i = 0; i < numberOfPlayer; i++) {
			Player player = null;
			if (i == 0) {
				player = new Player(50, 50, "playerOne", gamePage.getGameFieldPlayerPane(), 1, this);
			} else if (i == 1) {
				player = new Player(50 * 13, 50 * 13, "playerTwo", gamePage.getGameFieldPlayerPane(), 2, this);
			}
			players.add(player);
			gamePage.getScoreBoard().getPlayerStatusBoardViaIndex(i).linkToPlayer(player);
		}
	}

}
