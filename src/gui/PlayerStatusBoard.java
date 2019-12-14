package gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import player.PlayerBase;

public class PlayerStatusBoard extends HBox {
	private ImageView playerImageView;
	private Label hpLabel;
	private PlayerBase player;

	public PlayerStatusBoard() {
		this.setPrefSize(100, 50);
		this.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, null, null)));

		playerImageView = new ImageView();

		hpLabel = new Label();
		hpLabel.setPrefHeight(60);

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

	}

}
