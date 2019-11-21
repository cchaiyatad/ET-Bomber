package controller;

import java.util.Random;

public class LevelGenerator {

	private final int obstacleRate = 85;
	private ObjectInGame[][] objectsArray = new ObjectInGame[15][15];
	private Random random;
	
	public LevelGenerator() {
		random = new Random();
	}

	public ObjectInGame[][] generateLevel() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if ((i == 0 || i == 14) || (i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && (j == 0 || j == 14))) {
					objectsArray[i][j] = ObjectInGame.WALL;
				} else if (((i == 1 || i == 2 || i == 12 || i == 13) && (j == 1 || j == 2 || j == 12 || j == 13))) {
					objectsArray[i][j] = ObjectInGame.EMPTY;
				} else {
					objectsArray[i][j] = ObjectInGame.EMPTY;
					if(random.nextInt(100) < obstacleRate) {
//						objectsArray[i][j] = ObjectInGame.EMPTYOBSTACLE;
						objectsArray[i][j] = ObjectInGame.LIFEINCREASEITEM;
					}
				}
			}
		}
//
//		for (int i = 0; i < 15; i++) {
//			for (int j = 0; j < 15; j++) {
//				System.out.print(objectsArray[i][j] + "\t");
//			}
//			System.out.println();
//		}
		return objectsArray;
	}

}
