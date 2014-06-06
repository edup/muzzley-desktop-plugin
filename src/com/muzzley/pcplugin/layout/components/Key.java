package com.muzzley.pcplugin.layout.components;

public class Key {
	int value;
	String description;
	
	public Key(int value, String description) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.description = description;
	}
	
	public String toString(){
		return description;
	}
	
	public int getValue(){
		return value;
	}

	public String getDescription(){
		return description;
	}
}
