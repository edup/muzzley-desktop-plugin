package com.desktop.osplugin;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.desktop.gui.layout.MainFrame;


/***
 * 
 * @author edup
 * The app starts here.
 * In order to interact with this app you need to install
 * the muzzley app (android/ios) in your smartphone
 *
 */


public class Main
{
  public static void main(String[] args)
  {
    try
    {
    	System.out.println("[Thread ID] Start: "+ Thread.currentThread().getId());
    	    	
      
    	/* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
                 
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
      
    }catch (Exception e) {
      e.printStackTrace();
    } 
  } 
} 
