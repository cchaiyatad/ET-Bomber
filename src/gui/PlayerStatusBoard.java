package gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import player.Player;
import setting.Setting;

public class PlayerStatusBoard extends VBox {
	private ImageView playerImageView;
	private ImageView itemImageView;
	private Label scoreLabel;
	private Label hpLabel;
	private Player player;

	public PlayerStatusBoard() {
		this.setPrefSize(200, 160);
		this.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, null, null)));

		playerImageView = new ImageView(new Image(Setting.PATH_TO_PLACEHOLDER, 130, 130, false, false));
		HBox imagePane = new HBox();
		imagePane.resize(130, 130);
		imagePane.getChildren().add(playerImageView);

		itemImageView = new ImageView(new Image(Setting.PATH_TO_PLACEHOLDER, 70, 70, false, false));
		hpLabel = new Label("HP");
		hpLabel.setPrefHeight(60);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(hpLabel, itemImageView);

		HBox topHBox = new HBox();
		topHBox.getChildren().addAll(imagePane, vbox);

		scoreLabel = new Label("Score");
		scoreLabel.setPrefHeight(30);

		this.getChildren().addAll(topHBox, scoreLabel);
	}

	public void linkToPlayer(Player player) {
		this.player = player;
		upDateStatus();
	}

	public void upDateStatus() {
		scoreLabel.setText(String.valueOf(player.getScore()));
		hpLabel.setText(String.valueOf(player.getHp()));
		String imagePath = "";
		switch (player.getCurrentWeapon()) {
		case BOMB:
			imagePath = "bomb";
			break;
		case BOMBDETONATER:
			break;
		case GRENADE:
			break;
		case LANDMINE:
			break;
		case POISONDART:
			break;
		case REMOTEBOMB:
			break;
		case ROCKETLAUNCHER:
			break;
		default:
			imagePath = "placeholder";
			break;
		}

		itemImageView.setImage(new Image("file:res/Image/" + imagePath + ".png", 70, 70, false, false));
		playerImageView.setImage(new Image(player.getImagePath(), 130, 130, false, false));
	}

}
