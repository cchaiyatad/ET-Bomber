package gui;

import controller.Controller;
import controller.GameController;
import controller.StartPageController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartPage extends VBox {

	private Label gameLabel;
	private Button playButton;
	private Button instuctionButton;
	private Button quitButton;

	private Controller controller;

	public StartPage(Controller controller) {
		this.controller = controller;
		this.setAlignment(Pos.CENTER);
		gameLabel = new Label("Bomberman");
		playButton = new Button("PLAY");
		instuctionButton = new Button("INSTUCTION");
		quitButton = new Button("QUIT");
		setButtonAction();

		this.getChildren().addAll(gameLabel, playButton, instuctionButton, quitButton);
	}

	private void setButtonAction() {
		playButton.setOnAction(e -> {
			Controller otherController = controller.getOtherController();
			controller.getStage().setScene(otherController.getScene());

			if (otherController instanceof GameController) {
				AnimationTimer gameLoop = ((GameController) otherController).gameLoop();
				gameLoop.start();
			}
		});
		
		instuctionButton.setOnAction(e -> {
		});
		quitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
	}
}
