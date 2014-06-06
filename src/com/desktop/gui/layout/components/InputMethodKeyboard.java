package com.desktop.gui.layout.components;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InputMethodKeyboard {

	public InputMethodKeyboard() {
		// TODO Auto-generated constructor stub
	}
	
	public static Key[] getKeyboardArray(){
		ArrayList<Key> keyboard = new ArrayList<Key>();
		 
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD0, Integer.toString(0)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD1, Integer.toString(1)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD2, Integer.toString(2)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD3, Integer.toString(3)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD4, Integer.toString(4)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD5, Integer.toString(5)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD6, Integer.toString(6)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD7, Integer.toString(7)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD8, Integer.toString(8)));
		 keyboard.add(new Key(KeyEvent.VK_NUMPAD9, Integer.toString(9)));
		 
		 for(int i=65; i!=91; i++){
			 char c = (char) i;  
			 keyboard.add(new Key(i, Character.toString(c)));
		 }
		 
		 keyboard.add(new Key(-1, "- Not Defined -"));
		 keyboard.add(new Key(KeyEvent.VK_DELETE, "DELETE"));
		 keyboard.add(new Key(KeyEvent.VK_SPACE, "SPACE"));
		 keyboard.add(new Key(KeyEvent.VK_ENTER, "ENTER")); 
		 keyboard.add(new Key(KeyEvent.VK_ALT, "ALT"));
		 keyboard.add(new Key(KeyEvent.VK_CONTROL, "CONTROL"));
		 keyboard.add(new Key(KeyEvent.VK_UP, "UP ARROW"));
		 keyboard.add(new Key(KeyEvent.VK_ESCAPE, "ESCAPE"));
		 keyboard.add(new Key(KeyEvent.VK_DOWN, "DOWN ARROW")); 
		 keyboard.add(new Key(KeyEvent.VK_LEFT, "LEFT ARROW"));
		 keyboard.add(new Key(KeyEvent.VK_RIGHT, "RIGHT ARROW"));
		 
		 Key [] keyboardArray = new Key[keyboard.size()];
		 keyboard.toArray(keyboardArray);
		
		return keyboardArray;
	}

}
