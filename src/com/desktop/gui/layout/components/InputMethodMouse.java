package com.desktop.gui.layout.components;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InputMethodMouse {

	public InputMethodMouse() {
		// TODO Auto-generated constructor stub
	}
	
	public static Key[] getMouseArray(){
		ArrayList<Key> mouse = new ArrayList<Key>();
		 

		 mouse.add(new Key(-1, "- Not Defined -"));
		 mouse.add(new Key(MouseEvent.BUTTON1, "Mouse Button 1"));
		 mouse.add(new Key(MouseEvent.BUTTON2, "Mouse Button 2"));
		 mouse.add(new Key(MouseEvent.BUTTON3, "Mouse Button 3"));

		 
		 
		 Key [] mouseArray = new Key[mouse.size()];
		 mouse.toArray(mouseArray);
		
		return mouseArray;
	}

}
