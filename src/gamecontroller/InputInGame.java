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
	
	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyOnPressHandle);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyOnReleaseHandle);
	}
	
	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyOnPressHandle);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyOnReleaseHandle);
	}
	
	public boolean isKeyPress(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}
	
	private EventHandler<KeyEvent> keyOnPressHandle = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
            keyboardBitSet.set(e.getCode().ordinal(), true);
		}
	};
	
	private EventHandler<KeyEvent> keyOnReleaseHandle = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent e) {
			keyboardBitSet.set(e.getCode().ordinal(), false);
		}
	};

}
