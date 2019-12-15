package main;

import controller.GameController;
import controller.StartPageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
	private StartPageController startPageController;
	private GameController gameController;
	public static String fontpath = ClassLoader.getSystemResource("font/PixelEmulator-xq08.ttf").toString();

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

	@Override
	public void stop() throws Exception {
		Platform.exit();
		System.exit(0);
	}

}
