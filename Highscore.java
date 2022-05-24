package Galaga;

import java.awt.*;

public class Highscore {
    public Rectangle playButton = new Rectangle(GameObject.width/2+120, 150, 100, 50);

    public void render (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("High Score", GameObject.width / 2, 100);
    }
}
