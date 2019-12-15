package main;

import controller.GameController;
import controller.StartPageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
	public static String fontpath = ClassLoader.getSystemResource("font/PixelEmulator-xq08.ttf").toString();
	private GameController gameController;
	private StartPageController startPageController;
	public static void main(String[] args) {
		launch(args);
	}

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

	@Override
	public void stop() throws Exception {
		Platform.exit();
		System.exit(0);
	}

}
