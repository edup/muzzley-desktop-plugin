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
import com.muzzley.pcplugin.layout.components.Key;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetSwipe extends MZWidgetHandler{
	JComboBox<Key> combo_up;
	JComboBox<Key> combo_down;
	JComboBox<Key> combo_left;
	JComboBox<Key> combo_right;
	
	JComboBox<Key> combo_tap;
	JComboBox<Key> combo_bOk;
	JComboBox<Key> combo_bBack;
	
	
	
	
	public static int martelada_counter=0; //Isto é uma martelada para saber se é o user 1 ou 2 que entra porque ainda não temos mapeamento de keys
	

	public MZWidgetSwipe(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		martelada_counter++;
		System.out.println("HELLO: I'm in swipe");
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
		
        JPanel panel = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };
        
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(5, 4, 5, 5));
		
                 
		 ArrayList<Key> keyboard = new ArrayList<Key>();
		 
		 /*
		 for(int i=48; i!=58; i++){
			 char c = (char) i;  
			 keyboard.add(new Key(i, Character.toString(c)));
		 }*/
		 
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
		 keyboard.add(new Key(KeyEvent.VK_SPACE, "SPACE"));
		 keyboard.add(new Key(KeyEvent.VK_ENTER, "ENTER")); 
		 keyboard.add(new Key(KeyEvent.VK_DELETE, "DELETE")); 
		 
		 keyboard.add(new Key(KeyEvent.VK_ALT, "ALT"));
		 keyboard.add(new Key(KeyEvent.VK_CONTROL, "CONTROL"));
		 keyboard.add(new Key(KeyEvent.VK_UP, "UP ARROW"));
		 keyboard.add(new Key(KeyEvent.VK_DOWN, "DOWN ARROW")); 
		 keyboard.add(new Key(KeyEvent.VK_LEFT, "LEFT ARROW"));
		 keyboard.add(new Key(KeyEvent.VK_RIGHT, "RIGHT ARROW"));
		 
		 Key [] keyboardArray = new Key[keyboard.size()];
		 keyboard.toArray(keyboardArray);
		 
		 int s_up, s_down, s_left, s_right, s_tap, s_bOk, s_bBack;
		 s_up=KeyEvent.VK_UP;
		 s_down=KeyEvent.VK_DOWN;
		 s_left=KeyEvent.VK_LEFT;
		 s_right=KeyEvent.VK_RIGHT;
		 s_tap=KeyEvent.VK_SPACE;
		 s_bOk=KeyEvent.VK_ENTER;
		 s_bBack=KeyEvent.VK_DELETE;
		 
		 
		 
		combo_up = createGuiComboKeyItem(panel, "UP", s_up, keyboardArray);
		combo_down = createGuiComboKeyItem(panel, "DOWN", s_down, keyboardArray);
		combo_left = createGuiComboKeyItem(panel, "LEFT", s_left, keyboardArray);
		combo_right = createGuiComboKeyItem(panel, "RIGHT", s_right, keyboardArray);
		
		combo_tap = createGuiComboKeyItem(panel, "Tap", s_tap, keyboardArray);
		combo_bOk = createGuiComboKeyItem(panel, "Ok", s_bOk, keyboardArray);
		combo_bBack = createGuiComboKeyItem(panel, "Back", s_bBack, keyboardArray);
				
		
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		return panel;		
	}
	
	
	public JComboBox<Key> createGuiComboKeyItem(JPanel main_panel, String label_name, int default_element, Key[] keyboardArray){
		JPanel element_panel = new JPanel();
		element_panel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel(label_name);	
		Font newLabelFont=new Font(label.getFont().getName(),Font.BOLD,label.getFont().getSize());  
		 //Set JLabel font using new created font  
		 label.setFont(newLabelFont);  
		
		JComboBox<Key>combo = new JComboBox<Key>(keyboardArray);
		setComboDefault(combo, default_element);
		element_panel.add(label);
		element_panel.add(combo);
		main_panel.add(element_panel);
		
		return combo;
	}
	
			
	public void setComboDefault(JComboBox combo, int default_key){
	
		for(int i=0; i!=combo.getItemCount(); i++){
			Key key = (Key) combo.getItemAt(i);
			if(key.getValue()==default_key){
				combo.setSelectedIndex(i);
			}
		}
	}
	
	
	public Participant getParticipant(){
		return participant;
	}

}
