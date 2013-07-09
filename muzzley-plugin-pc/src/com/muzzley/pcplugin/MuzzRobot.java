package com.muzzley.pcplugin;

import java.awt.Point;
import java.awt.Robot;
import java.io.PrintStream;



public class MuzzRobot
{
  Robot robot;
  Keyboard keyboard;
  int last_x = -1;
  int last_y = -1;
  int speed = 0;
  

  int center_point_x = 0;
  int center_point_y = 0;
  

  boolean levantou_dedo_x = false;
  boolean levantou_dedo_y = false;
  
  public MuzzRobot()
  {
    try {
      System.out.println("New Robot created...");
      this.robot = new Robot();
      this.keyboard = new Keyboard();


    }
    catch (Exception e)
    {


      e.printStackTrace();
    } 
  } 
  
  public int getNewX(int x) {
    if (this.last_x == -1) {
      this.last_x = x;
      this.center_point_x = x;
    } 
    

    if (this.levantou_dedo_x) {
      this.last_x = x;
      this.levantou_dedo_x = false;
      this.click_last_point_x = this.center_point_x;
      return this.center_point_x;
    } 
    


    int position = this.center_point_x;
    if (x > this.last_x) {
      this.speed = (x - this.last_x);
      position = this.center_point_x + this.speed;
    }
    else if (x < this.last_x) {
      this.speed = (this.last_x - x);
      position = this.center_point_x - this.speed;
    } 
    
    this.last_x = x;
    this.center_point_x = position;
    
    if (this.center_point_x < 0) {
      this.center_point_x = 0;
      position = 0;
    } 
    

    return position;
  } 
  
  public int getNewY(int y) {
    if (this.last_y == -1) {
      this.last_y = y;
      this.center_point_y = y;
    } 
    

    if (this.levantou_dedo_y) {
      this.last_y = y;
      this.levantou_dedo_y = false;
      this.click_last_point_y = this.center_point_y;
      return this.center_point_y;
    } 
    

    int position = this.center_point_y;
    if (y > this.last_y) {
      this.speed = (y - this.last_y);
      position = this.center_point_y + this.speed;
    }
    else if (y < this.last_y) {
      this.speed = (this.last_y - y);
      position = this.center_point_y - this.speed;
    } 
    
    this.last_y = y;
    this.center_point_y = position;
    
    if (this.center_point_y < 0) {
      this.center_point_y = 0;
      position = 0;
    } 
    



    return position;
  } 
  
  public void mouseMove(int x, int y) {
    x = getNewX(x);
    y = getNewY(y);
    try
    {
      this.robot.mouseMove(x, y);
    }
    catch (Exception e) {
      e.printStackTrace();
    } 
  } 
  
  int dbl_click_n = 0;
  long last_time_touch_event = 0L;
  boolean mouse_is_down = false;
  

  int click_last_point_x = -1;
  int click_last_point_y = -1;
  int n_times = 0;
  long last_click_press = 0L;
  public void touchEvent(int value) {
    boolean event_click = false;
    long now = System.currentTimeMillis();
    
    if (value == 1) {
      this.levantou_dedo_x = true;
      this.levantou_dedo_y = true;
    } 
    
    if ((this.center_point_x == this.click_last_point_x) && (this.center_point_y == this.click_last_point_y)) {
      event_click = true;
    } 
    
    if (event_click)
    {
      if (value == 0) {
        System.out.println("click down");
      }
      else {
        System.out.println("click up");
      } 
      if ((value == 0) && (now - this.last_click_press < 100L) && (!this.mouse_is_down)) {
        mousePress(0);
        this.mouse_is_down = true;
      } 
      
      if (value == 1) {
        if (!this.mouse_is_down) {
          mousePress(0);
        } 
        mouseRelease(0);
        this.mouse_is_down = false;
        this.last_click_press = System.currentTimeMillis();
      } 
    } 
  } 
  




  public void mousePress(int button_id)
  {
    int button = 0;
    if (button_id == 0) button = 1024; 
    if (button_id == 1) button = 4096; 
    if (button_id == 2) button = 2048; 
    
    this.robot.mousePress(button);
  } 
  public void mouseRelease(int button_id) {
    int button = 0;
    if (button_id == 0) button = 1024; 
    if (button_id == 1) button = 4096; 
    if (button_id == 2) button = 2048; 
    
    this.robot.mouseRelease(button);
  } 
  
  public void keyEvent(int code) {
    System.out.println("Key writing: " + (char)code + "|" + code);
    //write((char)code);
  } 
  
  public void keyReset(int key_value){
	  keyEvent(key_value, 2);
  }
  
  public void keyEvent(int code, int event_type)
  {
	 if(code==-1) return;
	 write(code, event_type);
  } 
  
  /*
  private void write(char character) {
    this.keyboard.type(character);
  }*/
  
  private void write(int character, int event_type) {
    this.keyboard.type(character, event_type);
  } 
} 
