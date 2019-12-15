package setting;

import javafx.scene.input.KeyCode;

public class Setting {
	//Padding 20
	public static double SCENE_WIDTH 	= 790;
	public static double SCENE_HEIGHT 	= 860;
	
	public static String PATH_TO_PLACEHOLDER = ClassLoader.getSystemResource("image/placeholder.png").toString();
	public static int GAME_TIME = 150;
	
	//Input
	public static KeyCode MOVEUP_KEY 		= KeyCode.W;
	public static KeyCode MOVEDOWN_KEY  	= KeyCode.S;
	public static KeyCode MOVELEFT_KEY  	= KeyCode.A;
	public static KeyCode MOVERIGHT_KEY  	= KeyCode.D;
	public static KeyCode PLACEBOMB_KEY  	= KeyCode.SPACE;
}
