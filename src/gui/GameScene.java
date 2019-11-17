package gui; 

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import setting.Setting;

public class GameScene extends HBox{
	private GameField gameField;
	private ScoreBoard scoreBoard;
	
	public GameScene() {
		this.setPadding(new Insets(20));
		this.setPrefSize(Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		this.setSpacing(20);
		
		scoreBoard = new ScoreBoard();
		this.getChildren().add(scoreBoard);
		
		gameField = new GameField();
		this.getChildren().add(gameField);
	}
	
	public Pane GetGameFieldPane() {
		return gameField.getLayer();
	}
		
}
