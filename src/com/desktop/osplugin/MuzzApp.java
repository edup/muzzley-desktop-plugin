package com.desktop.osplugin;

import javaEventing.EventManager;

import com.desktop.gui.layout.MainFrame;
import com.desktop.mobile.handlers.MZWidgetHandler;
import com.desktop.osplugin.events.OnConnectionChanged;
import com.desktop.osplugin.events.OnParticipant;
import com.desktop.osplugin.events.OnParticipantQuit;
import com.muzzley.lib.Activity;
import com.muzzley.lib.Muzzley;
import com.muzzley.lib.Participant;
import com.muzzley.lib.commons.Action;
import com.muzzley.lib.commons.Callback;
import com.muzzley.lib.commons.Content;
import com.muzzley.lib.commons.Response;

/**
 *
 * @author tiago
 */
public class MuzzApp {
	static private MuzzRobot robot=new MuzzRobot();	
	
    public MuzzApp(final String appToken) {
    	
        Muzzley.connectApp(appToken, new Action<Activity>() {
            @Override
            public void invoke(Activity activity) {
                System.out.println("Id: " + activity.id + ", QR code URL: " + activity.qrCodeUrl);
   
                //Trigger event
                EventManager.triggerEvent(this, new OnConnectionChanged(this, OnConnectionChanged.State.CONNECTED, activity) );
                
                activity.onParticipant.add(new Action<Participant>() {
                    @Override
                    public void invoke(final Participant participant) {
                        
                        System.out.println("Participant joined. Properties:");
                        System.out.println("  Name: " + participant.name);
                
                        EventManager.triggerEvent(this, new OnParticipant(this, participant));
                        
                        participant.onAction.add(new Action<Participant.WidgetAction>() {

                            @Override
                            public void invoke(Participant.WidgetAction t) {
            					MZWidgetHandler.getWidgetHandler(participant, t).processMessage(t);
                            }

                        });
                        
                        participant.onSignal.add(new Callback.Abstract<Content>() {
                            @Override
                            public void invoke(Content t) {
                                System.out.println("Signal Callback action: " + t.a + ", data: " + t.d);
                            }

                            @Override
                            public void invoke(Content t, Action<Response> action) {
                                System.out.println("Signal RPC Callback action: " + t.a + ", data: " + t.d);
                                action.invoke(new Response(true, "Request handled"));
                            }
                        });
                        
                        participant.onQuit.add(new Action<Void>() {
                            @Override
                            public void invoke(Void t) {
                                System.out.println("Participant " + participant.name + " quit.");
                                EventManager.triggerEvent(this, new OnParticipantQuit(this, participant));
                            }
                        });
                    }
                });
                
                activity.onQuit.add(new Action<Void>() {
                    @Override
                    public void invoke(Void v) {
                    	System.out.println("Activity quitted........");
                    }
                });
                
            }
        }, new Action<Exception>() {
            @Override
            public void invoke(Exception e) {
                System.out.println(e);
            }
        });
        
    }
    
    
    public void changeWidget(Participant participant, final String widget_name, Action<Response> listener_response, Action<Exception> listener_error){
    	try{
    		participant.changeWidget(widget_name, listener_response, listener_error);
    	}catch(Exception e){
    		listener_response.invoke(null);
    		e.printStackTrace();
    	}
    }
    
    public static MuzzRobot getRobot(){
  	  return robot;
    } 
    
}