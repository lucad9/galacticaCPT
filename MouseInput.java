package Galaga;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

   // int Numberx = 0;
    //int Number = Numbers(Numberx);

  //  public static int Numbers(int N){
    //    N = 0;
  //      return N;
  //  }

int Number = 1;

Numbers s1;
//s1 = new  Numbers();
//s1.SetNumbers(1, Number);

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // playbutton
        if (mx >= GameObject.width / 2 + 120 && mx <= GameObject.width / 2 + 220) {
            if (my >= 150 && my <= 200) {
                //pressed playbutton
                GalagaPanel.state = GalagaPanel.STATE.LEVELONE;            }
        }

        // Score Button
        if (mx >= GameObject.width / 2 + 120 && mx <= GameObject.width / 2 + 220) {
            if (my >= 250 && my <= 300) {
                //pressed score button
                GalagaPanel.state = GalagaPanel.STATE.SCORES;
            }
        }

        // Tutorial Button
        if (mx >= GameObject.width / 2 + 120 && mx <= GameObject.width / 2 + 220) {
            if (my >= 350 && my <= 400) {
                //pressed Tutorial button
                GalagaPanel.state = GalagaPanel.STATE.TUTORIAL;
            }
        }

        //Quit button
        if (mx >= GameObject.width / 2 + 120 && mx <= GameObject.width / 2 + 220) {
            if (my >= 450 && my <= 500) {
                //pressed Quite button
                System.exit(1);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
