package main;

import gamecontroller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	private GameController gamecontroller;
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		gamecontroller = new GameController();
		primaryStage.setScene(gamecontroller.CreateGameScene());
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
