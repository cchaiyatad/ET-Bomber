package gui;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameField extends Pane{
	
	public GameField() {
		this.setPrefSize(50 * 15, 50 * 15);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		
	}
	
}
