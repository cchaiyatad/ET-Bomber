package main;

import controller.GameController;
import controller.StartPageController;
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
		
		startPageController.setOtherController(gameController);
		startPageController.setStage(primaryStage);
		gameController.setOtherController(startPageController);
		gameController.setStage(primaryStage);
		
		primaryStage.setScene(startPageController.getScene());
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
