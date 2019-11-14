package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		VBox pane = new VBox();
		Scene scene = new Scene(pane, 960, 790);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
