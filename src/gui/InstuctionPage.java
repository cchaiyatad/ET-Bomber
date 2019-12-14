package gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import setting.Setting;

public class InstuctionPage extends VBox implements HasButtonPage {
	private StartPage startPage;
	private Button backButton;

	public InstuctionPage(StartPage startPage) {
		createPage();

		backButton = new Button("Back");
		backButton.setFocusTraversable(false);

		setButtonAction();
		this.setSpacing(16);
		this.setPadding(new Insets(50));
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, null, null)));
		this.getChildren().add(backButton);
		this.startPage = startPage;
	}

	@Override
	public void setButtonAction() {
		backButton.setOnAction(e -> {
			startPage.setInstructionAppear(false);
		});
	}
	
	private void createPage() {
		Label headerLabel = new Label("INSTRUCTION");
	
		String aimOfTheGameContentString = "Blow up your opponents before they kill you.";
		VBox aimOfTheGame = new VBox();
		Label aimOfGameHeader = new Label("AIM OF THE GAME");
		Label aimOfGameContentLabel = new Label(aimOfTheGameContentString);
		aimOfGameContentLabel.setWrapText(true);
		aimOfTheGame.getChildren().addAll(aimOfGameHeader, aimOfGameContentLabel);
		
		VBox keyBox = new VBox();
		Label keyBoxHeader = new Label("KEY");
		keyBox.getChildren().addAll(keyBoxHeader);
		
		GridPane ItemBox = new GridPane();
		
		
		this.getChildren().addAll(headerLabel, aimOfTheGame, keyBox, ItemBox);
		
		
	}
}
