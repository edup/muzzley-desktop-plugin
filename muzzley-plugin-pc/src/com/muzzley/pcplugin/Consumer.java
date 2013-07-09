package com.muzzley.pcplugin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.muzzley.MZActivityListener;
import com.muzzley.pcplugin.handlers.MZWidgetHandler;
import com.muzzley.pcplugin.layout.MainFrame;
import com.muzzley.sdk.MZConnectionStatus;
import com.muzzley.sdk.MessageReceivedHandler;
import com.muzzley.sdk.appliance.MZActivity;
import com.muzzley.sdk.appliance.MZConnectionAppliance;
import com.muzzley.sdk.appliance.Participant;
import com.muzzley.sdk.appliance.listeners.CreateActivityListener;


public class Consumer
{
  static private MuzzRobot robot;
  Client client;
  int mobile_width;
  int mobile_height;
  Dimension screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();

  MainFrame mainframe;
  public HashMap<Integer, Participant> participants;
  
  
  public MZConnectionAppliance session;  
  MZActivity current_activity;
  
  
  public static MuzzRobot getRobot(){
	  return robot;
  } 
  
  MZActivityListener activity_listener = new MZActivityListener(){
	  @Override
		public void participantSignalReceived(Participant participant, JSONObject msg) {
			// TODO Auto-generated method stub
			//System.out.println("["+participant.getName()+"]Message received: " + msg);
		
			//processMessage(participant, msg);
			try {
				JSONObject data = msg.getJSONObject("d");
				
				if(data.has("a") && data.getString("a").compareTo("ready")==0)
						return;
				
				if((data.has("s") && data.getBoolean("s")==true) && data.has("w")){
					MZWidgetHandler widget_object = MZWidgetHandler.onWidgetChanged(participant, data.getString("w"));
					
					if(widget_object!=null){
						mainframe.setWidgetPanel(participant, widget_object.getWidgetPanel());						
					}else{
						//System.out.println("E NULO....");						
					}
					
					return;
				}
						
				try{					
					System.out.println("Trying to Process Message: " + msg.toString());
					MZWidgetHandler.getWidgetHandler(participant, msg).processMessage(msg);
				}catch(NullPointerException e2){
					System.out.println("Not found: " + e2.getMessage());
					//e2.printStackTrace();
				}
			
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Main Json Exception: " + e.getMessage());
			}
			
			
		}
		
		
		@Override
		public void onActivityCreated(MZActivity mzActivity) {
			// TODO Auto-generated method stub
			System.out.println("Activity created: " + mzActivity.getActivityId());
			participants = session.participants;
			mainframe.setActivity(mzActivity);
		}
	};
  
	
	
  
  public Consumer(MainFrame mainframe)
    throws IOException, InterruptedException
  {
    this.robot = new MuzzRobot();
    this.mainframe = mainframe;
    createNewSession();
    
     
  } 
  

  public void createNewSession(){
	  init("41f59b4f660cf28b");
  }
  
  public void changeWidget(Participant participant, final String widget_name){
	  
	  final Participant current_participant = participant;
	  session.changeWidget(current_participant, widget_name, new MessageReceivedHandler() {			
			@Override
			public void processMessage(JSONObject msg) {
				// TODO Auto-generated method stub
				System.out.println("[change widget] User widget is ready to start receiving messages");
				MZWidgetHandler widget_object = MZWidgetHandler.onWidgetChanged(current_participant, widget_name);
				
				if(widget_object!=null){
					mainframe.setWidgetPanel(current_participant, widget_object.getWidgetPanel());						
				}else{
											
				}
				
			}
		});
	                                                       	  
  }
  
  public void init(String token){
		String server_address = "ws://platform.geo.muzzley.com:80/ws";
		
		URI serverUri;
		try {
			serverUri = new URI(server_address);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		session = new MZConnectionAppliance(serverUri) {
			
			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				System.out.println("Some error occured: " + exception.getMessage());
				exception.printStackTrace();
			}
			
			@Override
			public void onConnectionStatusChanged(int status_code, String message) {
				// TODO Auto-generated method stub
				
				System.out.println("onConnectionStatusChanged: " + status_code + ", " + message);
				
				//The codes are defined in MZConnectionStatus class
				if(status_code==MZConnectionStatus.CONNECTED_AND_LOGGED){
					System.out.println("You're connected and logged");	
					session.createActivity(new CreateActivityListener() {
						
						@Override
						public void onError(String msg) {
							// TODO Auto-generated method stub
							System.out.print("Some error when creating activity");
						}
						
						@Override
						public void onActivityCreated(MZActivity activity) {
							// TODO Auto-generated method stub
							current_activity = activity;
							System.out.println("Activity created answer: " + activity.getActivityId());
							activity_listener.onActivityCreated(activity);							
						}
					});
				}
			}
			
			@Override
			public void onConnectionStatusChanged(int status_code) {
				// TODO Auto-generated method stub
				onConnectionStatusChanged(status_code, null);
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote) {
				// TODO Auto-generated method stub
				System.out.println("The connection was closed");
			}
			
			@Override
			public void onParticipantQuit(Participant participant) {
				// TODO Auto-generated method stub
				System.out.println("Bye bye participant: " + participant.getName());
				mainframe.onParticipantQuit(participant);
			}
			
			@Override
			public void onParticipantMessage(Participant participant, JSONObject json) {
				// TODO Auto-generated method stub
				System.out.println("[" + participant.getName() + "] " + json.toString());
				
				if(activity_listener != null)
					activity_listener.participantSignalReceived(participant, json);
			}
			
			@Override
			public void onParticipant(Participant participant) {
				
				mainframe.onParticipant(participant);
				Consumer.this.changeWidget(participant, "gamepad");
				// TODO Auto-generated method stub
				/*
				changeWidget(participant, "gamepad", new MessageReceivedHandler() {
					
					@Override
					public void processMessage(JSONObject msg) {
						// TODO Auto-generated method stub
						System.out.println("User widget is ready to start receiving messages");						
						
					}
				});*/
			}
			
			
			
			@Override
			public void onCoreMessage(JSONObject json) {
				
				try{
					JSONObject data = json.getJSONObject("d");
					if(data.getString("c").compareTo("hb")==0) return;
				}catch(Exception e){}
				
				System.out.println("[core] " + json.toString());
			}
		};
		
		
		session.open(token);
		
	}
  
  
  
  public void close()
  {
    try
    {
      
    }
    catch (Exception e) {
      e.printStackTrace();
    } 
  } 
} 