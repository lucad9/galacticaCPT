import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {//listens for user mouse movements



    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) { //tracks the location x and y of the mouse
        int mx = e.getX();
        int my = e.getY();

        // playbutton
        if (mx >= 48 && mx <= 253) { //dimensions of button
            if (my >= 165 && my <= 222) {
                //pressed playbutton
                GalagaPanel.state = GalagaPanel.STATE.LEVELONE;            }
        }

        // exit button
        if (mx >= 48 && mx <= 253) {//dimensions of button
            if (my >= 246 && my <= 303) {
                //pressed exit button
                 System.exit(1);
            }
        }

        // Tutorial Button
        if (mx >= 48 && mx <= 368) {//dimensions of button
            if (my >= 320 && my <= 377) {
                //pressed Tutorial button
                GalagaPanel.state = GalagaPanel.STATE.TUTORIAL;
            }
        }

        //scores button
        if (mx >= 48 && mx <= 401) {//dimensions of button
            if (my >= 390 && my <= 447) {
                //pressed scores button
                GalagaPanel.state = GalagaPanel.STATE.SCORES;
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