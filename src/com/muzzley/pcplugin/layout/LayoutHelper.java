package com.muzzley.pcplugin.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.muzzley.pcplugin.layout.components.Key;

public class LayoutHelper {

	public LayoutHelper() {
		// TODO Auto-generated constructor stub
	}

	static public JPanel getJPanel(int conf){

		JPanel panel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                
                return size;
            }
        };
        panel.setBackground(Color.WHITE);
        setConfigurationPanel(panel, conf);
        
		return panel;
	}
	
	static public JPanel getJPanel(){
		return getJPanel(0);
	}
	
	
	static public void setConfigurationPanel(JPanel panel, int conf){
		
		switch(conf){
		
		case 0: //REPLACE BY CONSTS
			panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        panel.setLayout(new GridLayout(5, 4, 5, 5));
		break;
		case 1: //REPLACE BY CONSTS
			//panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	        //panel.setLayout(new GridLayout(1, 1));
	        panel.setLayout(new FlowLayout());
		break;
		case 2: //REPLACE BY CONSTS
			//panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	        panel.setLayout(new FlowLayout());
	        panel.setBackground(Color.WHITE);
		break;
		}
	}
	
	static public JComboBox createGuiComboKeyItem(JPanel main_panel, String label_name, int default_element, Key[] keyboardArray){
		JPanel element_panel = new JPanel();
		element_panel.setBackground(Color.WHITE);
		
		element_panel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel(label_name);	
		
		Font newLabelFont=new Font(label.getFont().getName(),Font.BOLD,label.getFont().getSize());  
		 //Set JLabel font using new created font  
		 label.setFont(newLabelFont);  
		
		JComboBox combo = new JComboBox(keyboardArray);
		setComboDefault(combo, default_element);
		element_panel.add(label);
		element_panel.add(combo);
		main_panel.add(element_panel);
		
		return combo;
	}
	
			
	static public void setComboDefault(JComboBox combo, int default_key){
	
		for(int i=0; i!=combo.getItemCount(); i++){
			Key key = (Key) combo.getItemAt(i);
			if(key.getValue()==default_key){
				combo.setSelectedIndex(i);
			}
		}
	}
	
	
	static public Key[] getTemplateKeyMap(ArrayList<ArrayList> template_map){
		ArrayList<Key> template = new ArrayList<Key>();		 
		for(int i=0; i!=template_map.size(); i++){
			template.add(new Key(i, "Configuration " + (i+1)));
		}
		Key [] templateArray = new Key[template.size()];
		template.toArray(templateArray);
		return templateArray;
	}
	
}
