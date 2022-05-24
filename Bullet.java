import java.awt.*;
import javax.swing.*;


public class Bullet extends GameObject {
	Color col;
	Bullet(){
		width = 10; // variable containing bullet width
		height = 10; // variable containing bullet height
		
		x = 0; // empty for movement
		y = 0; // to be used later
		
		attribute = "bullet"; // used for printing
		col = Color.RED; // default bullet colour
	}

	public void update(){ // makes sure the ship bullet actually moves forward
		y -= 30;
	}

	public void makeColorGreen(){ // makes the seeker bullets green
		col = Color.GREEN;
	}

	public void makeColorBlue(){ // makes the alien and predator bullets blue
		col = Color.BLUE;
	}

	public void draw(Graphics g, Component c){ // actually draws the bullet for each alien
		g.setColor(col); // makes the colour unique to the object
		g.fillOval(x, y, width, height); // makes the visible bullet
	}
}
