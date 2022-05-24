import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class galaga2 extends JFrame {

    Image image;
    Graphics graphics;

    Image stars = new ImageIcon("Stars.jpg").getImage();
    Image ship = new ImageIcon("Ship.png").getImage();
    Image alien = new ImageIcon("Alien1.png").getImage();
    Image heart = new ImageIcon("Life.png").getImage();

    //All speeds are pretty arbitrary. You can mess around with them.

    //Player
    int x=500;
    int y=580;
    int speed;
    int lives=3;

    //Bullet
    int bX;
    int bY;
    int bSpeed;
    int shooting;

    //Aliens
    int alienX[] = new int[10];
    int alienY[] = new int[10];
    int aDead[] = new int[10];
    int aSpeed[] = new int[10];
    Random random = new Random();

    Color TRANSGENDER = new Color(150, 30, 30, 100);

    public galaga2() {
        super();
        Handler keyses = new Handler();
        addKeyListener(keyses);
    }

    private class Handler implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if(keyCode == KeyEvent.VK_A) {
                speed=-5;
            }
            if(keyCode == KeyEvent.VK_D) {
                speed=5;
            }
            if(keyCode == KeyEvent.VK_SPACE) {
                shooting=1;
            }
        }
        public void keyTyped(KeyEvent e) {

        }
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_D)) {
                speed=0;
            }
            if(keyCode == KeyEvent.VK_SPACE) {

            }
        }

    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        paintComponent(graphics);
        g.drawImage(image, 0, 0, null);
        repaint();

    }

    public void paintComponent(Graphics g) {
        g.drawImage(stars, 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.WHITE);

        //Player
        g.drawImage(ship, x-50, y, 100, 100, null);
        x=x+speed;

        //Bullets/Shooting
        bY=bY+bSpeed;
        if(shooting!=0) {
            bSpeed=-10;
            g.setColor(Color.WHITE);
            g.fillRect(bX-10, bY, 20, 20);
        }
        if(shooting==0) {
            bX=x;
            bY=y+50;
            bSpeed=0;
        }

        if(bY<0) {
            shooting=0;
        }

        //Aliens
        for(int a=0; a<10; a++) {

            if(aSpeed[a]==0) {
                aSpeed[a]=random.nextInt(6);
            }
            if(alienX[a]==0) {
                alienX[a]=random.nextInt(900)+50;
                alienY[a]=-100;
            }
            if(aDead[a]==0) {
                g.drawImage(alien, alienX[a]-50, alienY[a], 100, 100, null);
                if((bX>alienX[a]-50) && (bX<alienX[a]+50) && (bY>alienY[a]) && (bY<alienY[a]+100)) {
                    shooting=0;
                    aDead[a]=1;
                }

                if(shooting!=0) {
                    if(alienX[a]>bX) {
                        alienX[a]=alienX[a]+(aSpeed[a]-2);
                    }
                    if(alienX[a]<bX) {
                        alienX[a]=alienX[a]-(aSpeed[a]-2);
                    }

                    if(alienY[a]>bY) {
                        alienY[a]=alienY[a]+(aSpeed[a]-2);
                    }
                    if(alienY[a]<bY) {
                        alienY[a]=alienY[a]-(aSpeed[a]-2);
                    }
                } else {
                    if(alienX[a]>x) {
                        alienX[a]=alienX[a]-aSpeed[a];
                    }
                    if(alienX[a]<x) {
                        alienX[a]=alienX[a]+aSpeed[a];
                    }

                    if(alienY[a]>y) {
                        alienY[a]=alienY[a]-aSpeed[a];
                    }
                    if(alienY[a]<y) {
                        alienY[a]=alienY[a]+aSpeed[a];
                    }
                }

                //Murdering you all up in the face
                if((alienX[a]>x-50) && (alienX[a]<x+50) && (alienY[a]>y-50) && (alienY[a]<y+100)) {

                    lives=lives-1;
                    aDead[a]=1;

                }
            }

        }
        //UI
        //g.drawString("shooting: "+shooting, 100, 100);
        //g.drawString("Lives: "+lives, 100, 115);
        g.drawString("WASD to move", 100, 130);
        g.drawString("SPACE to shoot", 100, 145);

        //Lives
        if(lives<=0) {
            g.setColor(TRANSGENDER);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("WHAT A LOSER!", 100, 160);
            speed=0;
        }
        g.setColor(Color.RED);
        if(lives>2) {
            g.drawImage(heart, 700, 20, 100, 100, null);
        }
        if(lives>1) {
            g.drawImage(heart, 800, 20, 100, 100, null);
        }
        if(lives>0) {
            g.drawImage(heart, 900, 20, 100, 100, null);
        }

    }

    public static void main(String args[]) {
        galaga2 game = new galaga2();
        game.setVisible(true);
        game.setSize(1000, 700);

    }


}