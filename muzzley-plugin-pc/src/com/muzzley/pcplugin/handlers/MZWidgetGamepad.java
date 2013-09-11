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

import com.muzzley.pcplugin.MuzzRobot;
import com.muzzley.pcplugin.layout.LayoutHelper;
import com.muzzley.pcplugin.layout.components.InputMethodKeyboard;
import com.muzzley.pcplugin.layout.components.Key;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetGamepad extends MZWidgetHandler{

	//VISUAL COMBOS
	JComboBox<Key> combo_up;
	JComboBox<Key> combo_down;
	JComboBox<Key> combo_left;
	JComboBox<Key> combo_right;
	JComboBox<Key> combo_a;
	JComboBox<Key> combo_b;
	JComboBox<Key> combo_c;
	JComboBox<Key> combo_d;

	//TEMPLATE MAPS
	static ArrayList<ArrayList> TEMPLATE_MAP = new ArrayList<ArrayList>();
	static{
		//CONF1
		ArrayList<Key> conf1=new ArrayList<Key>();
		conf1.add(new Key(KeyEvent.VK_DOWN, "down"));
		conf1.add(new Key(KeyEvent.VK_UP, "up"));
		conf1.add(new Key(KeyEvent.VK_LEFT, "left"));
		conf1.add(new Key(KeyEvent.VK_RIGHT, "right"));
		conf1.add(new Key(KeyEvent.VK_SPACE, "a"));
		conf1.add(new Key(KeyEvent.VK_ENTER, "b"));
		conf1.add(new Key(KeyEvent.VK_CONTROL, "c"));
		conf1.add(new Key(KeyEvent.VK_ALT, "d"));
		TEMPLATE_MAP.add(conf1);

		//CONF1
		ArrayList<Key> conf2=new ArrayList<Key>();
		conf2.add(new Key(KeyEvent.VK_ENTER, "down"));
		conf2.add(new Key(KeyEvent.VK_ENTER, "up"));
		conf2.add(new Key(KeyEvent.VK_LEFT, "left"));
		conf2.add(new Key(KeyEvent.VK_RIGHT, "right"));
		conf2.add(new Key(KeyEvent.VK_UP, "a"));
		conf2.add(new Key(KeyEvent.VK_DOWN, "b"));
		conf2.add(new Key(KeyEvent.VK_N, "c"));
		conf2.add(new Key(KeyEvent.VK_ESCAPE, "d"));
		TEMPLATE_MAP.add(conf2);		
	}
	
	
	class StatusCurrentKeys{
		public boolean down=false;
		public boolean up=false;
		public boolean left=false;
		public boolean right=false;
	}
	StatusCurrentKeys key_status = new StatusCurrentKeys(); 
	

	public MZWidgetGamepad(Participant participant) {
		super(participant);
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub
		
		int button_up = ((Key)combo_up.getSelectedItem()).getValue();
		int button_down = ((Key)combo_down.getSelectedItem()).getValue();
		int button_left = ((Key)combo_left.getSelectedItem()).getValue();
		int button_right = ((Key)combo_right.getSelectedItem()).getValue();
		
		int button_a = ((Key)combo_a.getSelectedItem()).getValue();
		int button_b = ((Key)combo_b.getSelectedItem()).getValue();
		int button_c = ((Key)combo_c.getSelectedItem()).getValue();
		int button_d = ((Key)combo_d.getSelectedItem()).getValue();
		
		
		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			
			if(event.compareTo("press")==0){
				if(component.compareTo("jl")==0){
					int value = Integer.parseInt(data.v.toString());
					int key=0;
					if(value==0){
						//TURN RIGHT
						//System.out.println("RIGHT; "+button_right);
						keyReset();
						
						key_status.right=true;
						robot.keyEvent(button_right, 1);
					}
					else
					if(value==180){
						//TURN LEFT
						System.out.println("LEFT: "+button_left);
						keyReset();
						
						key_status.left=true;
						robot.keyEvent(button_left, 1);
					}else
					if(value==270){
						//TURN DOWN
						System.out.println("DOWN: " + button_down);
						keyReset();
						robot.keyEvent(button_down, 1);
					}else
					if(value==90){
						//TURN UP
						System.out.println("UP: " + button_up);
						keyReset();
						robot.keyEvent(button_up, 1);
					}else
					if(value==315){
						//TURN DOWN/RIGHT
						System.out.println("DOWN/RIGHT");
						keyReset();
						robot.keyEvent(button_down, 1);
						robot.keyEvent(button_right, 1);
					}else
					if(value==45){
						//TURN UP/RIGHT
						System.out.println("UP/RIGHT");
						keyReset();
						robot.keyEvent(button_up, 1);
						robot.keyEvent(button_right, 1);
					}else
					if(value==135){
						//TURN UP/LEFT
						System.out.println("UP/LEFT");
						keyReset();
						robot.keyEvent(button_up, 1);
						robot.keyEvent(button_left, 1);
					}else
					if(value==225){
						//TURN down/LEFT
						System.out.println("DOWN/LEFT");
						keyReset();
						robot.keyEvent(button_down, 1);
						robot.keyEvent(button_left, 1);
					}	
					
				}else
				if(component.compareTo("ba")==0){
					robot.keyEvent(button_a, 1);
				}else
				if(component.compareTo("bb")==0){
					robot.keyEvent(button_b, 1);
				}else
				if(component.compareTo("bc")==0){
					robot.keyEvent(button_c, 1);
				}else
				if(component.compareTo("bd")==0){
					robot.keyEvent(button_d, 1);
				}
				
			}else
			if(event.compareTo("release")==0){
				if(component.compareTo("jl")==0){
					keyReset();
				}else
				if(component.compareTo("ba")==0){
					robot.keyEvent(button_a, 2);
				}else
				if(component.compareTo("bb")==0){
					robot.keyEvent(button_b, 2);
				}else
				if(component.compareTo("bc")==0){
					robot.keyEvent(button_c, 2);
				}else
				if(component.compareTo("bd")==0){
					robot.keyEvent(button_d, 2);
				}
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
		JPanel main_panel;
		main_panel = LayoutHelper.getJPanel(1);        
        
        //TEMPLATE PANEL
        JPanel template_panel = LayoutHelper.getJPanel(2);    
        JComboBox<Key> templateCombo=LayoutHelper.createGuiComboKeyItem(template_panel, "Template Map:", 0, LayoutHelper.getTemplateKeyMap(TEMPLATE_MAP));        
        templateCombo.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        int index = ((JComboBox)e.getSource()).getSelectedIndex();	        
		        
		        ArrayList<Key> templateMap = TEMPLATE_MAP.get(index);		
		        LayoutHelper.setComboDefault(combo_down, templateMap.get(0).getValue());
		        LayoutHelper.setComboDefault(combo_up, templateMap.get(1).getValue());
		        LayoutHelper.setComboDefault(combo_left, templateMap.get(2).getValue());
		        LayoutHelper.setComboDefault(combo_right, templateMap.get(3).getValue());
		        LayoutHelper.setComboDefault(combo_a, templateMap.get(4).getValue());
		        LayoutHelper.setComboDefault(combo_b, templateMap.get(5).getValue());
		        LayoutHelper.setComboDefault(combo_c, templateMap.get(6).getValue());
		        LayoutHelper.setComboDefault(combo_d, templateMap.get(7).getValue());
				
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
		
		combo_a = LayoutHelper.createGuiComboKeyItem(panel, "Button A", templateMap.get(4).getValue(), keyboardArray);
		combo_b = LayoutHelper.createGuiComboKeyItem(panel, "Button B", templateMap.get(5).getValue(), keyboardArray);
		combo_c = LayoutHelper.createGuiComboKeyItem(panel, "Button C", templateMap.get(6).getValue(), keyboardArray);
		combo_d = LayoutHelper.createGuiComboKeyItem(panel, "Button D", templateMap.get(7).getValue(), keyboardArray);
		
		
		//adding to main panel
		main_panel.add(panel);
		
		
		return main_panel;		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}
