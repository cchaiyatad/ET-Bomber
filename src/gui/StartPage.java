package gui;

import controller.Controller;
import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import setting.Setting;

public class StartPage extends StackPane {

	private Label gameLabel;
	private Button playButton;
	private Button instuctionButton;
	private Button quitButton;
	private InstuctionPage instuctionPage;
	
	private Controller controller;

	public StartPage(Controller controller) {
		this.controller = controller;
		this.setPrefSize(Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		this.setAlignment(Pos.CENTER);
		
		VBox mainMenu = new VBox();
		mainMenu.setAlignment(Pos.CENTER);
		
		gameLabel = new Label("Bomberman");
		playButton = new Button("PLAY");
		instuctionButton = new Button("INSTUCTION");
		quitButton = new Button("QUIT");

		instuctionPage = new InstuctionPage();
		
		setButtonAction();
		mainMenu.getChildren().addAll(gameLabel, playButton, instuctionButton, quitButton);
		
		this.getChildren().add(mainMenu);
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
			this.getChildren().add(instuctionPage);
		});
		quitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
	}
}
