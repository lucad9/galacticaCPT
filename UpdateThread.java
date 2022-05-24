import javax.swing.*;

public class UpdateThread extends Thread{

	GalagaPanel panel; // imports the whole panel

	UpdateThread(GalagaPanel p){ // creates a function to update the panel and game graphics
		panel = p; // localizes the panel
	}

	public void run(){ // to be called in the main.java
		while(true){ // runs forever
			panel.update(); // updates the panel and all the game's graphics

			try{Thread.sleep(50);} // refreshes the while statement every 50 milliseconds thus updating the whole game's graphics
			catch(Exception e){} // makes sure everthing is alive with the code
		}
	}
}