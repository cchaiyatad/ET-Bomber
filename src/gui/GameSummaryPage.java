package gui;

import controller.GameController;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class GameSummaryPage extends FlowPane implements HasButtonPage {

	private GameController gameController;
	private Label mainLabel;
	private Label statusLabel;
	private Button continueButton;
	private Button mainMenuButton;

	public GameSummaryPage(GameController gameController) {
		this.gameController = gameController;
		mainLabel = new Label("Player X win");
		statusLabel = new Label("Player 1 : 0 game\n" + "Player 2 : 0 game");
		continueButton = new Button("Continue");
		continueButton.setFocusTraversable(false);
		mainMenuButton = new Button("Main Menu");
		mainMenuButton.setFocusTraversable(false);

		setButtonAction();
		this.setOrientation(Orientation.VERTICAL);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.NAVAJOWHITE, null, null)));
		this.getChildren().addAll(mainLabel, statusLabel, continueButton, mainMenuButton);

	}

	@Override
	public void setButtonAction() {
		continueButton.setOnAction(e -> {
			gameController.setSummaryPageAppear(false);
			gameController.restartGame();
		});
		mainMenuButton.setOnAction(e -> {
			gameController.getGamePage().getScoreBoard().backToMainMenuAction();
		});
	}

	public void setText(int[] data) {
		mainLabel.setText(String.format("Player %d win!!", data[0] + 1));
		statusLabel.setText(String.format("Player 1: %d\nPlayer 2: %d\nPlayer 3: %d\nPlayer 4: %d", data[1], data[2],
				data[3], data[4]));
	}

}
