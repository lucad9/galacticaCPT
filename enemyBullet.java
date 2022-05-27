import java.awt.*;
import javax.swing.*;


public class enemyBullet extends GameObject
{
    Color col;
    enemyBullet()
    {
        width = 10;
        height = 10;

        x = 0;
        y = 0;

        attribute = "bullet";
        col = Color.BLUE;
    }

    public void update()
    {
        y -= 10;
    }

    public void makeColor()
    {
        col = Color.GREEN;
    }
    public void draw(Graphics g, Component c)
    {
        g.setColor(col);
        g.fillOval(x, y, width, height);
    }
}
