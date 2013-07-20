package com.muzzley.pcplugin.layout.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.muzzley.lib.Activity;
import com.muzzley.pcplugin.Consts;
import com.muzzley.pcplugin.layout.UIXJPanel;
import com.muzzley.pcplugin.layout.VerticalLayout;
import com.muzzley.pcplugin.layout.WrapLayout;

public class ScreenPair extends JPanel{

	JPanel panel1, panel2;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3630167633013423691L;
	
	public ScreenPair() {
		init();
	}

	
	public void init(){
		this.setLayout(new FlowLayout());
		
		panel1 = new JPanel();
		panel1.setLayout(new VerticalLayout());
		panel1.setBackground(Color.WHITE);
		this.add(panel1);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.setBackground(Color.WHITE);
		this.add(panel2);

		
		try {
			BufferedImage logo_img = ImageIO.read(new URL(Consts.URL_LOGO));
			JLabel picLabel = new JLabel(new ImageIcon( logo_img ));
			panel1.add(picLabel);
			picLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	public void setActivity(Activity mzActivity) {
		// TODO Auto-generated method stub
		try {
			JLabel activityId = new JLabel("Activity: " + mzActivity.id);
			activityId.setFont(new Font(activityId.getFont().getName(), Font.BOLD, 16));
			panel1.add(activityId);
			
			System.out.println("Trying to download image from: " + mzActivity.qrCodeUrl);
			BufferedImage qrcode_img = ImageIO.read(new URL(mzActivity.qrCodeUrl));
						
			JLabel qrcodeLabel = new JLabel(new ImageIcon(qrcode_img.getScaledInstance(150, 150, Image.SCALE_SMOOTH) ));
			panel2.add(qrcodeLabel);			
			
			//this.mzActivity = mzActivity;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			System.out.println("Some exception occurred: " + e.getMessage());
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(null, "Could not load QRCODE. Please verify your internet connection and try again.");
		}
		
		
		this.getParent().validate();
	}

	
	//tabbedPane = new JTabbedPane(); 
	//Create the "cards".
	
    //JPanel card = new UIXJPanel(1, 1);
    //card.setBackground(Color.WHITE);
    //card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));
    
	
    //tabbedPane.addTab("Main", card);
    //tabs.put("0", card);             
    //pane.add(tabbedPane, BorderLayout.CENTER);
	
}
