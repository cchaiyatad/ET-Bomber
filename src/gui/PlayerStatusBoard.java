package gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import player.PlayerBase;

public class PlayerStatusBoard extends HBox {
	private ImageView playerImageView;
	private Label hpLabel;
	private PlayerBase player;

	public PlayerStatusBoard() {
		String fontpath = ClassLoader.getSystemResource("font/PixelEmulator-xq08.ttf").toString();
		this.setPrefSize(100, 50);
		this.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, null, null)));

		playerImageView = new ImageView();
		

		hpLabel = new Label();
		hpLabel.setPrefHeight(60);
		hpLabel.setFont(Font.loadFont(fontpath, 16));

		this.getChildren().addAll(playerImageView, hpLabel);
	}

	public void linkToPlayer(PlayerBase player) {
		this.player = player;
		playerImageView.setImage(new Image(player.getImagePath()));
		updateStatus();
	}

	public void updateStatus() {
		if (player == null) {
			return;
		}
		hpLabel.setText(String.format("HP:%d", player.getHp()));
		if(player.getHp() == 0){
			hpLabel.setText("");
			playerImageView.setImage(null);
			
		}
	}

}
