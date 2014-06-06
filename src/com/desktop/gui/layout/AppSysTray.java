package com.desktop.gui.layout;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javaEventing.EventManager;
import javaEventing.interfaces.Event;
import javaEventing.interfaces.GenericEventListener;

import javax.swing.JMenuItem;
import com.desktop.osplugin.WidgetManager;
import com.desktop.osplugin.events.OnConnectionChanged;
import com.desktop.tools.MyTools;
import com.muzzley.lib.Activity;


public class AppSysTray {
	MainFrame mainframe;
	JPopupMenuEnhanced trayPopup = new JPopupMenuEnhanced();;

    // Create a pop-up menu components
	JMenuItem status = new JMenuItem("Not connected");
	
    JMenuItem showHideItem = new JMenuItem("show app");
    JMenuItem aboutItem = new JMenuItem("about");
    JMenuItem exitItem = new JMenuItem("exit");
    TrayIcon trayIcon;

    //JCheckBoxMenuItem cb1 = new JCheckBoxMenuItem("accept Connections");
	
	public AppSysTray(MainFrame mainframe) {
		// TODO Auto-generated constructor stub
		this.mainframe = mainframe;
		addSystemTray();

		registerListeners();
	}
	
	private void registerListeners() {
		
		EventManager.registerEventListener( 
				new GenericEventListener(){
					@Override
					public void eventTriggered(Object source, Event event) {
						OnConnectionChanged obj = (OnConnectionChanged) event;
						changeTrayIcon(obj.isConnected());
						setStatus(obj.getActivity());
					}    // <-- Register an Event Listener.
			

		}, OnConnectionChanged.class);	
		
	}
	
	private void setStatus(Activity mzActivity) {
		// TODO Auto-generated method stub
		if(mzActivity == null) {
			status.setText("Not connected...");			
		} else {
			status.setText("Muzzley pair key: " + mzActivity.id);
		}
		
		trayPopup.pack();
	}
	
	public void showHideTrigger(boolean flag) {
		// TODO Auto-generated method stub
		if(!flag) {
			mainframe.setVisible(false);
			showHideItem.setText("show app");
		}else {
			mainframe.setVisible(true);
			showHideItem.setText("hide app");
		}
		
		trayPopup.setVisible(false);
	}
	
	private void showHideTrigger() {
		// TODO Auto-generated method stub
		if(mainframe.isVisible()) {
			showHideTrigger(false);
		}else {
			showHideTrigger(true);
		}
	}
	
	
	private void changeTrayIcon(boolean connected) {
		
		if (connected == true) {
			trayIcon.setImage(MyTools.createImage("appicon.png", "tray icon"));
		}else {
			trayIcon.setImage(MyTools.createImage("appicon_not_connected.png", "tray icon"));			
		}
		
	}
	
	
	public void addSystemTray() {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        
        trayIcon =
                new TrayIcon(MyTools.createImage("appicon_not_connected.png", "tray icon"));
        

        trayIcon.setImageAutoSize(true);
        
        final SystemTray tray = SystemTray.getSystemTray();
        
        
        //Add components to pop-up menu
        trayPopup.add(status);
        status.setEnabled(false);
        
        trayPopup.addSeparator();
        trayPopup.add(showHideItem);
        trayPopup.addSeparator();
        
        JMenuItem changeWidget = new JMenuItem("Change Widget");
        changeWidget.setEnabled(false);
        trayPopup.add(changeWidget);
        trayPopup.addSeparator();
        WidgetManager wmanager = WidgetManager.getInstance();
        Object[] widgets = wmanager.getDisplayNamesArray();
        for(int i=0; i!=widgets.length; i++) {
        	JMenuItem widget = new JMenuItem((String)widgets[i]);
            trayPopup.add(widget);
        	widget.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					trayPopup.setVisible(false);
					JMenuItem source = (JMenuItem) e.getSource();
					System.out.println("Change widget: " + source.getText());
					mainframe.changeWidgetToAllParticipants(source.getText());
					
				}
			});
        	
        }
        
        trayPopup.addSeparator();
        trayPopup.add(aboutItem);
        trayPopup.add(exitItem);
       
        
        //Display menu
        
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
        
        
        //SHOW HIDE LISTENER
        showHideItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHideTrigger();
			}
		});
        //EXIT LISTENER
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemTray.getSystemTray().remove(trayIcon);
                System.exit(0);
            }
        });
        
        
        trayIcon.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(trayPopup.isVisible()) {
					System.out.println("Shutting of");
					trayPopup.setVisible(false);
					return;
				}
				
				// TODO Auto-generated method stub
				System.out.println("Mouse pressed: " + e.getComponent() + ", " + e.getX() + ", " + e.getY());
				if (e.getButton() == MouseEvent.BUTTON1) {
                    Rectangle bounds = MyTools.getSafeScreenBounds(trayPopup.getLocation());
                    
                    Point point = e.getPoint();
                    int x = point.x;
                    int y = point.y;
                    
                    if (y < bounds.y) {
                        y = bounds.y;
                    } else if (y > bounds.y + bounds.height) {
                        y = bounds.y + bounds.height;
                    }
                    if (x < bounds.x) {
                        x = bounds.x;
                    } else if (x > bounds.x + bounds.width) {
                        x = bounds.x + bounds.width;
                    }
                    if (x + trayPopup.getPreferredSize().width > bounds.x + bounds.width) {
                        x = (bounds.x + bounds.width) - trayPopup.getPreferredSize().width;
                    }
                    if (y + trayPopup.getPreferredSize().height > bounds.y + bounds.height) {
                        y = (bounds.y + bounds.height) - trayPopup.getPreferredSize().height;
                    }
                    trayPopup.setLocation(x-10, y);
                    trayPopup.setVisible(true);
                }
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
			}
		});
        
        
	}

	
}
