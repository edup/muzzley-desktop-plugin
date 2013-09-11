package com.muzzley.pcplugin.layout;

/* BasicFrame.java

This is a really simple graphics program.
It opens a frame on the screen with a single
line drawn across it.

It's not very polished, but it demonstrates
a graphical program as simply as possible.mag-27Apr2008
*/

//Import the basic graphics classes.
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.muzzley.lib.Activity;
import com.muzzley.lib.Participant;
import com.muzzley.lib.commons.Action;
import com.muzzley.lib.commons.Response;
import com.muzzley.pcplugin.Consts;
import com.muzzley.pcplugin.MuzzApp;
import com.muzzley.pcplugin.handlers.MZWidgetHandler;
import com.muzzley.pcplugin.layout.UsersPanel.UserSelectedEvent;
import com.muzzley.pcplugin.layout.screens.ScreenPair;

public class MainFrame extends JFrame{
	MuzzApp muzzapp;
	
	JPanel main_panel;
	
	JPanel desktopPanel;
	UsersPanel usersPanel;
	
	ScreenPair screenPair;
	
	JPanel options_panel;
	
	JPanel participant_panel;
	JComboBox widgets_list;
	
	// Create a constructor method
	public MainFrame(){
	  super();
	  //consumer = new MuzzleyStateMachine(this);
	  muzzapp = new MuzzApp("41f59b4f660cf28b", this);
	  createAndShowGUI();
	}
	
	@Override
	public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        return size;
    }
	
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
	JScrollPane usersScrollPanel;
    public void createAndShowGUI(){
        //Create and set up the window.
        this.setTitle("Muzzley plugin - You Control!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        
        //Main Panel
        main_panel = new JPanel();
        setContentPane(main_panel);
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.X_AXIS));
        main_panel.setBackground(Color.WHITE);
       
        //Desktop Panel
        desktopPanel = new DesktopPanel(0.7f, 1f);
        //desktopPanel.setLayout(new VerticalLayout());
        //desktopPanel = new JPanel();
        desktopPanel.setLayout(new VerticalLayout());
        
        main_panel.add(desktopPanel);
        
        //Screen Pair
        screenPair = new ScreenPair();
        desktopPanel.add(screenPair);
        //USERS PANEL

        final JViewport viewport = new JViewport();
        viewport.setBackground(Color.WHITE);
        
        usersScrollPanel= new JScrollPane();
        usersScrollPanel.setViewportView(viewport);
        viewport.setLayout(new FlowLayout());
        viewport.setAlignmentX(CENTER_ALIGNMENT);
        
        
        usersPanel = new UsersPanel();
        viewport.add(usersPanel);
        
        main_panel.add(usersScrollPanel);        		
        usersScrollPanel.getViewport().setBackground(Color.red);
        
        this.pack();
        this.setSize(800, 700);
        this.setVisible(true);
        
        //TEST
        
        /*
        for(int i=0; i!=3; i++){
	        Participant p = new Participant("aaaa" + i, "João Esteves", "http://t0.gstatic.com/images?q=tbn:ANd9GcRFVOeQLz546EdScOrhdsLBGkS-AYiXAA75Bv8_qxnLirW2vVCyeA", null);
	        usersPanel.addParticipant(p);
        }*/
        
        
        options_panel=new JPanel();
        options_panel.setLayout(new FlowLayout());
        desktopPanel.add(options_panel);
        paintWidgetsList();
   
        participant_panel=new JPanel();
        participant_panel.setLayout(new FlowLayout());
        desktopPanel.add(participant_panel);
        
        
        desktopPanel.setBackground(Color.WHITE);
        options_panel.setBackground(Color.WHITE);
        screenPair.setBackground(Color.WHITE);
        
        
        
        //ADD LISTENERS
        usersPanel.addUserSelectedListener(new UsersPanel.UserSelectedListener() {
			
			@Override
			public void onUnfocus(UsersPanel.UserSelectedEvent e) {
				// TODO Auto-generated method stub
				UserPanel panel = (UserPanel)e.getSource();
				JPanel panel_to_remove = panel.getWidgetPanel();
				
				if(panel_to_remove!=null)
					participant_panel.remove(panel_to_remove);
			}
			
			@Override
			public void onFocus(UsersPanel.UserSelectedEvent e) {
				// TODO Auto-generated method stub
				UserPanel panel = (UserPanel)e.getSource();
				setWidgetPanel(panel.getWidgetPanel());
			}
			
			@Override
			public void onMultipleFocus() {
				// TODO Auto-generated method stub
				JPanel multipleMessagePanel = new JPanel();
				multipleMessagePanel.setLayout(new FlowLayout());
				JLabel label = new JLabel("Multiple users selected. If you want to see user option menu select only one user!");
				multipleMessagePanel.add(label);
				
				setWidgetPanel(multipleMessagePanel);
			}

			@Override
			public void onOneUserLeft(UserSelectedEvent e) {
				// TODO Auto-generated method stub
				UserPanel panel = (UserPanel)e.getSource();
				setWidgetPanel(panel.getWidgetPanel());
			}

			@Override
			public void onNoUserLeft() {
				// TODO Auto-generated method stub
				setWidgetPanel(null);
			}
		});
    }
	
	public void setActivity(Activity mzActivity){		
		screenPair.setActivity(mzActivity);
	}
	
	
	public void onParticipant(Participant participant) {
		usersPanel.addParticipant(participant);
		        	
		if(usersPanel.getSelectedParticipants().size()==0){
			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new FlowLayout());
			JLabel label = new JLabel("Click on a user on the right bar to start interact");
			messagePanel.add(label);
			setWidgetPanel(messagePanel);
		}
		
		String widgetName = (String)widgets_list.getSelectedItem();
		if(widgets_list.getSelectedIndex()!=0){
			UserPanel userPanel = usersPanel.getUserPanel(participant);
			userPanel.onWidgetChanged(widgetName);
			usersPanel.triggerClickEventOnSelected(userPanel);
		}
		
		validate();
		repaint();
	}
	
	public void onParticipantQuit(Participant participant) {
		UserPanel userPanel = usersPanel.removeParticipant(participant);
		usersPanel.validate();
		usersScrollPanel.validate();
	}
	
	
	void paintWidgetsList(){
		widgets_list = new JComboBox(MZWidgetHandler.WIDGETS);
		widgets_list.setSelectedIndex(0);
		
		
		JButton button = new JButton("Change widget");
		
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				JComboBox cb = widgets_list;
		        final String widgetName = (String)cb.getSelectedItem();	        
		        
		        ArrayList<UserPanel> participants=usersPanel.getSelectedParticipants();
		        
		        if(participants.size()==0){
		        	//widgets_list.setSelectedIndex(0);
		        	JOptionPane.showMessageDialog(null, "Please select at least one Participant on the right side");
		        }else{
		        	for(int i=0; i!=participants.size(); i++){
		        		final UserPanel userPanel = participants.get(i);
		        		final Participant participant = userPanel.getParticipant();
		        		userPanel.onWidgetChanged(widgetName);		        		
		        	}
		        	
		        	usersPanel.triggerClickEventOnSelected();
		        }
				
			}
		});
		
		
	    options_panel.add(widgets_list);
	    options_panel.add(button);
		
		
		desktopPanel.repaint();
	}
	
	
	public void setWidgetPanel(JPanel widget_panel){
		participant_panel.removeAll();
		participant_panel.validate();
		desktopPanel.validate();
		
		if(widget_panel==null) return ;
		
		//widgets_list.setSelectedIndex(0);
		widget_panel.setBackground(Color.WHITE);
	    participant_panel.add(widget_panel);
		
		participant_panel.validate();
		desktopPanel.validate();
	}	
	
}