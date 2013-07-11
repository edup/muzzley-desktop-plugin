package com.muzzley.pcplugin.handlers;

import java.util.HashMap;

import javax.swing.JPanel;


import com.google.gson.JsonObject;
import com.muzzley.lib.Participant;
import com.muzzley.pcplugin.MuzzApp;
import com.muzzley.pcplugin.MuzzleyStateMachine;
import com.muzzley.pcplugin.MuzzRobot;

public abstract class MZWidgetHandler {
	static HashMap<String, MZWidgetHandler> widgets = new HashMap<String, MZWidgetHandler>();
	
	
	
	public static String[] WIDGETS = { "Choose a widget...", "gamepad", "drawpad", "switch", "swipeNavigator", "wheel", "keyboard", "tap"};	
	public abstract void processMessage(Participant.WidgetAction message);
	public abstract JPanel getWidgetPanel();
	
	Participant participant;
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
			System.out.println("Created new object");
		}else
		if(widget_name.compareTo("wheel")==0){ 
			widget_object = new MZWidgetWheel(participant);
			System.out.println("Created new object wheel");
		}else
		if(widget_name.compareTo("swipeNavigator")==0){ 
			widget_object = new MZWidgetSwipe(participant);
			System.out.println("Created new object swipe");
		}else
		if(widget_name.compareTo("drawpad")==0){ 
			widget_object = new MZWidgetDrawpad(participant);
			System.out.println("Created new object drawpad");
		}else
		if(widget_name.compareTo("drawpad")==0){ 
			widget_object = new MZWidgetDrawpad(participant);
			System.out.println("Created new object drawpad");
		}else
		if(widget_name.compareTo("tap")==0){ 
			widget_object = new MZWidgetTap(participant);
			System.out.println("Created new object tap");
		}
		
		
		
		widgets.put(participant.id, widget_object);
		
		return widget_object;
	}
	
	
	
}
