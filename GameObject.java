import javax.swing.*;
import java.awt.*;

public class GameObject extends Rectangle{ // rectangle

	ImageIcon picture; // initiates the picture which will be written to later
	boolean alive; // defaults to alive for every alien; will be changed when they die
	String attribute = "nothing"; // can be edited later for printing statements

	GameObject(){ // holds x and y values for the rest of the game as well as width and height
		x= 200;
		y = 200;
		width = 50;
		height = 50;
		alive = true; // the default setting for every object
	}

	public void setPicture(ImageIcon p){
		picture = p; // sets the image for an object
	}

	public void draw(Graphics g, Component c){
		if(alive){ // while an alien is alive
			if(picture != null) // if it has a set image that is visible
				g.drawImage(picture.getImage(),x,y,width,height,c); // draw the image at the alien's specific coordinates
		}
	}

	public void kill(){ // when an alien is killed:
		x = 0; // move to 0,0
		y = 0;
		width = 0; // make its image invisible
		height = 0;
		System.out.println(attribute + " killed"); // print what died
		alive = false; // kill it.
	}
}