import javax.swing.*;
import java.awt.*;

class Galaga extends JApplet{
	public static void main(String [] args){
		// creates a frame
		JFrame frame = new JFrame("Galactica: An Inter-Galactical War");

		// creates a panel, put it in frame
		GalagaPanel panel = new GalagaPanel();
		frame.getContentPane().add(panel);

		// sets frame size, make it visible
		frame.setSize(700,650);
		frame.setVisible(true);
	}
}