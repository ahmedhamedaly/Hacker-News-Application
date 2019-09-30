import processing.core.*;
public class RadioButton extends PApplet{
  int x,y;
  PImage tick;
  RadioButton(int widgetPositionX,int widgetPositionY){
    tick=loadImage("Tick.jpg");
    tick.resize(10,10);
    x = widgetPositionX;
    y = widgetPositionY;
  }
  void draw(Widget tp){
   fill(255);
   rect(x,y,10,10);
//   if (tp.activated==true) {
//	   image(tick,x,y);
//   }
  }
}