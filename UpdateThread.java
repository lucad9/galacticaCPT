import javax.swing.*;

public class UpdateThread extends Thread{

	GalagaPanel panel;

	UpdateThread(GalagaPanel p){ // creates a function to update the panel
		panel = p;
	}

	public void run(){ // runs
		while(true){ // permanently runs
			panel.update(); // updates the panel

			try{Thread.sleep(50);} // refreshes the while statement every 50 milliseconds
			catch(Exception e){}
		}
	}
}