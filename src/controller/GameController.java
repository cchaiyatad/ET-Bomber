package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import ai.AIBase;
import ai.Boss;
import ai.Minion;
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

public class GameController extends Controller {
	public static int level = 0;

	private GameObject[][] gameObjectArray = new GameObject[15][15];

	private List<PlayerBase> players;
	private List<Minion> minions;

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

		createPlayer();
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
						if (player instanceof AIBase) {
							((AIBase) player).checkForAction();
						}
					}

					if (minions != null) {
						minions.forEach(Minion -> Minion.checkForAction());
					}

					players.forEach(Moveable -> Moveable.move());

					if (minions != null) {
						minions.forEach(Moveable -> Moveable.move());
					}
					
					if(minions != null) {
						for(int i = 0; i < minions.size();i++) {
							minions.get(i).checkContactPlayer();
						}
					}

					for (PlayerBase player : players) {
						checkPlayerGetItem(player);
					}

					gamePage.getScoreBoard().updateStatus();

					for (PlayerBase player : players) {
						checkPlayerPlaceBomb(player);
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
		if (!(player instanceof Player)) {
			return;
		}

		boolean up = inputInGame.isKeyPress(Setting.MOVEUP_KEY);
		boolean right = inputInGame.isKeyPress(Setting.MOVERIGHT_KEY);
		boolean down = inputInGame.isKeyPress(Setting.MOVEDOWN_KEY);
		boolean left = inputInGame.isKeyPress(Setting.MOVELEFT_KEY);

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

	public int[] checkXYSpeed(int x, int y, PlayerBase player) {
		int[] xy = { 0, 0 };
		switch (player.getCurrentPlayerState()) {
		case MOVEDOWN:
			xy[1] = ((50 - ((y - player.getSpeed()) % 50)) % 50 < player.getSpeed() && x % 50 == 0
					&& !checkMove(x, y + 50 + player.getSpeed(), player)) ? (50 - ((y - player.getSpeed()) % 50)) % 50
							: player.getSpeed();
			break;
		case MOVERIGHT:
			xy[0] = ((50 - ((x - player.getSpeed()) % 50)) % 50 < player.getSpeed() && y % 50 == 0
					&& !checkMove(x + 50 + player.getSpeed(), y, player)) ? (50 - ((x - player.getSpeed()) % 50)) % 50
							: player.getSpeed();
			break;
		case MOVELEFT:
			xy[0] = ((x + player.getSpeed()) % 50 < player.getSpeed() && y % 50 == 0 && !checkMove(x, y, player))
					? ((x + player.getSpeed()) % 50) * -1
					: player.getSpeed() * -1;
			break;
		case MOVEUP:
			xy[1] = ((y + player.getSpeed()) % 50 < player.getSpeed() && x % 50 == 0 && !checkMove(x, y, player))
					? ((y + player.getSpeed()) % 50) * -1
					: player.getSpeed() * -1;
			break;
		default:
			break;
		}
		return xy;
	}

	private void clearGame() {
		removeGame();
		remainingTime = Setting.GAME_TIME;
		if (this.createBombThread != null) {
			this.createBombThread.interrupt();
		}
		this.createBombThread = null;
		currentNextThreadTime = 60;
		this.createBombThread = null;
	}

	public void restartGame() {
		clearGame();
		createGame();
		createPlayer();
		this.gameLoop = gameLoop();
		gameLoop.start();
	}

	public void onRemoveScene() {
		clearGame();
		players = null;
		minions = null;
		inputInGame.removeListeners();
		inputInGame.clearKeyBoardCheck();
		this.scene = null;
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
		if(this.createBombThread != null) {
			createBombThread.interrupt();
		}
		this.createBombThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(time * 1000);
					if (remainingTime <= endTime) {
						break;
					}
					if (!isPlaying()) {
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

	public boolean checkMove(int x, int y, PlayerBase player) {
		x /= 50;
		y /= 50;
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return false;
		}
		if(player.getPlayerNumber() == 3) {
			return !(gameObjectArray[x][y] instanceof Bomb) && !(gameObjectArray[x][y] instanceof Wall);
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

	private void checkPlayerPlaceBomb(PlayerBase player) {
		if (player.isDead()) {
			return;
		}

		if (!(player instanceof Player)) {
			return;
		}
		KeyCode placeBombKey = Setting.PLACEBOMB_KEY;
		if (inputInGame.isKeyPress(placeBombKey)) {
			player.useWeapon();
		}
		inputInGame.changeBitset(placeBombKey, false);
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
				default:
					continue;
				}
				gameObjectArray[i][j] = new Obstacle(i * 50, j * 50, gamePage.getGameFieldItemPane(), gameObject, this);
			}
		}
	}

	private void createPlayer() {
		int numberOfPlayer = level == 3 ? 4 : 2;
		if (players != null) {
			for (PlayerBase player : players) {
				player.onObjectIsDestroyed();
			}
		}
		players = new ArrayList<PlayerBase>();
		int dx = 1;
		int dy = 1;

		switch (level) {
		case 1:
			dx = 13;
			break;
		case 2:
			dx = 13;
			dy = 13;
			break;
		case 3:
			dy = 13;
			break;
		}

		for (int i = 0; i < numberOfPlayer; i++) {
			PlayerBase player = null;
			if (i == 0) {
				player = new Player(50 * dx, 50 * dy, "playerOne", gamePage.getGameFieldPlayerPane(), 1, this);
			} else if (i == 1) {
				String imagePath = "";
				switch (level % 3 + 2) {
				case 2:
					imagePath = "playerTwo";
					break;
				case 3:
					imagePath = "playerThree";
					break;
				case 4:
					imagePath = "playerFour";
					break;
				}
				player = new Boss(50 * (14 - dx), 50 * (14 - dy), imagePath, gamePage.getGameFieldPlayerPane(),
						level % 3 + 2, this, players.get(0));
				player.setHp(3);
				player.setSpeed(1);
			} else if (i == 2) {
				player = new Boss(50 * dx, 50 * (14 - dy), "playerThree", gamePage.getGameFieldPlayerPane(), 3, this,
						players.get(0));
				player.setHp(3);
				player.setSpeed(1);
			} else if (i == 3) {
				player = new Boss(50 * (14 - dx), 50 * dy, "playerFour", gamePage.getGameFieldPlayerPane(), 4, this,
						players.get(0));
				player.setHp(3);
				player.setSpeed(1);
			}
			players.add(player);
			gamePage.getScoreBoard().getPlayerStatusBoardViaIndex(i).linkToPlayer(player);
		}
	}

	private void checkGameFinish() {
		int survirorCount = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isDead()) {
				survirorCount++;
			}
		}

		if (survirorCount == 1 || players.get(0).isDead() || remainingTime == 0) {
			setSummaryPageAppear(true);
			clearGame();
			gameLoop.stop();
			removeGame();
			gameSummaryPage.setText(!(players.get(0).isDead() || remainingTime == 0));

			level = players.get(0).isDead() || remainingTime == 0 ? 0 : (level + 1) % 4;
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
		if (minions != null) {
			for (Minion minion : minions) {
				minion.onObjectIsDestroyed();
			}
			minions = null;
		}
	}

	public void setSummaryPageAppear(boolean value) {
		if (value) {
			this.gamePage.getChildren().add(gameSummaryPage);
		} else {
			this.gamePage.getChildren().remove(gameSummaryPage);
		}
	}

	public GameObject getGameObject(int x, int y) {
		return this.gameObjectArray[x][y];
	}

	public ObjectInGame getObjectInGame(int x, int y) {
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

	public List<Minion> getMinions() {
		if (minions == null) {
			minions = new ArrayList<Minion>();
		}
		return minions;
	}

	public long getRemainingTime() {
		return remainingTime;
	}
}
