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
import javafx.scene.text.Font;
import setting.Setting;

public class StartPage extends StackPane implements HasButtonPage {

	private Button playButton;
	private Button instuctionButton;
	private Button creditButton;
	private Button quitButton;
	private InstuctionPage instuctionPage;
	private CreditPage creditPage;

	private Controller controller;

	public StartPage(Controller controller) {
		String fontpath = ClassLoader.getSystemResource("font/PixelEmulator-xq08.ttf").toString();
		this.controller = controller;
		this.setPrefSize(Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		this.setAlignment(Pos.CENTER);

		VBox mainMenu = new VBox();
		mainMenu.setAlignment(Pos.CENTER);
		mainMenu.setSpacing(20);

		Label gameLabel = new Label("E.T. Bomber");
		gameLabel.setFont(Font.loadFont(fontpath, 70));

		playButton = new Button("PLAY");
		playButton.setFont(Font.loadFont(fontpath, 30));
		playButton.setFocusTraversable(false);

		instuctionButton = new Button("INSTUCTION");
		instuctionButton.setFont(Font.loadFont(fontpath, 30));
		instuctionButton.setFocusTraversable(false);
		

		creditButton = new Button("CREDIT");
		creditButton.setFont(Font.loadFont(fontpath, 30));
		creditButton.setFocusTraversable(false);

		quitButton = new Button("QUIT");
		quitButton.setFont(Font.loadFont(fontpath, 30));
		quitButton.setFocusTraversable(false);

		instuctionPage = new InstuctionPage(this);
		creditPage = new CreditPage(this);
		

		setButtonAction();
		mainMenu.getChildren().addAll(gameLabel, playButton, instuctionButton, creditButton, quitButton);

		this.getChildren().add(mainMenu);
	}

	@Override
	public void setButtonAction() {
		playButton.setOnAction(e -> {
			Controller otherController = controller.getOtherController();
			controller.getStage().setScene(otherController.getScene());

			if (otherController instanceof GameController) {
				GameController.level = 0;
				AnimationTimer gameLoop = ((GameController) otherController).gameLoop();
				gameLoop.start();
				((GameController) otherController).setPlaying(true);
			}
		});

		instuctionButton.setOnAction(e -> {
			setPageAppear(instuctionPage,true);
		});
		
		creditButton.setOnAction(e -> {
			setPageAppear(creditPage,true);
		});

		quitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
	}

	public void setPageAppear(VBox page, boolean value) {
		if (value) {
			this.getChildren().add(page);
		} else {
			this.getChildren().remove(page);
		}
	}
	

}
