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

		String aimOfTheGameContentString = "1. Blow up your opponents before they kill you.\n"
				+ "2. Blow up your opponents before time out.\n" + "3. Avoid your opponents minions at all cost.\n"
				+ "4. Bomb can damage not only opponents but yourself too, so look before you leap.";
		VBox aimOfTheGame = new VBox();
		Label aimOfGameHeader = new Label("RULE");
		Label aimOfGameContentLabel = new Label(aimOfTheGameContentString);
		aimOfGameContentLabel.setWrapText(true);
		aimOfTheGame.getChildren().addAll(aimOfGameHeader, aimOfGameContentLabel);

		String keyString = "W : Move up\n" + "A : Move Left\n" + "S : Move Down\n" + "D : Move Right\n"
				+ "Space : place bomb";
		VBox keyBox = new VBox();
		Label keyBoxHeader = new Label("KEY");
		Label keyContentLabel = new Label(keyString);
		keyContentLabel.setWrapText(true);
		keyBox.getChildren().addAll(keyBoxHeader, keyContentLabel);

		GridPane legendBox = createLegendBox();

		this.getChildren().addAll(headerLabel, aimOfTheGame, keyBox, legendBox);
	}

	private GridPane createLegendBox() {
		GridPane legendBox = new GridPane();
		Label legendBoxHeader = new Label("LEGEND");

		ImageView playerImage = createImageView("playerOne");
		Label playerImageLabel = new Label("Player or opponents");
		playerImageLabel.setWrapText(true);

		ImageView minionImage = createImageView("minionOne");
		Label minionImageLabel = new Label("Minion of enemy");
		minionImageLabel.setWrapText(true);

		ImageView obstacleImage = createImageView("obstacle");
		Label obstacleImageLabel = new Label("An obstacle ,destroy obstacle to get an item");
		obstacleImageLabel.setWrapText(true);

		ImageView wallImage = createImageView("wall");
		Label wallImageLabel = new Label("A wall, you cannot destroy it");
		wallImageLabel.setWrapText(true);

		ImageView bombImage = createImageView("bomb");
		Label bombImageLabel = new Label("A bomb");
		bombImageLabel.setWrapText(true);

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

		legendBox.add(playerImage, 0, 1);
		legendBox.add(playerImageLabel, 1, 1);
		legendBox.add(minionImage, 0, 2);
		legendBox.add(minionImageLabel, 1, 2);
		legendBox.add(obstacleImage, 0, 3);
		legendBox.add(obstacleImageLabel, 1, 3);
		legendBox.add(wallImage, 0, 4);
		legendBox.add(wallImageLabel, 1, 4);
		legendBox.add(bombImage, 0, 5);
		legendBox.add(bombImageLabel, 1, 5);

		legendBox.add(bombUpgradeImage, 2, 1);
		legendBox.add(bombUpgradeLabel, 3, 1);
		legendBox.add(powerImage, 2, 2);
		legendBox.add(powerImageLabel, 3, 2);
		legendBox.add(speedImage, 2, 3);
		legendBox.add(speedImageLabel, 3, 3);
		legendBox.add(lifeImage, 2, 4);
		legendBox.add(lifeImageLabel, 3, 4);
		legendBox.add(shieldImage, 2, 5);
		legendBox.add(shieldImageLabel, 3, 5);

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
