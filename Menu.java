



import javax.swing.*;
import java.awt.*;

//whole code is only for shaping where the hit boxes of the menu are acccuratly

public class Menu {
    public Rectangle playButton = new Rectangle(48, 165, 205, 57);
    public Rectangle ScoresButton = new Rectangle(48, 246, 205, 57);
    public Rectangle TutorialButton = new Rectangle(48, 320, 320, 57);

    public Rectangle ExitButton = new Rectangle(48, 390, 353, 57);

    public void render (Graphics g){

        Graphics2D g2d = (Graphics2D) g;
/*
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("SPACE GAME", GameObject.width/2, 100);
*/
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("play", playButton.x+19, playButton.y+30);
        g2d.draw(playButton);

        g.drawString("Scores", ScoresButton.x+19, ScoresButton.y+30);
        g2d.draw(ScoresButton);

        g.drawString("Tutorial", TutorialButton.x+19, TutorialButton.y+30);
        g2d.draw(TutorialButton);

        g.drawString("Exit", ExitButton.x + 19, ExitButton.y + 30);
        g2d.draw(ExitButton);

        g2d.draw(ExitButton);
        g2d.draw(playButton);
        g2d.draw(ScoresButton);
        g2d.draw(TutorialButton);

    }
}
