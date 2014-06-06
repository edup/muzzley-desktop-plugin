package com.desktop.tools;

import java.awt.Robot;

public class Keyboard
{
  private Robot robot;
  
  public Keyboard() throws java.awt.AWTException
  {
    this.robot = new Robot();
  } 
  
  public Keyboard(Robot robot) {
    this.robot = robot;
  } 
  
  public void type(CharSequence characters) {
    int length = characters.length();
    for (int i = 0; i < length; i++) {
      char character = characters.charAt(i);
      type(character);
    } 
  } 
  
  public void type(int character, int event_type) {
    //System.out.println("INT_CHAR: " + character);
    int key = 0;
    key = character;
    
    if (event_type == 1) {
      this.robot.keyPress(key);
    }
    else if (event_type == 2) {
      this.robot.keyRelease(key);
    } 
  } 
  
  public void type(char character) {
    switch (character) {
    case 'a':  doType(new int[] { 65 });break;
    case 'b':  doType(new int[] { 66 });break;
    case 'c':  doType(new int[] { 67 });break;
    case 'd':  doType(new int[] { 68 });break;
    case 'e':  doType(new int[] { 69 });break;
    case 'f':  doType(new int[] { 70 });break;
    case 'g':  doType(new int[] { 71 });break;
    case 'h':  doType(new int[] { 72 });break;
    case 'i':  doType(new int[] { 73 });break;
    case 'j':  doType(new int[] { 74 });break;
    case 'k':  doType(new int[] { 75 });break;
    case 'l':  doType(new int[] { 76 });break;
    case 'm':  doType(new int[] { 77 });break;
    case 'n':  doType(new int[] { 78 });break;
    case 'o':  doType(new int[] { 79 });break;
    case 'p':  doType(new int[] { 80 });break;
    case 'q':  doType(new int[] { 81 });break;
    case 'r':  doType(new int[] { 82 });break;
    case 's':  doType(new int[] { 83 });break;
    case 't':  doType(new int[] { 84 });break;
    case 'u':  doType(new int[] { 85 });break;
    case 'v':  doType(new int[] { 86 });break;
    case 'w':  doType(new int[] { 87 });break;
    case 'x':  doType(new int[] { 88 });break;
    case 'y':  doType(new int[] { 89 });break;
    case 'z':  doType(new int[] { 90 });break;
    case 'A':  doType(new int[] { 16, 65 });break;
    case 'B':  doType(new int[] { 16, 66 });break;
    case 'C':  doType(new int[] { 16, 67 });break;
    case 'D':  doType(new int[] { 16, 68 });break;
    case 'E':  doType(new int[] { 16, 69 });break;
    case 'F':  doType(new int[] { 16, 70 });break;
    case 'G':  doType(new int[] { 16, 71 });break;
    case 'H':  doType(new int[] { 16, 72 });break;
    case 'I':  doType(new int[] { 16, 73 });break;
    case 'J':  doType(new int[] { 16, 74 });break;
    case 'K':  doType(new int[] { 16, 75 });break;
    case 'L':  doType(new int[] { 16, 76 });break;
    case 'M':  doType(new int[] { 16, 77 });break;
    case 'N':  doType(new int[] { 16, 78 });break;
    case 'O':  doType(new int[] { 16, 79 });break;
    case 'P':  doType(new int[] { 16, 80 });break;
    case 'Q':  doType(new int[] { 16, 81 });break;
    case 'R':  doType(new int[] { 16, 82 });break;
    case 'S':  doType(new int[] { 16, 83 });break;
    case 'T':  doType(new int[] { 16, 84 });break;
    case 'U':  doType(new int[] { 16, 85 });break;
    case 'V':  doType(new int[] { 16, 86 });break;
    case 'W':  doType(new int[] { 16, 87 });break;
    case 'X':  doType(new int[] { 16, 88 });break;
    case 'Y':  doType(new int[] { 16, 89 });break;
    case 'Z':  doType(new int[] { 16, 90 });break;
    case '`':  doType(new int[] { 192 });break;
    case '0':  doType(new int[] { 48 });break;
    case '1':  doType(new int[] { 49 });break;
    case '2':  doType(new int[] { 50 });break;
    case '3':  doType(new int[] { 51 });break;
    case '4':  doType(new int[] { 52 });break;
    case '5':  doType(new int[] { 53 });break;
    case '6':  doType(new int[] { 54 });break;
    case '7':  doType(new int[] { 55 });break;
    case '8':  doType(new int[] { 56 });break;
    case '9':  doType(new int[] { 57 });break;
    case '-':  doType(new int[] { 45 });break;
    case '=':  doType(new int[] { 61 });break;
    case '~':  doType(new int[] { 16, 192 });break;
    case '!':  doType(new int[] { 517 });break;
    case '@':  doType(new int[] { 512 });break;
    case '#':  doType(new int[] { 520 });break;
    case '$':  doType(new int[] { 515 });break;
    case '%':  doType(new int[] { 16, 53 });break;
    case '^':  doType(new int[] { 514 });break;
    case '&':  doType(new int[] { 150 });break;
    case '*':  doType(new int[] { 151 });break;
    case '(':  doType(new int[] { 519 });break;
    case ')':  doType(new int[] { 522 });break;
    case '_':  doType(new int[] { 523 });break;
    case '+':  doType(new int[] { 521 });break;
    case '\t':  doType(new int[] { 9 });break;
    case '\n':  doType(new int[] { 10 });break;
    case '[':  doType(new int[] { 91 });break;
    case ']':  doType(new int[] { 93 });break;
    case '\\':  doType(new int[] { 92 });break;
    case '{':  doType(new int[] { 16, 91 });break;
    case '}':  doType(new int[] { 16, 93 });break;
    case '|':  doType(new int[] { 16, 92 });break;
    case ';':  doType(new int[] { 59 });break;
    case ':':  doType(new int[] { 513 });break;
    case '\'':  doType(new int[] { 222 });break;
    case '"':  doType(new int[] { 152 });break;
    case ',':  doType(new int[] { 44 });break;
    case '<':  doType(new int[] { 153 });break;
    case '.':  doType(new int[] { 46 });break;
    case '>':  doType(new int[] { 160 });break;
    case '/':  doType(new int[] { 47 });break;
    case '?':  doType(new int[] { 16, 47 });break;
    case ' ':  doType(new int[] { 32 });break;
    case '\013': case '\f': case '\r': case '\016': case '\017': case '\020': case '\021': case '\022': 
    case '\023': case '\024': case '\025': case '\026': case '\027': case '\030': case '\031': 
    case '\032': case '\033': case '\034': case '\035': case '\036': case '\037': default: 
      System.out.println("Key no found: " + character);
      throw new IllegalArgumentException("Cannot type character " + character);
    } 
  } 
  
  private void doType(int[] keyCodes) {
    doType(keyCodes, 0, keyCodes.length);
  } 
  
  private void doType(int[] keyCodes, int offset, int length) {
    if (length == 0) {
      return;
    } 
    
    this.robot.keyPress(keyCodes[offset]);
    doType(keyCodes, offset + 1, length - 1);
    this.robot.keyRelease(keyCodes[offset]);
  } 
  
} 