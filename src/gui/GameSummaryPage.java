package gui;

import controller.GameController;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

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
		mainMenuButton = new Button("Main Menu");

		setButtonAction();
		this.setOrientation(Orientation.VERTICAL);
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(mainLabel, statusLabel, continueButton, mainMenuButton);

	}

	@Override
	public void setButtonAction() {
		continueButton.setOnAction(e -> {
		});
		mainMenuButton.setOnAction(e -> {
			gameController.getGamePage().getScoreBoard().backToMainMenuAction();
		});
	}

}
