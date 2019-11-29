package setting;

import javafx.scene.input.KeyCode;

public class Setting {
	public static double SCENE_WIDTH 	= 1010;
	public static double SCENE_HEIGHT 	= 790;
	
	public static String PATH_TO_PLACEHOLDER = "file:res/Image/placeholder.png";
	public static int GAME_TIME = 65;
	
	//Input
	public static KeyCode PLAYERONE_MOVEUP 		= KeyCode.W;
	public static KeyCode PLAYERONE_MOVEDOWN  	= KeyCode.S;
	public static KeyCode PLAYERONE_MOVELEFT  	= KeyCode.A;
	public static KeyCode PLAYERONE_MOVERIGHT  	= KeyCode.D;
	public static KeyCode PLAYERONE_PLACEBOMB  	= KeyCode.SPACE;
	
	public static KeyCode PLAYERTWO_MOVEUP 		= KeyCode.UP;
	public static KeyCode PLAYERTWO_MOVEDOWN  	= KeyCode.DOWN;
	public static KeyCode PLAYERTWO_MOVELEFT  	= KeyCode.LEFT;
	public static KeyCode PLAYERTWO_MOVERIGHT  	= KeyCode.RIGHT;
	public static KeyCode PLAYERTWO_PLACEBOMB  	= KeyCode.BACK_SLASH;
}
