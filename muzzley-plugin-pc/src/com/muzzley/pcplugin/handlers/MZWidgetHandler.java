package com.muzzley.pcplugin.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.muzzley.lib.Participant;
import com.muzzley.pcplugin.MuzzApp;
import com.muzzley.pcplugin.MuzzRobot;
import com.muzzley.pcplugin.layout.LayoutHelper;
import com.muzzley.pcplugin.layout.components.Key;

public abstract class MZWidgetHandler {
	static HashMap<String, MZWidgetHandler> widgets = new HashMap<String, MZWidgetHandler>();
	public static String[] WIDGETS = { "Change widget...", "gamepad", "drawpad", "swipeNavigator", "image"};	
	
	
	public abstract void processMessage(Participant.WidgetAction message);
	public abstract JPanel getWidgetPanel();
		
	final Participant participant;
	protected MuzzRobot robot = MuzzApp.getRobot();	
	
	public MZWidgetHandler(Participant participant){
		// TODO Auto-generated constructor stub
		this.participant = participant;
	}
	
	public static MZWidgetHandler getWidgetHandler(Participant participant, Participant.WidgetAction data){		
		MZWidgetHandler widget = widgets.get(participant.id);
		
		if(widget==null){ 
			//THIS IS BECAUSE ANDROID APP SEND DIFFERENT SIGNAL THAN IOS
			try{
				String widget_name = data.w;
				return onWidgetChanged(participant, widget_name);
			}catch(Exception e){}
		}
		
		//System.out.println("Getting widget for participant: " + participant.id + ", " + widget);
		     
		return widget;
	}
	
	public static MZWidgetHandler onWidgetChanged(Participant participant, String widget_name){		
		
		//ADD THE INTERFACE HANDLER HERE
		
		//if not in memory it creates a new one
		MZWidgetHandler widget_object = null;
		if(widget_name.compareTo("gamepad")==0){ 
			widget_object = new MZWidgetGamepad(participant);
		}else
		if(widget_name.compareTo("wheel")==0){ 
			widget_object = new MZWidgetWheel(participant);
		}else
		if(widget_name.compareTo("swipeNavigator")==0){ 
			widget_object = new MZWidgetSwipeNavigator(participant);
		}else
		if(widget_name.compareTo("drawpad")==0){ 
			widget_object = new MZWidgetDrawpad(participant);
		}else
		if(widget_name.compareTo("image")==0){ 
			widget_object = new MZWidgetImage(participant);
		}else
		if(widget_name.compareTo("tap")==0){ 
			widget_object = new MZWidgetTap(participant);
		}
		
		
		
		widgets.put(participant.id, widget_object);
		
		return widget_object;
	}
	
	public Participant getParticipant(){
		return participant;
	}

	
}
