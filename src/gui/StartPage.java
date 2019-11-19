package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartPage extends VBox {

	private Label gameLabel;
	private Button playButton;
	private Button instuctionButton;
	private Button quitButton;

	public StartPage() {
		this.setAlignment(Pos.CENTER);
		gameLabel = new Label("Bomberman");
		playButton = new Button("PLAY");
		instuctionButton = new Button("INSTUCTION");
		quitButton = new Button("QUIT");
		setButtonAction();

		this.getChildren().addAll(gameLabel, playButton, instuctionButton, quitButton);
	}

	private void setButtonAction() {
		playButton.setOnAction(e -> {
		});
		instuctionButton.setOnAction(e -> {
		});
		quitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

	}
}
