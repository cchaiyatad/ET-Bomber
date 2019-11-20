package gui; 

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import setting.Setting;

public class GamePage extends HBox {
	private GameField gameField;
	private ScoreBoard scoreBoard;
	
	public GamePage(Controller controller) {
		this.setPadding(new Insets(20));
		this.setPrefSize(Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		this.setSpacing(20);
		
		scoreBoard = new ScoreBoard(controller);
		this.getChildren().add(scoreBoard);
		
		gameField = new GameField();
		this.getChildren().add(gameField);
	}
	
	public Pane getGameFieldPane() {
		return gameField.getLayer();
	}
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
		
}
