package com.muzzley.pcplugin.handlers;

import java.util.HashMap;
import javax.swing.JPanel;
import com.muzzley.lib.Participant;
import com.muzzley.lib.commons.Action;
import com.muzzley.lib.commons.Response;
import com.muzzley.osplugin.MuzzApp;
import com.muzzley.osplugin.MuzzRobot;
import com.muzzley.osplugin.WidgetManager;
import com.muzzley.osplugin.WidgetManager.Widget;

public abstract class MZWidgetHandler {
	static HashMap<String, MZWidgetHandler> widgets = new HashMap<String, MZWidgetHandler>();
	public abstract void processMessage(Participant.WidgetAction message);
	public abstract void destroy();
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
		Widget widget = WidgetManager.getInstance().getClassByDisplayName(widget_name);
		if(widget==null) return null;
		
		MZWidgetHandler widget_object = null;
		try {
			System.out.println("ClassName: " + widget.className);
			//Prepare constructor
			Class[] cArg = new Class[1];
	        cArg[0] = Participant.class;
			//Instance a new object
			widget_object = (MZWidgetHandler) widget.className.getDeclaredConstructor(cArg).newInstance(participant);
			widgets.put(participant.id, widget_object);
			
			participant.changeWidget(widget.name,  widget.options,
					new Action<Response>() {
	            @Override
	            public void invoke(Response r) {
	           
	            }
	        },
	        new Action<Exception>() {
	            @Override
	            public void invoke(Exception e) {
	           
	                e.printStackTrace();
	                return;
	            }
	        });
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Exception changing widget: " + e1.getMessage());
		} 
		
		
		
		
		return widget_object;
	}
	
	public Participant getParticipant(){
		return participant;
	}
	
	
}
