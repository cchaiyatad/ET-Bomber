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
		mainLabel = new Label("");
		statusLabel = new Label("");
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

	public void setText(boolean isSurvive) {
		String buttonText = isSurvive && !(GameController.level == 3) ? "Continue" : "Restart";
		String mainLabelText = isSurvive ? "You survive" : "You died";
		String statusLabelText;
		if (GameController.level == 3) {
			statusLabelText = isSurvive ? "You defeated all enemy" : "You was defeated";
		} else {
			statusLabelText = isSurvive ? "You defeated " + GameController.level + " monster"
					: "You was defeated by " + GameController.level + " monster";
		}
		mainLabel.setText(mainLabelText);
		statusLabel.setText(statusLabelText);
		continueButton.setText(buttonText);
	}

}
