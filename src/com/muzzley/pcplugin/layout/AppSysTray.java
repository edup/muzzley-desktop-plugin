package com.muzzley.pcplugin.layout;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.imgscalr.Scalr;

import com.muzzley.lib.Activity;
import com.muzzley.osplugin.WidgetManager;
import com.muzzley.tools.JPopupMenuEx;
import com.muzzley.tools.MyTools;

public class AppSysTray {
	MainFrame mainframe;
	JPopupMenuEx trayPopup = new JPopupMenuEx();;

    // Create a pop-up menu components
	JMenuItem status = new JMenuItem("Not connected");
	
    JMenuItem showHideItem = new JMenuItem("show app");
    JMenuItem aboutItem = new JMenuItem("about");
    JMenuItem exitItem = new JMenuItem("exit");
    
    //JCheckBoxMenuItem cb1 = new JCheckBoxMenuItem("accept Connections");
	
	public AppSysTray(MainFrame mainframe) {
		// TODO Auto-generated constructor stub
		this.mainframe = mainframe;
		addSystemTray();
	}
	
	public void setStatus(String statusStr) {
		status.setText(statusStr);
	}
	
	public void setStatus(Activity mzActivity) {
		// TODO Auto-generated method stub
		status.setText("Muzzley pair key: " + mzActivity.id);
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
	
	public void addSystemTray() {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final TrayIcon trayIcon =
                new TrayIcon(MyTools.createImage("logo.png", "tray icon"));
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
