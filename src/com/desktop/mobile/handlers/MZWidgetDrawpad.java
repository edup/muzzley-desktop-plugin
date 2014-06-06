package com.desktop.mobile.handlers;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.desktop.gui.layout.LayoutHelper;
import com.desktop.gui.layout.components.InputMethodKeyboard;
import com.desktop.gui.layout.components.InputMethodMouse;
import com.desktop.gui.layout.components.Key;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetDrawpad extends MZWidgetHandler{
	JComboBox combo_tap;

	double screen_width=0;
	double screen_height=0;
	
	
	public MZWidgetDrawpad(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screen_width = screenSize.getWidth();
		screen_height = screenSize.getHeight();
		
		try{
			Thread.sleep(3000);
		}catch(Exception e){}
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub
		//int button_tap = ((Key)combo_tap.getSelectedItem()).getValue();
		
		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			JsonObject value = new Gson().fromJson(data.v.toString(), JsonObject.class);

			Double new_x = value.get("x").getAsDouble();
			Double new_y = value.get("y").getAsDouble();
			
			if(event.compareTo("touchBegin")==0){
				robot.touchEvent(0);
				robot.mouseMove((int)(new_x*screen_width), (int)(new_y*screen_height));
				//robot.mousePress(0);
			}else
			if(event.compareTo("touchMove")==0){				
				robot.mouseMove((int)(new_x*screen_width), (int)(new_y*screen_height));				
			}else	
			if(event.compareTo("touchEnd")==0){
				//robot.mouseRelease(0);
				robot.touchEvent(1);
			}else
			if(event.compareTo("press")==0){
				robot.mousePress(1);				
				robot.mouseRelease(1);
			}	
	
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
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
