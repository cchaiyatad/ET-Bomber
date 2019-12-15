package gameobject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameObject {
	protected Image image;
	protected ImageView imageView;
	protected String imagePath;
	protected Pane layer;
	protected int xPosition;
	protected int yPosition;

	public GameObject(int xPosition, int yPosition, String imagePath, Pane layer) {
		setxPosition(xPosition);
		setyPosition(yPosition);
		setImagePath(imagePath);
		image = new Image(getImagePath());
		imageView = new ImageView(image);
		this.layer = layer;
		setImageShow(true);
		SetPositionOnScreen();
	}

	public Pane getLayer() {
		return layer;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void SetPositionOnScreen() {
		imageView.relocate(getxPosition(), getyPosition());
	}

	@Override
	public String toString() {
		return imagePath;
	}

	public void setImageShow(boolean value) {
		if (value) {
			this.layer.getChildren().add(this.imageView);
		} else {
			this.layer.getChildren().remove(this.imageView);
		}
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath.equals("") ? ClassLoader.getSystemResource("image/placeholder.png").toString()
				: ClassLoader.getSystemResource(String.format("image/%s.png", imagePath)).toString();
	}

}