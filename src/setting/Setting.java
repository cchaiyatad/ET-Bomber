package setting;

import javafx.scene.input.KeyCode;

public class Setting {
	//Padding 20
	public static double SCENE_WIDTH 	= 790;
	public static double SCENE_HEIGHT 	= 860;
	
	public static String PATH_TO_PLACEHOLDER = "file:res/Image/placeholder.png";
	public static int GAME_TIME = 120;
	
	//Input
	public static KeyCode PLAYERONE_MOVEUP 		= KeyCode.W;
	public static KeyCode PLAYERONE_MOVEDOWN  	= KeyCode.S;
	public static KeyCode PLAYERONE_MOVELEFT  	= KeyCode.A;
	public static KeyCode PLAYERONE_MOVERIGHT  	= KeyCode.D;
	public static KeyCode PLAYERONE_PLACEBOMB  	= KeyCode.SPACE;
}
