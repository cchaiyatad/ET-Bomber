package gui;

import javafx.scene.layout.Pane; 
import javafx.scene.layout.VBox;

public class GameField extends VBox{
	private Pane layer;
	public GameField() {
		this.setPrefSize(50 * 15, 50 * 15);
		layer = new Pane();
		this.getChildren().add(layer);
	}
	
	public Pane getLayer() {
		return layer;
	}
}
