package gamecontroller;

import gui.StartPage;
import javafx.scene.Scene;
import setting.Setting;

public class StartPageController {

	public Scene createStartScene() {
		StartPage startPage = new StartPage();
		Scene scene = new Scene(startPage, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		return scene;
	}
	
}
