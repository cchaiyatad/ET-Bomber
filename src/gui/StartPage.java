package gui;

import controller.Controller;
import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import setting.Setting;

public class StartPage extends StackPane implements HasButton{

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
		playButton.setFocusTraversable(false);
		
		instuctionButton = new Button("INSTUCTION");
		instuctionButton.setFocusTraversable(false);
		
		quitButton = new Button("QUIT");
		quitButton.setFocusTraversable(false);

		instuctionPage = new InstuctionPage(this);
		
		setButtonAction();
		mainMenu.getChildren().addAll(gameLabel, playButton, instuctionButton, quitButton);
		
		this.getChildren().add(mainMenu);
	}

	@Override
	public void setButtonAction() {
		playButton.setOnAction(e -> {
			Controller otherController = controller.getOtherController();
			controller.getStage().setScene(otherController.getScene());

			if (otherController instanceof GameController) {
				AnimationTimer gameLoop = ((GameController) otherController).gameLoop();
				gameLoop.start();
				((GameController) otherController).setPlaying(true);
			}
		});
		
		instuctionButton.setOnAction(e -> {
			setInstructionAppear(true);
		});
		
		quitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
	}
	
	public void setInstructionAppear(boolean value) {
		if(value) {
			this.getChildren().add(instuctionPage);
		}else {
			this.getChildren().remove(instuctionPage);
		}
	}

}
