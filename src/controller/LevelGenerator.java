package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LevelGenerator {

	private final int obstacleRate = 103 - 20;
	private final int emptyTileCount = 121 + 20;
	private Random random = new Random();

	public ObjectInGame[][] generateLevel() {
		ObjectInGame[][] objectsArray = new ObjectInGame[15][15];
		int objectCount = random.nextInt(emptyTileCount - obstacleRate) + obstacleRate;
		List<ObjectInGame> suffleList = new ArrayList<ObjectInGame>();

		for (int k = 3; k < 8; k++) {
			int count;
			if (k == 3 || k == 4 || k == 5) {
				count = random.nextInt(4) + 12;
			} else {
				count = random.nextInt(2) + 3;
			}
			for (int l = 0; l < count; l++) {
				suffleList.add(ObjectInGame.values()[k]);
			}
		}
		while (suffleList.size() < objectCount) {
			suffleList.add(ObjectInGame.OBSTACLE);
		}
		while (suffleList.size() < emptyTileCount) {
			suffleList.add(ObjectInGame.EMPTY);
		}

		Collections.shuffle(suffleList);

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if ((i == 0 || i == 14) || (i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && (j == 0 || j == 14))) {
					objectsArray[i][j] = ObjectInGame.WALL;
				} else if (((i == 1 || i == 2 || i == 12 || i == 13) && (j == 1 || j == 2 || j == 12 || j == 13))) {
					objectsArray[i][j] = ObjectInGame.EMPTY;
				} else {
					objectsArray[i][j] = suffleList.get(0);
					suffleList.remove(0);
				}
			}
		}
		
		return objectsArray;
	}

}
