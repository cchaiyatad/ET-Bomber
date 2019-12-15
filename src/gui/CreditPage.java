package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

public class CreditPage extends VBox implements HasButtonPage {
	private StartPage startPage;
	private Button backButton;

	public CreditPage(StartPage startPage) {
		createPage();

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

	private void createPage() {
		Label headerlabel = new Label(" Credit ");
		headerlabel.setFont(Font.loadFont(Main.fontpath, 30));
		headerlabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

		ArrayList<String> names = new ArrayList<String>();
		names.add("Freepik");
		names.add("Skyclick");
		names.add("surang");
		names.add("Smashicons");
		names.add("Vectors Market");

		this.getChildren().add(headerlabel);

		for (int i = 0; i < 5; i++) {
			Label firstword = new Label("Icon made by");
			Label from = new Label("from");
			Label web = new Label("www.flaticon.com");

			firstword.setFont(Font.loadFont(Main.fontpath, 18));
			from.setFont(Font.loadFont(Main.fontpath, 18));
			web.setFont(Font.loadFont(Main.fontpath, 18));

			web.setTextFill(Color.LIGHTSKYBLUE);
			HBox hbox = new HBox(10);
			hbox.setAlignment(Pos.CENTER);
			hbox.getChildren().addAll(firstword, createAuthorLabel(names.get(i)), from, web);

			this.getChildren().add(hbox);

		}

	}

	private Label createAuthorLabel(String name) {
		Label author = new Label(name);
		author.setFont(Font.loadFont(Main.fontpath, 18));
		author.setTextFill(Color.LIGHTSKYBLUE);
		return author;
	}

}
