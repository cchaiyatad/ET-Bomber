package weapon;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombArea {

	// field--------------------------------------------------------
	private boolean[] isCanShowTop;
	private boolean[] isCanShowBot;
	private boolean[] isCanShowLeft;
	private boolean[] isCanShowRight;
	private ArrayList<ImageView> allImageViews = new ArrayList<ImageView>();
	private String imagePath;
	private Bomb bomb;
	private int range;
	private int xPos;
	private int yPos;

	public BombArea(Bomb bomb) {
		setBomb(bomb);
		xPos = bomb.getxPosition() / 50;
		yPos = bomb.getyPosition() / 50;
		imagePath = "file:res/Image/bombArea.png";
		setRange(bomb.getDamageRange());
		isCanShowTop = new boolean[getRange()];
		isCanShowBot = new boolean[getRange()];
		isCanShowLeft = new boolean[getRange()];
		isCanShowRight = new boolean[getRange()];
	}

	public void showRange() {
		setIsCanShowRange();
		showRangeBySite(Site.BOT);
		showRangeBySite(Site.TOP);
		showRangeBySite(Site.RIGHT);
		showRangeBySite(Site.LEFT);
		ImageView imageView = new ImageView(new Image(imagePath));
		bomb.getLayer().getChildren().add(imageView);
		imageView.relocate(xPos * 50, yPos * 50);
		allImageViews.add(imageView);
	}

	public void removeRange() {
		for (ImageView x : allImageViews) {
			bomb.getLayer().getChildren().remove(x);
		}

	}

	public boolean[] setIsCanShowBySite(Site site) {
		boolean[] isCanShow = new boolean[getRange()];
		for (int j = 0; j < getRange(); j++) {
			switch (site) {
			case TOP:
				isCanShow[j] = bomb.getGameController().canSetObject(xPos, Math.max(yPos - j - 1, 0));
				break;
			case BOT:
				isCanShow[j] = bomb.getGameController().canSetObject(xPos, Math.min(yPos + j + 1, 14));
				break;
			case LEFT:
				isCanShow[j] = bomb.getGameController().canSetObject(Math.max(xPos - j - 1, 0), yPos);
				break;
			case RIGHT:
				isCanShow[j] = bomb.getGameController().canSetObject(Math.min(xPos + j + 1, 14), yPos);
				break;
			}
			if(!isCanShow[j]) break;
		}
		return isCanShow;
	}

	public void setIsCanShowRange() {
		isCanShowTop = setIsCanShowBySite(Site.TOP);
		isCanShowBot = setIsCanShowBySite(Site.BOT);
		isCanShowLeft = setIsCanShowBySite(Site.LEFT);
		isCanShowRight = setIsCanShowBySite(Site.RIGHT);
		
	}

	public void showRangeBySite(Site site) {
		switch (site) {
		case TOP:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowTop[j]) {
					ImageView imageView = new ImageView(new Image(imagePath));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(xPos * 50, Math.max(yPos - j - 1, 0) * 50);
					allImageViews.add(imageView);
				}
				else break;
			}
			break;
		case BOT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowBot[j]) {
					ImageView imageView = new ImageView(new Image(imagePath));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(xPos * 50, Math.min(yPos + j + 1, 14) * 50);
					allImageViews.add(imageView);
				}
				else break;
			}
			break;
		case LEFT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowLeft[j]) {
					ImageView imageView = new ImageView(new Image(imagePath));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(Math.max(xPos - j - 1, 0) * 50, yPos * 50);
					allImageViews.add(imageView);
				}
				else break;
			}
			break;
		case RIGHT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowRight[j]) {
					ImageView imageView = new ImageView(new Image(imagePath));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(Math.min(xPos + j + 1, 14) * 50, yPos * 50);
					allImageViews.add(imageView);
				}
				else break;
			}
			break;
		}
	}


	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public Bomb getBomb() {
		return bomb;
	}

	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}

	public boolean[] getIsCanShowTop() {
		return isCanShowTop;
	}

	public boolean[] getIsCanShowBot() {
		return isCanShowBot;
	}

	public boolean[] getIsCanShowLeft() {
		return isCanShowLeft;
	}

	public boolean[] getIsCanShowRight() {
		return isCanShowRight;
	}
	
	

}
