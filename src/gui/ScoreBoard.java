package gui;

import controller.Controller;
import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreBoard extends VBox implements HasButton {

	private PlayerStatusBoard[] playerStatusBoards = new PlayerStatusBoard[4];
	private Label timer;
	private Button pauseButton;
	private Button mainMenuButton;
	private Controller controller;

	public ScoreBoard(Controller controller) {
		this.setPrefSize(50 * 4, 750);
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, null, null)));

		for (int i = 0; i < 4; i++) {
			playerStatusBoards[i] = new PlayerStatusBoard();
			this.getChildren().add(playerStatusBoards[i]);
		}

		timer = new Label("0:00");
		timer.setPrefSize(200, 40);

		pauseButton = new Button("PAUSE");
		pauseButton.setPrefSize(200, 35);
		pauseButton.setFocusTraversable(false);

		mainMenuButton = new Button("MAIN MENU");
		mainMenuButton.setPrefSize(200, 35);
		mainMenuButton.setFocusTraversable(false);

		setButtonAction();
		this.getChildren().addAll(timer, pauseButton, mainMenuButton);
		this.controller = controller;
	}

	public PlayerStatusBoard getPlayerStatusBoardViaIndex(int index) {
		return playerStatusBoards[index];
	}
	
	public void setTimer(long time) {
		int minute = (int) (time / 60);
		int second = (int) (time % 60);
		String timeText = String.format("%01d:%02d", minute, second);
		timer.setText(timeText);
	}

	@Override
	public void setButtonAction() {

		pauseButton.setOnAction(e -> {
			if (controller instanceof GameController) {
				AnimationTimer gameLoop = ((GameController) controller).gameLoop();
				GameController gameController = (GameController) controller;
				if (gameController.isPlaying()) {
					gameLoop.stop();
					gameController.setPlaying(false);
				} else {
					gameLoop.start();
					gameController.setPlaying(true);
					gameController.setStartTime(System.nanoTime());
				}
				String pauseButtonText = gameController.isPlaying() ? "PAUSE" : "RESUME";
				pauseButton.setText(pauseButtonText);
			}
		});
		
		mainMenuButton.setOnAction(e -> {
			if (controller instanceof GameController) {
				AnimationTimer gameLoop = ((GameController) controller).gameLoop();
				gameLoop.stop();
				((GameController) controller).setPlaying(false);
				((GameController) controller).onRemoveScene();
			}
			Controller otherController = controller.getOtherController();
			controller.getStage().setScene(otherController.getScene());
		});

	}
}
