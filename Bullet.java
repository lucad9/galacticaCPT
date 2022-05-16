import java.awt.*;
import javax.swing.*;


public class Bullet extends GameObject {
	Color col;
	Bullet(){
		width = 10;
		height = 10;
		
		x = 0;
		y = 0;
		
		attribute = "bullet";
		col = Color.WHITE;
	}
	
	public void update(){
		y -= 30;
	}

	public void makeColorGreen(){
		col = Color.GREEN;
	}

	public void makeColorBlue(){
		col = Color.BLUE;
	}

	public void draw(Graphics g, Component c){
		g.setColor(col);
		g.fillOval(x, y, width, height);
	}
}
