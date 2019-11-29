package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import ai.AI;
import gameobject.Destroyable;
import gameobject.GameObject;
import gameobject.Obstacle;
import gameobject.Wall;
import gui.GamePage;
import gui.GameSummaryPage;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import player.*;
import setting.Setting;
import weapon.Bomb;
import weapon.Weapon;
import weapon.WeaponType;

public class GameController extends Controller {
	private GameObject[][] gameObjectArray = new GameObject[15][15];
	private List<PlayerBase> players;

	private LevelGenerator levelGenerator;
	private GameSummaryPage gameSummaryPage;
	private GamePage gamePage;
	private InputInGame inputInGame;
	private AnimationTimer gameLoop;
	private boolean isPlaying;
	private long remainingTime = Setting.GAME_TIME;
	private long startTime;
	private Thread createBombThread;
	private int currentNextThreadTime = 60;

	@Override
	protected Scene createScene() {

		gamePage = new GamePage(this);
		gameSummaryPage = new GameSummaryPage(this);
		levelGenerator = new LevelGenerator();
		createBackground();
		createGame();

		createPlayer(4);
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

	public List<PlayerBase> getPlayers() {
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

//			AStar astar = new AStar(this);
			startTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
			this.gameLoop = new AnimationTimer() {

				@Override
				public void handle(long now) {

					checkGameFinish();
					setTimer(now);
					checkRemainingTime();
					gamePage.getScoreBoard().setTimer(remainingTime);

					for (PlayerBase player : players) {
						checkPlayerMoveAndSetState(player);
					}

					for (PlayerBase player : players) {
						if (player instanceof AI) {
							((AI) player).checkStatus();
						}
					}

					players.forEach(Moveable -> Moveable.move());

					for (PlayerBase player : players) {
						checkPlayerGetItem(player);
					}

					gamePage.getScoreBoard().updateStatus();

					for (PlayerBase player : players) {
						checkPlayerPlaceBome(player);
					}

					/// Debug
					for (PlayerBase player : players) {
						KeyCode key = null;
						switch (player.getPlayerNumber()) {
						case 1:
							key = KeyCode.U;
							break;
						case 2:
							key = KeyCode.I;
							break;
						case 3:
							key = KeyCode.O;
							break;
						case 4:
							key = KeyCode.P;
							break;
						default:
							return;
						}

						if (inputInGame.isKeyPress(key)) {
							player.setHp(player.getHp() - 1);
						}
						inputInGame.changeBitset(key, false);
					}
					///

					/// Debug
					if (inputInGame.isKeyPress(KeyCode.Y)) {
						PlayerBase player = players.get(0);
						int x = player.getxPosition() / 50;
						int y = player.getyPosition() / 50;
						if (gameObjectArray[x + 1][y] != null && gameObjectArray[x + 1][y] instanceof Destroyable) {
							((Destroyable) gameObjectArray[x + 1][y]).onObjectIsDestroyed();
						}
						inputInGame.changeBitset(KeyCode.Y, false);
					}
					// Debug
					if (inputInGame.isKeyPress(KeyCode.T)) {
						PlayerBase player = players.get(0);
						int x = player.getxPosition() / 50;
						int y = player.getyPosition() / 50;
						if (gameObjectArray[x][y + 1] != null && gameObjectArray[x][y + 1] instanceof Destroyable) {
							((Destroyable) gameObjectArray[x][y + 1]).onObjectIsDestroyed();
						}
						inputInGame.changeBitset(KeyCode.T, false);

					}
					// Debug
					if (inputInGame.isKeyPress(KeyCode.R)) {
						players.get(0).setCurrentWeapon(WeaponType.BOMB);
						inputInGame.changeBitset(KeyCode.R, false);

					}

				}
			};
		}
		return gameLoop;
	}

	private void checkPlayerMoveAndSetState(PlayerBase player) {
		if (player.isDead()) {
			return;
		}
		KeyCode buttonUp = null;
		KeyCode buttonRight = null;
		KeyCode buttonDown = null;
		KeyCode buttonLeft = null;
		if (player instanceof Player) {
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
	}

	public int[] isMoveAble(int x, int y, PlayerBase player) {
		int[] xy = { 0, 0 };
		switch (player.getCurrentPlayerState()) {
		case MOVEDOWN:
			xy[1] = ((50 - ((y - player.getSpeed()) % 50)) % 50 < player.getSpeed() && x % 50 == 0
					&& !checkMove(x, y + 50 + player.getSpeed())) ? (50 - ((y - player.getSpeed()) % 50)) % 50
							: player.getSpeed();
			break;
		case MOVERIGHT:
			xy[0] = ((50 - ((x - player.getSpeed()) % 50)) % 50 < player.getSpeed() && y % 50 == 0
					&& !checkMove(x + 50 + player.getSpeed(), y)) ? (50 - ((x - player.getSpeed()) % 50)) % 50
							: player.getSpeed();
			break;
		case MOVELEFT:
			xy[0] = ((x + player.getSpeed()) % 50 < player.getSpeed() && y % 50 == 0 && !checkMove(x, y))
					? ((x + player.getSpeed()) % 50) * -1
					: player.getSpeed() * -1;
			break;
		case MOVEUP:
			xy[1] = ((y + player.getSpeed()) % 50 < player.getSpeed() && x % 50 == 0 && !checkMove(x, y))
					? ((y + player.getSpeed()) % 50) * -1
					: player.getSpeed() * -1;
			break;
		default:
			break;
		}
		return xy;

	}

	public void restartGame() {
		removeGame();
		createGame();
		createPlayer(4);
		this.gameLoop = gameLoop();
		gameLoop.start();
		remainingTime = Setting.GAME_TIME;
		if (this.createBombThread != null) {
			this.createBombThread.interrupt();
		}
		currentNextThreadTime = 60;
		this.createBombThread = null;
	}

	public void onRemoveScene() {
		removeGame();
		players = null;
		inputInGame.removeListeners();
		inputInGame.clearKeyBoardCheck();
		this.scene = null;
		remainingTime = Setting.GAME_TIME;
		if (this.createBombThread != null) {
			this.createBombThread.interrupt();
		}
		currentNextThreadTime = 60;
		this.createBombThread = null;
	}

	private void setTimer(long now) {
		if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime == 1) {
			remainingTime -= TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
			startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
		} else if (TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS) != startTime) {
			startTime = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
		}
	}

	private void checkRemainingTime() {
		int generateRateSec = 0;
		int bombGenerateRate = 0;
		long endTime = 0;
		if (remainingTime <= 0) {
			remainingTime = 0;
			generateRateSec = 1;
			bombGenerateRate = 4;
			endTime = -1;
		} else if (remainingTime == 15) {
			generateRateSec = 1;
			bombGenerateRate = 3;
			endTime = 0;
		} else if (remainingTime == 30) {
			generateRateSec = 2;
			bombGenerateRate = 3;
			endTime = 15;
		} else if (remainingTime == 45) {
			generateRateSec = 2;
			bombGenerateRate = 2;
			endTime = 30;
		} else if (remainingTime == 60) {
			generateRateSec = 2;
			bombGenerateRate = 1;
			endTime = 45;
		} else {
			return;
		}
		if (currentNextThreadTime == remainingTime) {
			generateBombThread(generateRateSec, bombGenerateRate, endTime);
		}
	}

	private void generateBombThread(int time, int bomb, long endTime) {
		Random random = new Random();
		this.createBombThread = new Thread(() -> {

			while (true) {
				try {
					Thread.sleep(time * 1000);
					if (remainingTime <= endTime) {
						break;
					}
					if(!isPlaying()) {
						continue;
					}
					Platform.runLater(() -> {
						for (int i = 0; i < bomb; i++) {
							int x;
							int y;
							while (true) {
								x = random.nextInt(13) + 1;
								y = random.nextInt(13) + 1;
								if (canSetObject(x, y)) {
									setObjectInGame(x, y, new Bomb(x * 50, y * 50, getGamePage().getGameFieldItemPane(),
											2, null, this));
									break;
								}
							}

						}
					});
				} catch (InterruptedException e) {
					break;
				}
			}
		});
		this.createBombThread.start();
		this.currentNextThreadTime = (int) endTime;

	}

	public boolean checkMove(int x, int y) {
		x /= 50;
		y /= 50;
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return false;
		}
		return !(gameObjectArray[x][y] instanceof Bomb) && !(gameObjectArray[x][y] instanceof Wall)
				&& !(gameObjectArray[x][y] instanceof Obstacle);
	}

	private void checkPlayerGetItem(PlayerBase player) {
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

	private void checkPlayerPlaceBome(PlayerBase player) {
		if (player.isDead()) {
			return;
		}
		KeyCode placeBombKey = null;
		if (player instanceof Player) {
			switch (player.getPlayerNumber()) {
			case 1:
				placeBombKey = Setting.PLAYERONE_PLACEBOMB;
				break;
			case 2:
				placeBombKey = Setting.PLAYERTWO_PLACEBOMB;
				break;
			default:
				return;
			}

			if (inputInGame.isKeyPress(placeBombKey)) {
				player.useWeapon();
			}
			inputInGame.changeBitset(placeBombKey, false);
		}
	}

	private void getItem(int x, int y, PlayerBase player) {
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
		ObjectInGame[][] spawnObjectsInfomationArray = levelGenerator.generateLevel();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {

				if (spawnObjectsInfomationArray[i][j] == ObjectInGame.WALL) {
					gameObjectArray[i][j] = new Wall(i * 50, j * 50, gamePage.getGameFieldItemPane());
					continue;
				}
				Item gameObject = null;
				switch (spawnObjectsInfomationArray[i][j]) {
				case OBSTACLE:
					break;
				case BOMBUPGRADEITEM:
					gameObject = new BombUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case POWERUPGRADEITEM:
					gameObject = new PowerUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case SPEEDUPGRADEITEM:
					gameObject = new SpeedUpgradeItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case LIFEINCREASEITEM:
					gameObject = new LifeIncreaseItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case SHIELDITEM:
					gameObject = new Shield(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case LANDMINEITEM:
					gameObject = new LandMineItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				case REMOTEBOMBITEM:
					gameObject = new RemoteBombItem(i * 50, j * 50, gamePage.getGameFieldItemPane(), this);
					break;
				default:
					continue;
				}
				gameObjectArray[i][j] = new Obstacle(i * 50, j * 50, gamePage.getGameFieldItemPane(), gameObject, this);
			}
		}
	}

	private void createPlayer(int numberOfPlayer) {
		if (players == null) {
			players = new ArrayList<PlayerBase>();
		}

		for (int i = 0; i < numberOfPlayer; i++) {
			PlayerBase player = null;
			if (i == 0) {
				player = new Player(50, 50, "playerOne", gamePage.getGameFieldPlayerPane(), 1, this);
			} else if (i == 1) {
				player = new Player(50 * 13, 50 * 13, "playerTwo", gamePage.getGameFieldPlayerPane(), 2, this);
			} else if (i == 2) {
				player = new AI(50 * 13, 50, "playerThree", gamePage.getGameFieldPlayerPane(), 3, this, players.get(0),
						players.get(1));
			} else if (i == 3) {
				player = new AI(50, 50 * 13, "playerFour", gamePage.getGameFieldPlayerPane(), 4, this, players.get(0),
						players.get(1));
				((AI) player).setOtherAI(players.get(2));
				((AI) players.get(2)).setOtherAI(player);
			}
			if (players.size() == numberOfPlayer) {
				player.setScore(players.get(i).getScore());
				players.get(i).onObjectIsDestroyed();
				players.set(i, player);
			} else {
				players.add(player);
			}
			gamePage.getScoreBoard().getPlayerStatusBoardViaIndex(i).linkToPlayer(player);
		}
	}

	private void checkGameFinish() {
		int survirerCount = 0;
		int survirerIndex = -1;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isDead()) {
				survirerCount++;
				survirerIndex = i;
			}
		}
		if (survirerCount == 1) {
			setSummaryPageAppear(true);
			players.get(survirerIndex).setScore(players.get(survirerIndex).getScore() + 1);
			gameLoop.stop();
			removeGame();
			int[] data = new int[players.size() + 1];
			data[0] = survirerIndex;
			for (int i = 0; i < players.size(); i++) {
				data[i + 1] = players.get(i).getScore();
			}
			gameSummaryPage.setText(data);
		}
	}

	private void removeGame() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (gameObjectArray[i][j] instanceof Obstacle) {
					((Destroyable) gameObjectArray[i][j]).onObjectIsDestroyed();
				}
				if (gameObjectArray[i][j] instanceof Destroyable) {
					((Destroyable) gameObjectArray[i][j]).onObjectIsDestroyed();
				}
				gameObjectArray[i][j] = null;

			}
		}
	}

	public void setSummaryPageAppear(boolean value) {
		if (value) {
			this.gamePage.getChildren().add(gameSummaryPage);
		} else {
			this.gamePage.getChildren().remove(gameSummaryPage);
		}
	}

	public GameObject getObjecyInGame(int x, int y) {
		return this.gameObjectArray[x][y];
	}

	public ObjectInGame getObjectOnPositionXY(int x, int y) {
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return null;
		}
		
		if (gameObjectArray[x][y] != null && gameObjectArray[x][y] instanceof Wall) {
			return ObjectInGame.WALL;
		} else if (gameObjectArray[x][y] != null && (gameObjectArray[x][y] instanceof Obstacle)) {
			return ObjectInGame.OBSTACLE;
		} else if (gameObjectArray[x][y] != null && (gameObjectArray[x][y] instanceof Item)) {
			return ((Item) gameObjectArray[x][y]).getObjectInGame();
		} else if (gameObjectArray[x][y] != null && (gameObjectArray[x][y] instanceof Weapon)) {
			return ((Weapon) gameObjectArray[x][y]).getObjectInGame();
		}
		return ObjectInGame.EMPTY;
	}
}
