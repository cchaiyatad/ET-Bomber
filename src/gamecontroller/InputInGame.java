package gamecontroller;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputInGame {
	
	private Scene scene;
	private BitSet keyboardBitSet = new BitSet();
	
	public InputInGame(Scene scene) {
		this.scene = scene;
	}
	
	public void AddListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, KeyOnPressHandle);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, KeyOnReleaseHandle);
	}
	
	public void RemoveListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, KeyOnPressHandle);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, KeyOnReleaseHandle);
	}
	
	public boolean IsKeyPress(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}
	
	private EventHandler<KeyEvent> KeyOnPressHandle = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
            keyboardBitSet.set(e.getCode().ordinal(), true);
		}
	};
	
	private EventHandler<KeyEvent> KeyOnReleaseHandle = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
			keyboardBitSet.set(e.getCode().ordinal(), false);
		}
	};

}
