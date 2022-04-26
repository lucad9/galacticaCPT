import javax.swing.*;
import java.awt.*;

public class GameObject extends Rectangle {

	ImageIcon picture;
	boolean alive;
	String attribute = "nothing";
	
	GameObject(){
		x= 200;
		y = 200;
		width = 50;
		height = 50;
		alive = true;
	}

	public void setPicture(ImageIcon p){
		picture = p;
	}

	public void draw(Graphics g, Component c){
		if(alive){
			if(picture != null)
				g.drawImage(picture.getImage(),x,y,width,height,c);
			else{
				g.setColor(Color.BLUE);
				g.fillRect(x,y,width,height);
			}
		}
	}
	
	public void kill(){
			x = 0;
			y = 0;
			width = 0;
			height = 0;
			System.out.println(attribute + " killed");
			alive = false;
	}

	public void update(){
		x += 1;
		y += 1;
	}
}