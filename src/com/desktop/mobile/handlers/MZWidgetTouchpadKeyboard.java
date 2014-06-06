package com.desktop.mobile.handlers;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.desktop.gui.layout.LayoutHelper;
import com.desktop.gui.layout.components.InputMethodKeyboard;
import com.desktop.gui.layout.components.InputMethodMouse;
import com.desktop.gui.layout.components.Key;
import com.desktop.tools.MyTools;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetTouchpadKeyboard extends MZWidgetHandler{
	JComboBox combo_tap;

	double screen_width=0;
	double screen_height=0;
	
	
	public MZWidgetTouchpadKeyboard(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screen_width = screenSize.getWidth();
		screen_height = screenSize.getHeight();
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub
		//int button_tap = ((Key)combo_tap.getSelectedItem()).getValue();
		
		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			
			System.out.println("Processing Message on Touchpad");
			System.out.println("Event: " + event);
			if(component.compareTo("touchpad")==0) {
				JsonObject value = new Gson().fromJson(data.v.toString(), JsonObject.class);
				
				if(event.compareTo("touchMove")==0){
					int x = value.get("x").getAsInt();
					int y = value.get("y").getAsInt();
					robot.mouseRawMove(x, y);
					//robot.mousePress(0);
				}else
				if(event.compareTo("tap")==0){
					System.out.println("TAP");
					robot.mousePress(1);
					MyTools.delay(20);
					robot.mouseRelease(1);
				}else	
				if(event.compareTo("longPress")==0){
					System.out.println("LONG PRESS");
					robot.mousePress(2);
					MyTools.delay(20);
					robot.mouseRelease(2);
				}
			}else
			if(component.compareTo("kb")==0) {
				if(event.compareTo("chars")==0){
					System.out.println("CHARS: " + data.v + ", " + data.v.getClass().getName());
					ArrayList value = (ArrayList)data.v;
					for (int i=0; i<value.size(); i++) {
						System.out.println("Get code: " + value.get(i));
					    int code = (int)(((String)value.get(i)).charAt(0));
					    System.out.println("EDU: Code is= " + code);
					    robot.keyPressRelease(((String)value.get(i)).charAt(0));
						System.out.println("Fim");
					} 
				}
				
			}
			
				
	
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem parsing: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	public JPanel getWidgetPanel(){
		JPanel panel = LayoutHelper.getJPanel();
		Key [] mouseArray = InputMethodMouse.getMouseArray();
		 
		int s_tap;
		s_tap=MouseEvent.BUTTON2;
		
		//combo_tap = LayoutHelper.createGuiComboKeyItem(panel, "Clean Button", s_tap, mouseArray);
	
		
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		return panel;	
	}
	

	public Participant getParticipant(){
		return participant;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
