package gui;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreBoard extends VBox{
	public ScoreBoard() {
		this.setPrefSize(50 * 4, 750);
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, null, null)));
	}
}
