package main;

import gamecontroller.GameController;
import gamecontroller.StartPageController;
import gui.StartPage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	private StartPageController startPageController;
	private GameController gameController;
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		startPageController = new StartPageController();
		gameController = new GameController();
//		primaryStage.setScene(gamecontroller.createGameScene());
		primaryStage.setScene(startPageController.createStartScene());
		primaryStage.setResizable(false);
		primaryStage.show();
//		AnimationTimer gameLoop = gamecontroller.gameLoop();
//		gameLoop.start();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
