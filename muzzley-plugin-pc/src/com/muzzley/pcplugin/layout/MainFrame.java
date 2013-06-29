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

import com.muzzley.pcplugin.Consumer;
import com.muzzley.pcplugin.handlers.MZWidgetHandler;
import com.muzzley.sdk.appliance.MZActivity;
import com.muzzley.sdk.appliance.Participant;

public class MainFrame{

	MZActivity mzActivity=null;
	Consumer consumer;

	
	final static int extraWindowWidth = 100;
	
	
	// Create a constructor method
	public MainFrame(){
	  // All we do is call JFrame's constructor.
	  // We don't need anything special for this
	  // program.
	  super();
	  
	  	try {
			consumer = new Consumer(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	}
	
	
	HashMap<Integer, JPanel> tabs = new HashMap<Integer, JPanel>();	
	JTabbedPane tabbedPane;
	public void addComponentToPane(Container pane) {
        
		tabbedPane = new JTabbedPane(); 
		//Create the "cards".
        JPanel card = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        
        tabbedPane.addTab("Main", card);
        tabs.put(0, card);             
        pane.add(tabbedPane, BorderLayout.CENTER);
        
    }
	
	private JPanel getNewTab(int id, String name){
		//Create the "cards".
        JPanel card = new JPanel();
        
        card.setLayout(new GridBagLayout());
		
        tabbedPane.addTab(name, card);
        tabs.put(id, card);

        return card;
	} 
	
	
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
	static JFrame frame;
    public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Muzzley PC Plugin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        //Create and set up the content pane.
        MainFrame demo = new MainFrame();
        demo.addComponentToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
	
	JPanel getMainPanel(){ 
		return tabs.get(0);
	}
	
	JPanel getParticipantPanel(Participant participant){ 
		return tabs.get(participant.getPId());
	}
	
	public void setActivity(MZActivity mzActivity){		
		try {
			this.mzActivity = mzActivity;
			BufferedImage qrcode_img = ImageIO.read(new URL(mzActivity.getQRCodeUrl()));
			
			JLabel picLabel = new JLabel(new ImageIcon( qrcode_img ));
			getMainPanel().add(picLabel);
			frame.repaint();			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void onParticipant(Participant participant) {
		JPanel participant_panel = getNewTab(participant.getPId(), participant.getName());
		paintWidgetsList(participant, participant_panel);
		
		try{
			//TODO: MARTELO
			tabbedPane.setSelectedComponent(participant_panel);
		}catch(Exception e){}
	}
	
	public void onParticipantQuit(Participant participant) {
		JPanel pane = tabs.remove(participant.getPId());
		try{
			//TODO: MARTELO
			tabbedPane.remove(pane);
		}catch(Exception e){}
	}
	
	void paintWidgetsList(Participant participant, JPanel participant_panel){		

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		
		final Participant current_participant = participant;
		
		JComboBox widgets_list = new JComboBox(MZWidgetHandler.WIDGETS);
		widgets_list.setSelectedIndex(0);
		widgets_list.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				JComboBox cb = (JComboBox)e.getSource();
		        String widgetName = (String)cb.getSelectedItem();	        
		        
		        System.out.println("Changed: " + widgetName);
				if(cb.getSelectedIndex()!=0)
					consumer.changeWidget(current_participant, widgetName);
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
	    c.gridy = 0;
	    participant_panel.add(paintPhoto(participant), c);
		
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
	    c.gridy = 0;
		participant_panel.add(widgets_list, c);
		
		frame.repaint();
	}

	public JPanel paintPhoto(Participant participant){
		try {
			JPanel panel= new JPanel();
			BufferedImage img = ImageIO.read(new URL(participant.getPhotoUrl()));
			
			JLabel picLabel = new JLabel(new ImageIcon( img.getScaledInstance(120, 100, Image.SCALE_SMOOTH) ));
			panel.add(picLabel);
			return panel;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void setWidgetPanel(Participant participant, JPanel widget_panel){		
		getParticipantPanel(participant).removeAll();
		
		paintWidgetsList(participant, getParticipantPanel(participant));
		getParticipantPanel(participant).remove(widget_panel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 1;
	    c.gridy = 1;
		
		getParticipantPanel(participant).add(widget_panel, c);		
		frame.repaint();
	}	
		
	
	
}