package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller {
	protected Stage stage;
	protected Scene scene;
	protected Controller otherController;
		
	protected abstract Scene createScene();
	
	public Scene getScene() {
		if(scene == null) {
			return createScene();
		}
		return scene;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Controller getOtherController() {
		return otherController;
	}

	public void setOtherController(Controller otherController) {
		this.otherController = otherController;
	}
	
}
