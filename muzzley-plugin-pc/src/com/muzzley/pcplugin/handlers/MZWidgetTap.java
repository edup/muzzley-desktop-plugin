package com.muzzley.pcplugin.handlers;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.muzzley.osplugin.MuzzRobot;
import com.muzzley.pcplugin.layout.LayoutHelper;
import com.muzzley.pcplugin.layout.components.InputMethodKeyboard;
import com.muzzley.pcplugin.layout.components.Key;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetTap extends MZWidgetHandler{
	JComboBox<Key> combo_tap;
		
	
	public static int martelada_counter=0; //Isto é uma martelada para saber se é o user 1 ou 2 que entra porque ainda não temos mapeamento de keys
	

	public MZWidgetTap(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		martelada_counter++;
		System.out.println("HELLO: I'm in swipe");
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub		
		int button_tap = ((Key)combo_tap.getSelectedItem()).getValue();
	
		
		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			
			if(component.compareTo("button")==0 && event.compareTo("release")==0){
				robot.keyEvent(button_tap, 1);
				robot.keyEvent(button_tap, 2);						
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public JPanel getWidgetPanel(){		
		JPanel panel = LayoutHelper.getJPanel();
		Key [] keyboardArray = InputMethodKeyboard.getKeyboardArray();
		 
		int s_tap;
		s_tap=KeyEvent.VK_SPACE;
		 
		combo_tap = LayoutHelper.createGuiComboKeyItem(panel, "Tap", s_tap, keyboardArray);
	
		
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		return panel;		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	

}
