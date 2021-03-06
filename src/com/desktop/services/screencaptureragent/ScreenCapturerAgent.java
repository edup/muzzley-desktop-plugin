package com.desktop.services.screencaptureragent;
import java.awt.AWTEvent;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.EventListener;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.desktop.osplugin.Consts;
import com.desktop.osplugin.MuzzRobot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class ScreenCapturerAgent extends WebSocketClient{
	boolean can_run_capture_screen_thread=true;
	boolean capture_screen=false;
	
	private MuzzRobot robot = new MuzzRobot();
	
	static private ScreenCapturerAgent single_instance;
	public static int CAPTURE_DEFAULT_HEIGHT=1024;
	public static float DEFAULT_DIFFERENCE_RATE=0.015f;
	
	ArrayList<ImageReadyOnServerListener> imageReadyOnServerListeners = new ArrayList<ImageReadyOnServerListener>();
	
	public static ScreenCapturerAgent getInstance(){
		if (single_instance == null){
			try {
				single_instance=new ScreenCapturerAgent( new URI(Consts.IMAGE_SERVER_ADDRESS) );
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return single_instance;
	} 
	
	
	
	private ScreenCapturerAgent(URI serverURI) {
		super(serverURI);
		connect();		
	}
	
	public void destroy(){
		try{
			System.out.println("Stopping connection to image server...");
			can_run_capture_screen_thread=false;
			close();
			single_instance=null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Class that manages the capture of the screen
	class ScreenCapture implements Runnable{
		BufferedImage LAST_CAPTURED_IMAGE;
		Rectangle screen_size=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		
		@Override
		public void run() {			
			// TODO Auto-generated method stub
			while(can_run_capture_screen_thread==true){
				try{
					Thread.sleep(Consts.MIN_TIME_TO_CAPTURE_SCREEN);
					streamNewImage();
				}catch(Exception e){ e.printStackTrace(); }
			}
			
		}
		
		
		private void streamNewImage(){
				if(capture_screen==false) return;

				double start = System.currentTimeMillis();
				int id=0;
				BufferedImage img = robot.createScreenCapture(screen_size);
				//System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));
				
				if(!bufferedImagesEqual(LAST_CAPTURED_IMAGE,img)){
					capture_screen=false;
					LAST_CAPTURED_IMAGE=img;
					
					img = Scalr.resize(img, CAPTURE_DEFAULT_HEIGHT);
					//System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						ImageIO.write(img, "jpg", baos);
						//System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));
						baos.flush();			
						//System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));						
						send(baos.toByteArray());						
						//System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));						
						baos.close();
						System.out.println("(" + (++id) + "): " + (System.currentTimeMillis()-start));
						System.out.println("***********\n*******************");
					} catch (Exception e) {
						System.out.println("exception...: " + e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
		boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
				
				if(img1==null && img2==null) return true;
				if(img1==null || img2==null) return false;
				
				
				float difference_count=0;
				float total_count=0;
				if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight() ) {
				    for (int x = 0; x < img1.getWidth(); x++) {
				      for (int y = 0; y < img1.getHeight(); y++) {
				        if (img1.getRGB(x, y) != img2.getRGB(x, y) ) difference_count++;
				        total_count++;
				        
				        //Test immediatly if its different enouph to say that is different
				        //System.out.println("Diferent: " + difference_count+"/"+total_count);
						float difference_rate = difference_count/total_count;
						if(difference_rate>=DEFAULT_DIFFERENCE_RATE) return false;
				      }
				     }
				}else {
				    return false;
				}
				
				return true;
			}
		
		
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub
		System.out.println("Connection closed: (" + arg0+ ")" + arg1);
		can_run_capture_screen_thread = false;
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getMessage());
		arg0.printStackTrace();
	}

	@Override
	public void onMessage(String arg0) {
		// TODO Auto-generated method stub
		
		JsonObject json=new Gson().fromJson(arg0, JsonObject.class);
		try{
			String type = json.get("type").getAsString();
			
			if(type.compareTo("init")==0){
				capture_screen=true;
				can_run_capture_screen_thread=true;
				new Thread(new ScreenCapture()).start();
			}else
			if(type.compareTo("image-updated")==0){
				capture_screen=true;				
				if((json.get("success").getAsBoolean())==true){
					String imageUrl=json.get("url").getAsString();
					triggerImageReadyOnServerEvent(imageUrl, ImageReadyOnServerEvent.IMAGE_READY);
				}else{
					System.out.println("Error sending the message to the server: " + json.get("error").getAsString());
				}
			}
		}catch(Exception e){}
		
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		// TODO Auto-generated method stub
		System.out.println("[Thread Connection opened] "+ Thread.currentThread().getId());
	}
	
	
	
	//LISTENERS
	
	 public interface ImageReadyOnServerListener extends EventListener{
		    public abstract void onImageReady(ImageReadyOnServerEvent e);
	 }
	 
	 public class ImageReadyOnServerEvent extends AWTEvent{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 5976062328343349208L;
		public static final int IMAGE_READY = AWTEvent.RESERVED_ID_MAX + 1;
	    
	    
	    public ImageReadyOnServerEvent(Object obj, int id) {
	        super(obj, id);
	    }
	}
	 
	public void addImageReadyOnServerListener(ImageReadyOnServerListener imageReadyOnServerListener){
		imageReadyOnServerListeners.add(imageReadyOnServerListener);
	}
	public void removeImageReadyOnServerListener(ImageReadyOnServerListener imageReadyOnServerListener){
		imageReadyOnServerListeners.remove(imageReadyOnServerListener);
	}
	
	private void triggerImageReadyOnServerEvent(Object source, int id){
		ImageReadyOnServerEvent event = new ImageReadyOnServerEvent(source, id);
		
		System.out.println("[Triggering] "+ Thread.currentThread().getId());
		
		for(int i=0; i!= imageReadyOnServerListeners.size(); i++){
			ImageReadyOnServerListener imageReadyOnServerListener = (ImageReadyOnServerListener)imageReadyOnServerListeners.get(i);
			if(id==ImageReadyOnServerEvent.IMAGE_READY){
				imageReadyOnServerListener.onImageReady(event);
			}
		}
	}

}
