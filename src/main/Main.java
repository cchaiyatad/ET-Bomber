package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		StackPane pane = new StackPane();
		Scene scene = new Scene(pane, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
