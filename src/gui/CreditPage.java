package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

public class CreditPage extends VBox implements HasButtonPage {
	private StartPage startPage;
	private Button backButton;
	
	public CreditPage(StartPage startPage) {

		backButton = new Button("Back");
		backButton.setFocusTraversable(false);
		backButton.setFont(Font.loadFont(Main.fontpath, 14));
		
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
			startPage.setPageAppear(this, false);
		});
	}

}
