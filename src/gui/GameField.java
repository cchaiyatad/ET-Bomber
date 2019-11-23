package gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameField extends StackPane{
	private Pane itemLayer;
	private Pane playerLayer;
	
	public GameField() {
		this.setPrefSize(50 * 15, 50 * 15);
		itemLayer = new Pane();
		playerLayer = new Pane();
		this.getChildren().add(itemLayer);
		this.getChildren().add(playerLayer);
	}
	
	public Pane getItemLayer() {
		return itemLayer;
	}
	
	public Pane getPlayerLayer() {
		return playerLayer;
	}
}
