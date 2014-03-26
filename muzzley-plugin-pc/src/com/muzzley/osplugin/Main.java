package com.muzzley.osplugin;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.muzzley.pcplugin.layout.MainFrame;


public class Main
{
  public static void main(String[] args)
  {
    try
    {
    	System.out.println("[Thread ID] Start: "+ Thread.currentThread().getId());
    	    	
      
    	/* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");            
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            
        	//System.out.println("Look and feel (OS): " + UIManager.getSystemLookAndFeelClassName());
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        /* Turn off metal's use of bold fonts */
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
         
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