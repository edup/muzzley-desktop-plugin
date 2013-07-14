package com.muzzley.pcplugin.handlers;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.management.modelmbean.DescriptorSupport;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.imgscalr.Scalr;
import org.java_websocket.util.Base64;


import com.muzzley.pcplugin.MuzzRobot;
import com.muzzley.pcplugin.layout.components.Key;
import com.muzzley.pcplugin.layout.components.screenrecorder.DesktopScreenRecorder;
import com.muzzley.pcplugin.layout.components.screenrecorder.ScreenRecorder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;
import com.muzzley.lib.commons.Action;
import com.muzzley.lib.commons.Content;
import com.muzzley.lib.commons.Response;

public class MZWidgetImage extends MZWidgetHandler{
	public static boolean streaming = false;
	
	public MZWidgetImage(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		
	}
	
	private void streamNow(boolean stream){
		if(!stream){
			streaming = false;	
		}else{
			streaming = true;
			new Thread (new ImageStreamer()).start();
		}
	}
	
	class ImageStreamer implements Runnable {
		BufferedImage last_result = null;
		boolean sendAgain = true;
		
		@Override
		public void run() {
			Rectangle recordArea = initialiseScreenCapture();
			while(true){
				if(!streaming) return;
				try {
					Thread.sleep(100);
					if(!sendAgain) continue;

					BufferedImage img = captureScreen(recordArea);					
					img = Scalr.resize(img, 1024);
					
					//if(last_result!=null && img.)
					
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					OutputStream b64 = new Base64.OutputStream(os);
					ImageIO.write(img, "jpg", b64);
					String result = "data:image/gif;base64," + os.toString("UTF-8");					
		
					
					JsonObject obj = new JsonObject();
					obj.addProperty("src", result);
					obj.addProperty("orientation", "landscape");
					obj.addProperty("mode", "center");					
					JsonElement c = new Content("image", obj).d;
                    
					System.out.println("Sending...");
					sendAgain=false;
					participant.changeWidget("image",  c,
							new Action<Response>() {
                        @Override
                        public void invoke(Response r) {
                        	sendAgain=true;
                        }
                    },
                    new Action<Exception>() {
                        @Override
                        public void invoke(Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    });    	 
					
					//last_result = result;					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
				
			}			

			
			//participant.send(new Content("image", d));
		}

		public Rectangle initialiseScreenCapture() {	  
	      return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	   }

		public BufferedImage captureScreen(Rectangle recordArea) {
			// TODO Auto-generated method stub
			BufferedImage image = robot.createScreenCapture(recordArea);

		      //Graphics2D grfx = image.createGraphics();
		      //grfx.drawImage(mouseCursor, mousePosition.x - 8, mousePosition.y - 5,
		      //      null);
		      //grfx.dispose();

		      return image;
		}
		
		
	}
	

	@Override
	public void processMessage(Participant.WidgetAction data) {
		// TODO Auto-generated method stub

		try {			
			String component = (String)data.c;
			String event = (String) data.e;
			
	
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
        //panel.setLayout(new GridLayout(5, 4, 5, 5));
        panel.setLayout(new FlowLayout());

        
        final JCheckBox streamButton = new JCheckBox("Stream to url http://goo.gl/NYjQF");
        streamButton.setSelected(false);
        //Register a listener for the check boxes.
        streamButton.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Object source = e.getItemSelectable();
			    if (source == streamButton) {
			    	if (e.getStateChange() == ItemEvent.DESELECTED){
			    		streamNow(false);
			    	 }else{
			    		 streamNow(true);
			    	 }
			    } 
			}
        	
        });
        
        panel.add(streamButton);
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		
		return panel;		
	}
	

	
	
	public Participant getParticipant(){
		return participant;
	}

}
