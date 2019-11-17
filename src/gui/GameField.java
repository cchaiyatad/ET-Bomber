package gui;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane; 
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameField extends VBox{
	private Pane layer;
	public GameField() {
		this.setPrefSize(50 * 15, 50 * 15);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		layer = new Pane();
		layer.setBackground(new Background(new BackgroundFill(Color.CRIMSON, null, null)));
		this.getChildren().add(layer);
	}
	
	public Pane getLayer() {
		return layer;
	}
}
