package com.muzzley.pcplugin.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.muzzley.lib.Participant;
import com.muzzley.pcplugin.handlers.MZWidgetHandler;

public class UserPanel extends JPanel{
	public JPanel content;
	
	boolean selected=false;
	Participant participant;
	
	MZWidgetHandler widget_object;
	JPanel widgetPanel;
			
	public UserPanel(Participant participant) {
		// TODO Auto-generated constructor stub
		this.participant = participant;
		
		defaultGui();
	}
	
	private void defaultGui(){
		this.setBackground(Color.WHITE);
		//this.setBorder(new EmptyBorder(10, 10, 10, 10) );		
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setSize(new Dimension());
		
		//TOP BORDER
		JPanel border = new JPanel();
		border.setBackground(Color.WHITE);
		border.setBorder(new EmptyBorder(0, 0, 1, 0) );
		this.add(border);
		
		content = new JPanel();
		content.setBackground(Color.WHITE);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(content);

		//BOTTOM BORDER
		JPanel border_bottom = new JPanel();
		border_bottom.setBackground(Color.WHITE);
		border_bottom.setBorder(new EmptyBorder(1, 0, 0, 0) );
		this.add(border_bottom);
	}
	
	
	public Participant getParticipant(){
		return participant;
	}
	
	public void select(boolean status){
		if(status){
			this.content.setBackground(Color.LIGHT_GRAY);
			selected=true;
		}
		else{
			this.content.setBackground(Color.WHITE);
			selected=false;
		}
	}
	
	public boolean toggleSelect(){
		select(!selected);
		return selected;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	
	public void onWidgetChanged(String widgetName) {
		// TODO Auto-generated method stub
		destroyWidgetObject();
		
    	widget_object = MZWidgetHandler.onWidgetChanged(participant, widgetName);
    	if(widget_object!=null)
    		widgetPanel=widget_object.getWidgetPanel();
	}

	
	public void destroyWidgetObject() {
		// TODO Auto-generated method stub
		if(widget_object!=null) widget_object.destroy();
	}
	
	public JPanel getWidgetPanel() {        
		// TODO Auto-generated method stub
		return widgetPanel;
	}
	
}
