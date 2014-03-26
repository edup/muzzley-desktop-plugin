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


public class MZWidgetSwipeNavigator extends MZWidgetHandler{
	JComboBox<Key> combo_up;
	JComboBox<Key> combo_down;
	JComboBox<Key> combo_left;
	JComboBox<Key> combo_right;
	
	JComboBox<Key> combo_tap;
	JComboBox<Key> combo_bOk;
	JComboBox<Key> combo_bBack;
	
	//TEMPLATE MAPS
	static ArrayList<ArrayList> TEMPLATE_MAP = new ArrayList<ArrayList>();
	static{
		//CONF1
		ArrayList<Key> conf1=new ArrayList<Key>();
		conf1.add(new Key(KeyEvent.VK_DOWN, "down"));
		conf1.add(new Key(KeyEvent.VK_UP, "up"));
		conf1.add(new Key(KeyEvent.VK_LEFT, "left"));
		conf1.add(new Key(KeyEvent.VK_RIGHT, "right"));
		conf1.add(new Key(KeyEvent.VK_SPACE, "tap"));
		conf1.add(new Key(KeyEvent.VK_ENTER, "ok"));
		conf1.add(new Key(KeyEvent.VK_DELETE, "back"));
		TEMPLATE_MAP.add(conf1);
	}

	public MZWidgetSwipeNavigator(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub
		
		int button_up = ((Key)combo_up.getSelectedItem()).getValue();
		int button_down = ((Key)combo_down.getSelectedItem()).getValue();
		int button_left = ((Key)combo_left.getSelectedItem()).getValue();
		int button_right = ((Key)combo_right.getSelectedItem()).getValue();
		
		int button_tap = ((Key)combo_tap.getSelectedItem()).getValue();
		int button_ok = ((Key)combo_bOk.getSelectedItem()).getValue();
		int button_back = ((Key)combo_bBack.getSelectedItem()).getValue();
		
		
		
		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			
			if(event.compareTo("swipe")==0){
					String value = data.v.toString();
					System.out.println("Value: " + value);
					int key=0;
					if(value.compareTo("r")==0){
						//TURN RIGHT
						System.out.println("RIGHT; "+button_right);
						keyReset();
						robot.keyEvent(button_right, 1);
						robot.keyEvent(button_right, 2);
					}
					else
					if(value.compareTo("l")==0){
						//TURN LEFT
						System.out.println("LEFT: "+button_left);
						keyReset();
						robot.keyEvent(button_left, 1);
						robot.keyEvent(button_left, 2);
					}else
					if(value.compareTo("d")==0){
						//TURN DOWN
						System.out.println("DOWN: " + button_down);
						keyReset();
						robot.keyEvent(button_down, 1);
						robot.keyEvent(button_down, 2);
					}else
					if(value.compareTo("u")==0){
						//TURN UP
						System.out.println("UP: " + button_up);
						keyReset();
						robot.keyEvent(button_up, 1);
						robot.keyEvent(button_up, 2);
					}					
			}else
			if(event.compareTo("tap")==0){
				robot.keyEvent(button_tap, 1);
				robot.keyEvent(button_tap, 2);				
			}else
			if(event.compareTo("release")==0 && component.compareTo("bOk")==0){
				robot.keyEvent(button_ok, 1);
				robot.keyEvent(button_ok, 2);				
			}else
			if(event.compareTo("release")==0 && component.compareTo("bCancel")==0){
				robot.keyEvent(button_back, 1);
				robot.keyEvent(button_back, 2);				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void keyReset(){
		int button_up = ((Key)combo_up.getSelectedItem()).getValue();
		int button_down = ((Key)combo_down.getSelectedItem()).getValue();
		int button_left = ((Key)combo_left.getSelectedItem()).getValue();
		int button_right = ((Key)combo_right.getSelectedItem()).getValue();
		  
		robot.keyReset(button_up);
		robot.keyReset(button_down);
		robot.keyReset(button_left);
		robot.keyReset(button_right);
	}
	
	
	public JPanel getWidgetPanel(){
    JPanel main_panel = LayoutHelper.getJPanel(1);        
        
        //TEMPLATE PANEL
        JPanel template_panel = LayoutHelper.getJPanel(2);    
        JComboBox<Key> templateCombo=LayoutHelper.createGuiComboKeyItem(template_panel, "Template Map:", 0, LayoutHelper.getTemplateKeyMap(TEMPLATE_MAP));        
        templateCombo.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        int index = ((JComboBox)e.getSource()).getSelectedIndex();	        
		        
		        ArrayList<Key> templateMap = TEMPLATE_MAP.get(index);		
		        LayoutHelper.setComboDefault(combo_up, templateMap.get(0).getValue());
		        LayoutHelper.setComboDefault(combo_down, templateMap.get(1).getValue());
		        LayoutHelper.setComboDefault(combo_left, templateMap.get(2).getValue());
		        LayoutHelper.setComboDefault(combo_right, templateMap.get(3).getValue());
		        LayoutHelper.setComboDefault(combo_tap, templateMap.get(4).getValue());
		        LayoutHelper.setComboDefault(combo_bOk, templateMap.get(5).getValue());
		        LayoutHelper.setComboDefault(combo_bBack, templateMap.get(6).getValue());		        
			}
		});        
        main_panel.add(template_panel);
        
        //COMBOS PANEL
        JPanel panel = LayoutHelper.getJPanel(0);
        Key [] keyboardArray = InputMethodKeyboard.getKeyboardArray();
        ArrayList<Key> templateMap = (ArrayList<Key>) TEMPLATE_MAP.get(0); //GET DEFAULT MAP
        
        
        combo_up = LayoutHelper.createGuiComboKeyItem(panel, "UP", templateMap.get(0).getValue(), keyboardArray);
		combo_down = LayoutHelper.createGuiComboKeyItem(panel, "DOWN", templateMap.get(1).getValue(), keyboardArray);
		combo_left = LayoutHelper.createGuiComboKeyItem(panel, "LEFT", templateMap.get(2).getValue(), keyboardArray);
		combo_right = LayoutHelper.createGuiComboKeyItem(panel, "RIGHT", templateMap.get(3).getValue(), keyboardArray);
		
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		combo_tap = LayoutHelper.createGuiComboKeyItem(panel, "TAP", templateMap.get(4).getValue(), keyboardArray);
		combo_bOk = LayoutHelper.createGuiComboKeyItem(panel, "bOk", templateMap.get(5).getValue(), keyboardArray);
		combo_bBack = LayoutHelper.createGuiComboKeyItem(panel, "bBack", templateMap.get(6).getValue(), keyboardArray);
		
		//adding to main panel
		main_panel.add(panel);
		
		return main_panel;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
}
