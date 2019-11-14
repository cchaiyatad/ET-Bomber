package gameobject;

public abstract class GameObject {
	private int xPosition;
	private int yPosition;
	private String imagePath;
	
	public GameObject(){
		setxPosition(0);
		setyPosition(0);
		setImagePath("");
	}
	
	public GameObject(int xPosition, int yPosition, String imagePath){
		setxPosition(xPosition);
		setyPosition(yPosition);
		setImagePath(imagePath);
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
		this.imagePath = imagePath;
	}
}