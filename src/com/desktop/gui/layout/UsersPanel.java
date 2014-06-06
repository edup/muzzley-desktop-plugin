package com.desktop.gui.layout;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.desktop.gui.layout.UsersPanel.UserSelectedEvent;
import com.desktop.osplugin.Consts;
import com.desktop.tools.MyTools;
import com.muzzley.lib.Participant;


public class UsersPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6513824661255776715L;
	private HashMap<String, UserPanel> users = new HashMap<String, UserPanel>();
	ArrayList<UserSelectedListener> userSelectedListeners = new ArrayList<UserSelectedListener>();
	
	public UsersPanel() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}
	

	private void init(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setBackground(Color.WHITE);
	}
	
	public void addParticipant(Participant participant){
		UserPanel panel = new UserPanel(participant);
		users.put(participant.id, panel);
		
		BufferedImage img;
		try {
			JLabel nameLabel = new JLabel(participant.name);
			nameLabel.setBorder(new EmptyBorder(0, 0, 3, 0) );
			panel.content.add(nameLabel);
			
			img = ImageIO.read(new URL(participant.photoUrl));
			if(img==null) img = MyTools.toBufferedImage(MyTools.createImage("logo.png", "Participant"));
			System.out.println("Participant picture: " + participant.photoUrl + ", " + img);
			
			JLabel picLabel = new JLabel(new ImageIcon( img.getScaledInstance(75, 75, Image.SCALE_SMOOTH) ));
			panel.content.add(picLabel);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				UserPanel panel = (UserPanel)e.getSource();
				boolean result=panel.toggleSelect();
				
				if(result==true){
					triggerUserSelectedEvent(panel, UserSelectedEvent.SELECTED);
				}else{
					triggerUserSelectedEvent(panel, UserSelectedEvent.NOT_SELECTED);
				}
			}
		});
		
		
		this.add(panel);
	}
	
	 public ArrayList<UserPanel> getSelectedParticipants(){
		ArrayList<UserPanel> participants = new ArrayList<UserPanel>();
		
		Iterator it = users.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry user = (Map.Entry)it.next();
	        
	        UserPanel userPanel = (UserPanel) user.getValue();
	        if(userPanel.isSelected()==true){
	        	participants.add(userPanel);
	        	
	        }
	    }
	    
	    return participants;
	 }
	 public ArrayList<UserPanel> getAllParticipants(){
			ArrayList<UserPanel> participants = new ArrayList<UserPanel>();
			Iterator it = users.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry user = (Map.Entry)it.next();
		        UserPanel userPanel = (UserPanel) user.getValue();
		        participants.add(userPanel);
		    }
		    return participants;
	 }
     
	 
	 public UserPanel removeParticipant(Participant participant){
		 UserPanel userPanel = (UserPanel) users.remove(participant.id);
		 userPanel.destroyWidgetObject();
		 
		 this.remove(userPanel);
		 return userPanel;
	 }
	 
	 
	 
	 //LISTENER
	    //button.addActionListener(this);
	 public interface UserSelectedListener extends EventListener {
		    public abstract void onFocus(UserSelectedEvent e);
		    public abstract void onUnfocus(UserSelectedEvent e);
		    public abstract void onMultipleFocus();
		    public abstract void onOneUserLeft(UserSelectedEvent e);
		    public abstract void onNoUserLeft();
	 }
	 
	 public class UserSelectedEvent extends AWTEvent {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 5976062328343349208L;
		public static final int SELECTED = AWTEvent.RESERVED_ID_MAX + 1;
	    public static final int NOT_SELECTED   = SELECTED + 1;
	    public static final int MULTIPLE_SELECTED   = SELECTED + 2;
	    public static final int ONE_USER_LEFT = SELECTED + 3;
	    public static final int NO_USER_LEFT = SELECTED + 4;
	    
	    public UserSelectedEvent(UserPanel source, int id) {
	        super(source, id);
	    }
	}
	 
	public void addUserSelectedListener(UserSelectedListener userSelectedListener){
		userSelectedListeners.add(userSelectedListener);
	}
	public void removeUserSelectedListener(UserSelectedListener userSelectedListener){
		userSelectedListeners.remove(userSelectedListener);
	}
	public void triggerUserSelectedEvent(UserPanel source, int id){
		UserSelectedEvent event = new UserSelectedEvent(source, id);
		
		for(int i=0; i!=userSelectedListeners.size(); i++){
			UserSelectedListener userSelectedListener = (UserSelectedListener)userSelectedListeners.get(i);
			if(id==UserSelectedEvent.SELECTED)
				userSelectedListener.onFocus(event);
			else
				userSelectedListener.onUnfocus(event);
			
			
			//TEST FOR MULTIPLE USERS
			if(id==UserSelectedEvent.SELECTED && getSelectedParticipants().size()>1){
				userSelectedListener.onMultipleFocus();
			}
			
			//TEST FOR ONE USER LEFT
			if(id==UserSelectedEvent.NOT_SELECTED && getSelectedParticipants().size()==1){
				userSelectedListener.onOneUserLeft(new UserSelectedEvent((UserPanel)getSelectedParticipants().get(0), UserSelectedEvent.ONE_USER_LEFT));
			}
			
			//TEST FOR NO USER LEFT
			if(id==UserSelectedEvent.NOT_SELECTED && getSelectedParticipants().size()==0){
				userSelectedListener.onNoUserLeft();
			}
			
		}
		
		
	}


	public void triggerClickEventOnSelected() {
		// TODO Auto-generated method stub
		ArrayList<UserPanel> userPanel = getSelectedParticipants();
		for(int i=0; i!=userPanel.size(); i++){
			triggerClickEventOnSelected(userPanel.get(i));
		}
	}
	
	public void triggerClickEventOnSelected(UserPanel userPanel) {
		// TODO Auto-generated method stub
		triggerUserSelectedEvent(userPanel, UserSelectedEvent.SELECTED);
	}


	public UserPanel getUserPanel(Participant participant) {
		// TODO Auto-generated method stub
		
		return (UserPanel) users.get(participant.id);
	}
	
}
