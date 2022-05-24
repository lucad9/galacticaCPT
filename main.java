package Galaga;

import javax.swing.*;
import java.awt.*;

class Galaga extends JApplet{
	public static void main(String [] args){
		//step 1, create a frame
		JFrame frame = new JFrame("Galactica: An Inter-Galactical War");

		//step 2, create a panel, put it in frame
		GalagaPanel panel = new GalagaPanel();
		frame.getContentPane().add(panel);

		//step 3, set frame size, make it visible
		frame.setSize(700,650);
		frame.setVisible(true);
	}
}