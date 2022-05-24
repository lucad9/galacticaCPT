package Galaga;

import Galaga.GameObject;

import javax.swing.*;
import java.awt.*;


public class Menu {

    public Rectangle playButton = new Rectangle(GameObject.width/2+150, 150, 100, 50);
    public Rectangle ScoresButton = new Rectangle(GameObject.width/2+150, 250, 100, 50);
    public Rectangle TutorialButton = new Rectangle(GameObject.width/2+170, 350, 100, 50);

    public Rectangle ExitButton = new Rectangle(GameObject.width / 2 + 120, 450, 100, 50);

    public void render (Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("SPACE GAME", GameObject.width/2, 100);

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