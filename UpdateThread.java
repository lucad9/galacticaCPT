import javax.swing.*;

public class UpdateThread extends Thread{

	GalagaPanel panel;

	UpdateThread(GalagaPanel p){
		panel = p;
	}

	public void run(){
		while(true){
			panel.update();

			//wait awhile
			try{Thread.sleep(50);}
			catch(Exception e){}
		}
	}
}