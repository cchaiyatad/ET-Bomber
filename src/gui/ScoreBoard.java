package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreBoard extends VBox{
	
	private PlayerStatusBoard[] playerStatusBoards = new PlayerStatusBoard[4];
	private Label timer;
	private Button pauseButton;
	private Button quitButton;
	
	public ScoreBoard() {
		this.setPrefSize(50 * 4, 750);
		this.setBackground(new Background(new BackgroundFill(Color.BROWN, null, null)));
//		this.setSpacing(10);
		
		for(int i = 0; i < 4; i++) {
			playerStatusBoards[i] = new PlayerStatusBoard();
			this.getChildren().add(playerStatusBoards[i]);
		}
		
		
	}
	
	public PlayerStatusBoard getPlayerStatusBoardViaIndex(int index) {
		return playerStatusBoards[index];
	}
}
