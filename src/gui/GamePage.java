package gui; 

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import setting.Setting;

public class GamePage extends StackPane {
	private GameField gameField;
	private ScoreBoard scoreBoard;
	
	public GamePage(Controller controller) {
		HBox gameHBoxPage = new HBox();
		gameHBoxPage.setPadding(new Insets(20));
		gameHBoxPage.setPrefSize(Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		gameHBoxPage.setSpacing(20);
		
		scoreBoard = new ScoreBoard(controller);
		gameHBoxPage.getChildren().add(scoreBoard);
		
		gameField = new GameField();
		gameHBoxPage.getChildren().add(gameField);
		this.getChildren().add(gameHBoxPage);
	}
	
	public Pane getGameFieldItemPane() {
		return gameField.getItemLayer();
	}
	
	public Pane getGameFieldPlayerPane() {
		return gameField.getPlayerLayer();
	}
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}
		
}
