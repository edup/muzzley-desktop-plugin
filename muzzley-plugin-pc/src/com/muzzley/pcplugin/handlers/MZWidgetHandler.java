package com.muzzley.pcplugin.handlers;

import java.util.HashMap;

import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;

import com.muzzley.pcplugin.Consumer;
import com.muzzley.pcplugin.MuzzRobot;
import com.muzzley.sdk.appliance.Participant;

public abstract class MZWidgetHandler {
	static HashMap<Integer, MZWidgetHandler> widgets = new HashMap<Integer, MZWidgetHandler>();
	
	
	
	public static String[] WIDGETS = { "Choose a widget...", "gamepad", "drawpad", "switch", "swipeNavigator", "wheel", "keyboard"};	
	public abstract void processMessage(JSONObject message);
	public abstract JPanel getWidgetPanel();
	
	Participant participant;
	MuzzRobot robot = Consumer.getRobot();	
	
	public MZWidgetHandler(Participant participant){
		// TODO Auto-generated constructor stub
		this.participant = participant;
	}
	
	public static MZWidgetHandler getWidgetHandler(Participant participant, JSONObject message){		
		MZWidgetHandler widget = widgets.get(participant.getPId());
		
		if(widget==null){ 
			//THIS IS BECAUSE ANDROID APP SEND DIFFERENT SIGNAL THAN IOS
			try{
				//System.out.println("Message: " + message.toString());
				JSONObject data = message.getJSONObject("d");
				String widget_name = data.getString("w");
				return onWidgetChanged(participant, widget_name);
			}catch(Exception e){}
		}
		
		System.out.println("Getting widget for participant: " + participant.getPId() + ", " + widget);
		     
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
		}
		
		
		
		widgets.put(participant.getPId(), widget_object);
		
		return widget_object;
	}
	
	
	
}
