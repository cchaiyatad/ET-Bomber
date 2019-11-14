package gameobject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameObject extends Pane {
	private int xPosition;
	private int yPosition;
	private String imagePath;
	private ImageView imageView;
	private Image image;
	
	public GameObject(){
		setxPosition(0);
		setyPosition(0);
		setImagePath("");
		image = new Image(getImagePath());
		imageView = new ImageView(image);
	}
	
	public GameObject(int xPosition, int yPosition, String imagePath){
		super();
		setxPosition(xPosition);
		setyPosition(yPosition);
		setImagePath(imagePath);
		image = new Image(getImagePath());
		imageView = new ImageView(image);
	}
	
	public void SetPosition() {
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
		this.imagePath = "res/Image/placeholder.PNG";
	}
}