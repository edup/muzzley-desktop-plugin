package com.muzzley.pcplugin.handlers;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;


public class MZWidgetDrawpad extends MZWidgetHandler{
	final String MY_NAME = "drawpad";
	

	double posX=0;
	double posY=0;
	
	double screen_width=0;
	double screen_height=0;
	
	public MZWidgetDrawpad(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		System.out.println("HELLO: I'm in drawpad");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screen_width = screenSize.getWidth();
		screen_height = screenSize.getHeight();
		
		try{
			Thread.sleep(3000);
		}catch(Exception e){}
	}

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub

		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			JsonObject value = new Gson().fromJson(data.v.toString(), JsonObject.class);

			Double new_x = value.get("x").getAsDouble();
			Double new_y = value.get("y").getAsDouble();
			
			if(event.compareTo("touchBegin")==0){
				robot.mouseMove((int)(new_x*screen_width), (int)(new_y*screen_height));
				//robot.mousePress(0);
			}else
			if(event.compareTo("touchMove")==0){				
				Double diffX = new_x-posX;	
				Double diffY = new_y-posY;				
				new_x = posX+diffX;
				new_y = posY+diffY;
				
				robot.mouseMove((int)(new_x*screen_width), (int)(new_y*screen_height));
				
				
			}else	
			if(event.compareTo("touchEnd")==0){
				//robot.mouseRelease(0);
			}

			posX=new_x;
			posY=new_y;				

			
			//pointer.setLocation( pointer.x+value.getInt("x"), pointer.y-value.getInt("y") );				
			
			/*
				if(component.compareTo("jl")==0){
					int value = Integer.parseInt(data.getString("v"));
					System.out.println("Value: " + value);
					int key=0;
					if(value==0){
						//TURN RIGHT
						System.out.println("RIGHT; "+button_right);
						keyReset();
						robot.keyEvent(button_right, 1);
					}
					else
					if(value==180){
						//TURN LEFT
						System.out.println("LEFT: "+button_left);
						keyReset();
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
						//robot.keyEvent(39, 1);
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
					
				}
				
			}else
			if(event.compareTo("release")==0){
				if(component.compareTo("jl")==0){
					keyReset();
				}
			}*/
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		return panel;		
	}
	

	
	
	public Participant getParticipant(){
		return participant;
	}

}
