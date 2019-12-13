package gameobject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameObject {
	protected int xPosition;
	protected int yPosition;
	protected String imagePath;
	protected ImageView imageView;
	protected Image image;
	protected Pane layer;

	public GameObject(int xPosition, int yPosition, String imagePath, Pane layer) {
		setxPosition(xPosition);
		setyPosition(yPosition);
		setImagePath(imagePath);
		image = new Image(getImagePath());
		imageView = new ImageView(image);
		this.layer = layer;
		this.layer.getChildren().add(this.imageView);
		SetPositionOnScreen();
	}

	public void SetPositionOnScreen() {
		imageView.relocate(getxPosition(), getyPosition());
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath.equals("") ? "file:res/Image/placeholder.png"
				: "file:res/Image/" + imagePath + ".png";
	}

	@Override
	public String toString() {
		return imagePath;
	}

	public Pane getLayer() {
		return layer;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageShow(boolean value) {
		if (value) {
			this.layer.getChildren().add(this.imageView);
		} else {
			this.layer.getChildren().remove(this.imageView);
		}
	}

}