package weapon;

import java.util.ArrayList;

import controller.ObjectInGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombArea {

	// field--------------------------------------------------------
	private boolean[] isCanShowTop;
	private boolean[] isCanShowBot;
	private boolean[] isCanShowLeft;
	private boolean[] isCanShowRight;
	private int[] afterDestroy;
	private ArrayList<ImageView> allImageViews = new ArrayList<ImageView>();
	private Bomb bomb;
	private int range;
	private int xPos;
	private int yPos;

	public BombArea(Bomb bomb) {
		setBomb(bomb);
		xPos = bomb.getxPosition() / 50;
		yPos = bomb.getyPosition() / 50;
		setRange(bomb.getDamageRange());
		isCanShowTop = new boolean[getRange()];
		isCanShowBot = new boolean[getRange()];
		isCanShowLeft = new boolean[getRange()];
		isCanShowRight = new boolean[getRange()];
		afterDestroy = new int[4];
		for(int i = 0; i<4;i++) {
			afterDestroy[i] = -1;
		}
	}

	public void showRange() {
		showRangeBySite(Site.BOT);
		showRangeBySite(Site.TOP);
		showRangeBySite(Site.RIGHT);
		showRangeBySite(Site.LEFT);
		ImageView imageView = new ImageView(new Image(ClassLoader.getSystemResource("image/bombAreaC.png").toString()));
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
		for(int i = 0; i < getRange(); i++) {
			isCanShow[i] = false;
		}
		for (int j = 0; j < getRange(); j++) {
			if(site == Site.TOP) isCanShow[j] = bomb.getGameController().canSetObject(xPos, Math.max(yPos - j - 1, 0));
			else if (site == Site.BOT) isCanShow[j] = bomb.getGameController().canSetObject(xPos, Math.min(yPos + j + 1, 14));
			else if (site == Site.LEFT) isCanShow[j] = bomb.getGameController().canSetObject(Math.max(xPos - j - 1, 0), yPos);
			else if (site == Site.RIGHT) isCanShow[j] = bomb.getGameController().canSetObject(Math.min(xPos + j + 1, 14), yPos);
			if(!isCanShow[j]) {
				if(site == Site.TOP && bomb.getGameController().getObjectInGame(xPos, Math.max(yPos - j - 1, 0))!= ObjectInGame.WALL) afterDestroy[0] = j;
				else if (site == Site.BOT && bomb.getGameController().getObjectInGame(xPos, Math.min(yPos + j + 1, 14))!= ObjectInGame.WALL) afterDestroy[1] = j;
				else if (site == Site.LEFT && bomb.getGameController().getObjectInGame(Math.max(xPos - j - 1, 0), yPos)!= ObjectInGame.WALL) afterDestroy[2] = j;
				else if (site == Site.RIGHT && bomb.getGameController().getObjectInGame(Math.min(xPos + j + 1, 14), yPos)!= ObjectInGame.WALL) afterDestroy[3] = j;
				break;
			}
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
		String imagePathTB = ClassLoader.getSystemResource("image/bombArea2.png").toString();
		String imagePathLR = ClassLoader.getSystemResource("image/bombArea1.png").toString();
		switch (site) {
		case TOP:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowTop[j]) {
					ImageView imageView = new ImageView(new Image(imagePathTB));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(xPos * 50, Math.max(yPos - j - 1, 0) * 50);
					allImageViews.add(imageView);
				}
				else {break;}
			}
			break;
		case BOT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowBot[j]) {
					ImageView imageView = new ImageView(new Image(imagePathTB));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(xPos * 50, Math.min(yPos + j + 1, 14) * 50);
					allImageViews.add(imageView);
				}
				else {break;}
			}
			break;
		case LEFT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowLeft[j]) {
					ImageView imageView = new ImageView(new Image(imagePathLR));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(Math.max(xPos - j - 1, 0) * 50, yPos * 50);
					allImageViews.add(imageView);
				}
				else {break;}
			}
			break;
		case RIGHT:
			for (int j = 0; j < getRange(); j++) {
				if (isCanShowRight[j]) {
					ImageView imageView = new ImageView(new Image(imagePathLR));
					bomb.getLayer().getChildren().add(imageView);
					imageView.relocate(Math.min(xPos + j + 1, 14) * 50, yPos * 50);
					allImageViews.add(imageView);
				}
				else {break;}
			}
			break;
		}
	}
	public void setAfterDestroy() {
		if(afterDestroy[0] >= 0) isCanShowTop[afterDestroy[0]] = true;
		if(afterDestroy[1] >= 0) isCanShowBot[afterDestroy[1]] = true;
		if(afterDestroy[2] >= 0) isCanShowLeft[afterDestroy[2]] = true;
		if(afterDestroy[3] >= 0) isCanShowRight[afterDestroy[3]] = true;
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
