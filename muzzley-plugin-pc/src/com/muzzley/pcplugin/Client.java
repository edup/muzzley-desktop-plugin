package com.muzzley.pcplugin;

public class Client {
	  String id_user;
	  String name;
	  
	  public Client(String id_user, String name) {
	    this.id_user = id_user;
	    this.name = name;
	  } 
	  
	  public String getName() {
	    return this.name;
	  } 
	  
	  public String getId() {
	    return this.id_user;
	  } 
} 