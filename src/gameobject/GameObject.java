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
	protected Pane Layer;
	
	public GameObject(int xPosition, int yPosition, String imagePath, Pane layer){
		setxPosition(xPosition);
		setyPosition(yPosition);
		setImagePath(imagePath);
		image = new Image(getImagePath());
		imageView = new ImageView(image);
		this.Layer = layer;
		this.Layer.getChildren().add(this.imageView);
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
//		this.imagePath = imagePath;
		this.imagePath = "file:res/Image/placeholder.png";
	}
	
}