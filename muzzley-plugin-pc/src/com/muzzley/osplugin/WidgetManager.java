package com.muzzley.osplugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.muzzley.pcplugin.handlers.MZWidgetDrawpad;
import com.muzzley.pcplugin.handlers.MZWidgetGamepad;
import com.muzzley.pcplugin.handlers.MZWidgetHandler;
import com.muzzley.pcplugin.handlers.MZWidgetImage;
import com.muzzley.pcplugin.handlers.MZWidgetSwipeNavigator;
import com.muzzley.pcplugin.handlers.MZWidgetTouchpadKeyboard;

public class WidgetManager {
	ArrayList<Widget> widgets = new ArrayList<WidgetManager.Widget>();
	static WidgetManager widgetM = new WidgetManager();
	
	static public WidgetManager getInstance() {
		return widgetM;
	}
	
	private WidgetManager() {
		// TODO Auto-generated constructor stub
		loadWidgets();
	}
	
	private void loadWidgets() {
		
		widgets.add( new Widget("touchpadKeyboard", "Mouse & Keyboard",  MZWidgetTouchpadKeyboard.class) );
		widgets.add( new Widget("gamepad", "Gamepad",  MZWidgetGamepad.class) );
		widgets.add( new Widget("drawpad", "Mouse",  MZWidgetDrawpad.class) );
		widgets.add(new Widget("swipeNavigator", "Swipe",  MZWidgetSwipeNavigator.class));
		widgets.add(new Widget("image", "Share my screen",  MZWidgetImage.class));
	/*
		ClassLoader loader;
		try {
			loader = URLClassLoader.newInstance(
				    new URL[] { new URL("http://localhost/osplugin/test.jar") },
				    getClass().getClassLoader()
			);
			
			//Class<?> clazz = Class.forName("mypackage.MyClass", true, loader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			
		
	}

	public Object[] getDisplayNamesArray() {
		ArrayList<String> objs = new ArrayList<String>();
		for(int i=0; i!= widgets.size(); i++) {
			objs.add(widgets.get(i).displayName);
		}
		
		return objs.toArray();
	}
	public Widget getClassByDisplayName(String displayName) {
		System.out.println("Display Name: " + displayName);
		for(int i=0; i!= widgets.size(); i++) {
			if(widgets.get(i).displayName.compareTo(displayName)==0) {
				return widgets.get(i);
			}
		}
		return null;
	}
	
	public class Widget{
		public String name;
		public String displayName;
		public Class className;
		public JsonElement options;
		
		public Widget(String name, String displayName, Class className, JsonElement options) {
			this.name = name;
			this.displayName = displayName;
			this.className=className;
			this.options = options;
		}
		public Widget(String name, String displayName, Class className) {
			this.name = name;
			this.displayName = displayName;
			this.className=className;
			this.options = null;
		}
		
	}

}
