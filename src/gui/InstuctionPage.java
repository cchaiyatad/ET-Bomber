package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
		
		GridPane legendBox = createLegendBox();
		
		this.getChildren().addAll(headerLabel, aimOfTheGame, keyBox, legendBox);
	}
	private GridPane createLegendBox() {
		GridPane legendBox = new GridPane();
		Label legendBoxHeader = new Label("LEGEND");
		
		ImageView bombUpgradeImage = createImageView("bombItem");
		Label bombUpgradeLabel = new Label("Increse number of bomb that you can place by one");
		bombUpgradeLabel.setWrapText(true);
		
		ImageView powerImage = createImageView("powerItem");
		Label powerImageLabel = new Label("Increse power of bomb by one square");
		powerImageLabel.setWrapText(true);
		
		ImageView speedImage = createImageView("speedItem");
		Label speedImageLabel = new Label("Increse running speed by one");
		speedImageLabel.setWrapText(true);
		
		ImageView lifeImage = createImageView("lifeItem");
		Label lifeImageLabel = new Label("Increse HP by one");
		lifeImageLabel.setWrapText(true);
		
		ImageView shieldImage = createImageView("shield");
		Label shieldImageLabel = new Label("Make player invulnerable to damage 5 second");
		shieldImageLabel.setWrapText(true);
		
		legendBox.add(legendBoxHeader, 0, 0);
		legendBox.add(bombUpgradeImage, 0, 1);
		legendBox.add(bombUpgradeLabel, 1, 1);
		legendBox.add(powerImage, 0, 2);
		legendBox.add(powerImageLabel, 1, 2);
		legendBox.add(speedImage, 0, 3);
		legendBox.add(speedImageLabel, 1, 3);
		legendBox.add(lifeImage, 0, 4);
		legendBox.add(lifeImageLabel, 1, 4);
		legendBox.add(shieldImage, 0, 5);
		legendBox.add(shieldImageLabel, 1, 5);
		
		return legendBox;
	}
	
	private ImageView createImageView(String imagePath) {
		String path = ClassLoader.getSystemResource(String.format("image/%s.png", imagePath)).toString();
		ImageView imageView = new ImageView(new Image(path));
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		imageView.setPreserveRatio(true);
		return imageView;
	}
}
