package main;

import gamecontroller.InputInGame;
import gui.GameScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import setting.Setting;

public class Main extends Application{
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		GameScene gameScene = new GameScene();
		Scene scene = new Scene(gameScene, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		InputInGame inputInGame = new InputInGame(scene);
		inputInGame.AddListeners();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
