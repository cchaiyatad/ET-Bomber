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
import javafx.scene.text.Font;
import main.Main;

public class InstuctionPage extends VBox implements HasButtonPage {
	private StartPage startPage;
	private Button backButton;

	public InstuctionPage(StartPage startPage) {
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
			startPage.setInstructionAppear(false);
		});
	}

	private void createPage() {
		Background bg = new Background(new BackgroundFill(Color.LIGHTBLUE, null, null));
		Label headerLabel = new Label(" INSTRUCTION ");
		headerLabel.setFont(Font.loadFont(Main.fontpath, 30));
		headerLabel.setBackground(bg);
		
		String aimOfTheGameContentString = "1. Blow up your opponents before they kill you.\n"
				+ "2. Blow up your opponents before time out.\n" + "3. Avoid your opponents minions at all cost.\n"
				+ "4. Bomb can damage not only opponents but yourself too, so look before you leap.";
		VBox aimOfTheGame = new VBox();
		aimOfTheGame.setSpacing(10);
		
		Label aimOfGameHeader = new Label(" RULE ");
		aimOfGameHeader.setFont(Font.loadFont(Main.fontpath, 20));
		aimOfGameHeader.setBackground(bg);
		
		Label aimOfGameContentLabel = new Label(aimOfTheGameContentString);
		aimOfGameContentLabel.setFont(Font.loadFont(Main.fontpath, 14));
		aimOfGameContentLabel.setWrapText(true);
		aimOfTheGame.getChildren().addAll(aimOfGameHeader, aimOfGameContentLabel);

		String keyString = "W : Move Up\n" + "A : Move Left\n" + "S : Move Down\n" + "D : Move Right\n"
				+ "Space : Place bomb";
		VBox keyBox = new VBox();
		keyBox.setSpacing(10);
		
		Label keyBoxHeader = new Label(" KEY ");
		keyBoxHeader.setFont(Font.loadFont(Main.fontpath, 20));
		keyBoxHeader.setBackground(bg);
		
		Label keyContentLabel = new Label(keyString);
		keyContentLabel.setFont(Font.loadFont(Main.fontpath, 14));
		keyContentLabel.setWrapText(true);
		
		Label legendBoxHeader = new Label(" LEGEND ");
		legendBoxHeader.setFont(Font.loadFont(Main.fontpath, 20));
		legendBoxHeader.setBackground(bg);
		
		keyBox.getChildren().addAll(keyBoxHeader, keyContentLabel,legendBoxHeader);

		GridPane legendBox = createLegendBox();

		this.getChildren().addAll(headerLabel, aimOfTheGame, keyBox, legendBox);
	}

	private GridPane createLegendBox() {
		GridPane legendBox = new GridPane();
		legendBox.setVgap(10);
		legendBox.setHgap(5);
		
		ImageView playeroneImage = createImageView("playerOne");
		Label playeroneImageLabel = new Label("Player");
		playeroneImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		playeroneImageLabel.setWrapText(true);
		
		ImageView playertwoImage = createImageView("playerTwo");
		Label playertwoImageLabel = new Label("Simple Monster. Just summon minions.");
		playertwoImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		playertwoImageLabel.setWrapText(true);
		
		ImageView playerthreeImage = createImageView("playerThree");
		Label playerthreeImageLabel = new Label("More advanced Monster. Can pass though the wall.");
		playerthreeImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		playerthreeImageLabel.setWrapText(true);
		
		ImageView playerfourImage = createImageView("playerFour");
		Label playerfourImageLabel = new Label("The most advanced Monster. Can invisible for a while.");
		playerfourImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		playerfourImageLabel.setWrapText(true);

		ImageView minionImage = createImageView("minion");
		Label minionImageLabel = new Label("Minion of enemy");
		minionImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		minionImageLabel.setWrapText(true);

		ImageView obstacleImage = createImageView("obstacle");
		Label obstacleImageLabel = new Label("An obstacle ,destroy obstacle to get an item");
		obstacleImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		obstacleImageLabel.setWrapText(true);

		ImageView wallImage = createImageView("wall");
		Label wallImageLabel = new Label("A wall, you cannot destroy it");
		wallImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		wallImageLabel.setWrapText(true);

		ImageView bombImage = createImageView("bomb");
		Label bombImageLabel = new Label("A bomb");
		bombImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		bombImageLabel.setWrapText(true);

		ImageView bombUpgradeImage = createImageView("bombItem");
		Label bombUpgradeLabel = new Label("Increse number of bomb that you can place by one");
		bombUpgradeLabel.setFont(Font.loadFont(Main.fontpath, 14));
		bombUpgradeLabel.setWrapText(true);

		ImageView powerImage = createImageView("powerItem");
		Label powerImageLabel = new Label("Increse power of bomb by one square");
		powerImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		powerImageLabel.setWrapText(true);

		ImageView speedImage = createImageView("speedItem");
		Label speedImageLabel = new Label("Increse running speed by one");
		speedImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		speedImageLabel.setWrapText(true);

		ImageView lifeImage = createImageView("lifeItem");
		Label lifeImageLabel = new Label("Increse HP by one");
		lifeImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		lifeImageLabel.setWrapText(true);

		ImageView shieldImage = createImageView("shield");
		Label shieldImageLabel = new Label("Make player invulnerable to damage 5 second");
		shieldImageLabel.setFont(Font.loadFont(Main.fontpath, 14));
		shieldImageLabel.setWrapText(true);

		legendBox.addRow(2, playeroneImage,playeroneImageLabel,playertwoImage,playertwoImageLabel);
		legendBox.addRow(3, playerthreeImage,playerthreeImageLabel,playerfourImage,playerfourImageLabel);
		legendBox.addRow(4, minionImage,minionImageLabel,powerImage,powerImageLabel);
		legendBox.addRow(5, obstacleImage,obstacleImageLabel,speedImage,speedImageLabel);
		legendBox.addRow(6, wallImage,wallImageLabel,lifeImage,lifeImageLabel);
		legendBox.addRow(7, bombUpgradeImage,bombUpgradeLabel,shieldImage,shieldImageLabel);
		legendBox.addRow(8, bombImage,bombImageLabel);
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
