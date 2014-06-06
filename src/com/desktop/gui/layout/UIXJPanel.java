package com.desktop.gui.layout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class UIXJPanel extends JPanel{
	private float percent_width=1;
	private float percent_height=1;
	protected int width=0;
	protected int height=0;
	public String name = "NULL";
	
	private boolean parent_preferred_size=false;
	
	public UIXJPanel() {
		setDefaults();
		calculateNewSize();
	}
	public UIXJPanel(int width, int height) {
		setDefaults();
		
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
		
		this.setSize(new Dimension(this.width, this.height));
	}
	public UIXJPanel(float percent_width, float percent_height) {
		parent_preferred_size=true;
		setDefaults();
		
		// TODO Auto-generated constructor stub
		this.percent_width = percent_width;		
		this.percent_height = percent_height;
				
		calculateNewSize();
	}
	
	private void setDefaults(){
		setBackground(Color.WHITE);
	}

	private void calculateNewSize(){
		if(getParent()!=null){
			Dimension d = getParent().getSize();
			this.width = (int)(d.width * (percent_width / 1));
			this.height = (int)(d.height * (percent_height / 1));
		}
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		calculateNewSize();
		if(parent_preferred_size==true)
			return new Dimension(this.width, this.height);
		else
			return super.getPreferredSize();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return width;
	}
	

	
}
