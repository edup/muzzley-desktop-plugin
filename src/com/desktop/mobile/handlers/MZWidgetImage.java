package com.desktop.mobile.handlers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.imgscalr.Scalr;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.Base64;

import com.desktop.osplugin.Consts;
import com.desktop.osplugin.MuzzRobot;
import com.desktop.services.screencaptureragent.ScreenCapturerAgent;
import com.desktop.services.screencaptureragent.ScreenCapturerAgent.ImageReadyOnServerEvent;
import com.desktop.services.screencaptureragent.ScreenCapturerAgent.ImageReadyOnServerListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.muzzley.lib.Participant;
import com.muzzley.lib.commons.Action;
import com.muzzley.lib.commons.Content;
import com.muzzley.lib.commons.Response;

public class MZWidgetImage extends MZWidgetHandler{
	
	ScreenCapturerAgent screen_agent;
	private boolean streaming = false;
	
	
	static int n_instances=0;
	
	ImageReadyOnServerListener image_ready_listener = new ImageReadyOnServerListener() {
		Object mutex = new Object();		
		
		@Override
		public void onImageReady(ImageReadyOnServerEvent e) {
			System.out.println("[On Image Ready] "+ Thread.currentThread().getId());
			// TODO Auto-generated method stub	
			if(streaming==false) return; // if not streaming bye bye
			
			//if cannot send again to client
			//it means that is still sending.
			//Let's hold this image till it finished or arrive a new one to stream
			String result = (String) e.getSource();
			
			JsonObject obj = new JsonObject();
			obj.addProperty("src", result);
			obj.addProperty("orientation", "landscape");
			obj.addProperty("mode", "center");					
			JsonElement c = new Content("image", obj).d;
            
			participant.changeWidget("image",  c,
					new Action<Response>() {
                @Override
                public void invoke(Response r) {
                	//System.out.println("This thread number: " + Thread.currentThread().getId());
                	
                }
            },
            new Action<Exception>() {
                @Override
                public void invoke(Exception e) {
                    e.printStackTrace();
                    return;
                }
            });

		}
	};  
	
	public MZWidgetImage(Participant participant) {
		super(participant);
		// TODO Auto-generated constructor stub
		n_instances++;
	}
	
	private void streamNow(boolean stream){
		if(!stream){
			streaming = false;
			if(screen_agent!=null){
				screen_agent.removeImageReadyOnServerListener(image_ready_listener);
				if(n_instances==0)
					screen_agent.destroy();
			}
			
			screen_agent=null;
		}else{
			streaming = true;
			screen_agent = ScreenCapturerAgent.getInstance();
			screen_agent.addImageReadyOnServerListener(image_ready_listener);
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
        JPanel panel = new JPanel();
        
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //panel.setLayout(new GridLayout(5, 4, 5, 5));
        panel.setLayout(new GridBagLayout());

        final JCheckBox streamButton = new JCheckBox("Stream to url http://share.lab.muzzley.com");
        streamButton.setSelected(true);
        streamNow(true);
        
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
                
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        //panel.add(new JLabel("Stream image height:"), c);
        c.gridx = 1;
        c.gridy = 0;
        
        final JTextField textfield_default_height = new JTextField(new Integer(ScreenCapturerAgent.CAPTURE_DEFAULT_HEIGHT).toString());
        textfield_default_height.setSize(100, 30);
        //panel.add(textfield_default_height, c);
        
        c.gridx = 0;
        c.gridy = 1;
        //panel.add(new JLabel("Stream only with different rate >= "), c);
        c.gridx = 1;
        c.gridy = 1;
        final JTextField textfield_stream_rate = new JTextField(new Float(ScreenCapturerAgent.DEFAULT_DIFFERENCE_RATE).toString());
        //panel.add(textfield_stream_rate, c);        
        
        c.gridx = 0;
        c.gridy = 2;
        //panel.add(new JLabel("Stream seconds period"), c);
        c.gridx = 1;
        c.gridy = 2;
        final JTextField textfield_stream_period = new JTextField(new Float(Consts.MIN_TIME_TO_CAPTURE_SCREEN/1000).toString());
        //panel.add(textfield_stream_period, c);        
        
        
        
        c.gridx = 1;
        c.gridy = 3;
        JButton bupdate = new JButton("Update");
        //panel.add(bupdate, c);        

        bupdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					ScreenCapturerAgent.CAPTURE_DEFAULT_HEIGHT = Integer.parseInt(textfield_default_height.getText());
					ScreenCapturerAgent.DEFAULT_DIFFERENCE_RATE = Float.parseFloat(textfield_stream_rate.getText());
					Consts.MIN_TIME_TO_CAPTURE_SCREEN = Math.round(Float.parseFloat(textfield_stream_period.getText())*1000);
				}catch(Exception exp){
					exp.printStackTrace();
				}
			}
		});
        
        
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.0;
        c.gridwidth = 2;
        panel.add(streamButton, c);

        
        //panel.add(new JSeparator());
        //panel.add(new JSeparator());
		
        
        
        //panel.add(streamButton);
		//panel.add(new JSeparator());
		
		return panel;		
	}
	

	/*
	static public boolean compareBufferImages(BufferedImage img1, BufferedImage img2){
		
		if(img1==null && img2==null) return true;
		if(img1==null || img2==null) return false;
		
		
		DataBuffer dbActual = img1.getRaster().getDataBuffer();
		DataBuffer dbExpected = img2.getRaster().getDataBuffer();
		
		DataBufferInt actualDBAsDBInt = (DataBufferInt) dbActual ;
		DataBufferInt expectedDBAsDBInt = (DataBufferInt) dbExpected ;
		
		int difference_count=0;
		for (int bank = 0; bank < actualDBAsDBInt.getNumBanks(); bank++) {
			   int[] actual = actualDBAsDBInt.getData(bank);
			   int[] expected = expectedDBAsDBInt.getData(bank);

			   // this line may vary depending on your test framework
			   if(Arrays.equals(actual, expected)==false) difference_count++;
			  
		}
		
		
		double difference_rate = difference_count/actualDBAsDBInt.getNumBanks();
		if(difference_rate<=0.03){
			return true;
		}		
		
		return false;
	}*/
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		n_instances--;
		streamNow(false);
	}
	
	

}
