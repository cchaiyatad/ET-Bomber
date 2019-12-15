package controller;

import gui.StartPage;
import javafx.scene.Scene;
import setting.Setting;

public class StartPageController extends Controller {

	@Override
	protected Scene createScene() {
		StartPage startPage = new StartPage(this);
		this.scene = new Scene(startPage, Setting.SCENE_WIDTH, Setting.SCENE_HEIGHT);
		return scene;
	}	
}
