import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GalagaPanel extends JPanel implements KeyListener
{

	//images
	ImageIcon space, shippic, dumbalien, pred, seekpic;

	//list of all objects in game
	LinkedList<Alien> masterList;

	//ship
	Ship ship;
	Bullet bullet;
	
	int dead, listlength;
	
	GalagaPanel()
	{
		//load images, data, whatever
		space = new ImageIcon("space.gif");
		shippic = new ImageIcon("player.png");
		dumbalien = new ImageIcon("enemy_0.png");
		pred = new ImageIcon("enemy_1.png");

		//add some aliens to game
		masterList = new LinkedList<Alien>();
		for(int i=0; i<3; i++)
		{
			Alien a = new Alien();
			a.setPicture(dumbalien);
			masterList.add(a);
		}
		
		CircleAlien ca = new CircleAlien();
		ca.setPicture(dumbalien);
		masterList.add(ca);
				
		//add a ship to the game
		ship = new Ship();
		ship.setPicture(shippic);
		ship.x = 200;
		ship.y = 400;
	
		Predator p1 = new Predator();
		p1.setPicture(pred);
		p1.setPrey(ship);
		masterList.add(p1);
		
		Seeker seek = new Seeker();
		seekpic = new ImageIcon("enemy_2.png");
		seek.setPicture(seekpic);
		seek.setPrey(ship);
		masterList.add(seek);
		
		Predator p2 = new Predator();
		p2.setPicture(pred);
		p2.setPrey(ship);
		masterList.add(p2);
		
		bullet = new Bullet();

		UpdateThread ut = new UpdateThread(this);
		ut.start();
		
		
		//stupid key listener stuff
		addKeyListener(this);
		setFocusable(true);
	}


	public void paintComponent(Graphics g)
	{
		//clear screen
		g.drawImage(space.getImage(),0,0,getWidth(),getHeight(),this);

		//draw all objects in game
		for( Alien go : masterList )
		{
			go.draw(g,this);
			listlength++;
			go.bullet.draw(g, this);
			if(!go.alive)
				dead++;
		}
		ship.draw(g, this);
		bullet.draw(g, this);
		if(ship.alive == false)
		{
			g.setFont(new Font("sansserif", Font.BOLD, 32));
			g.drawString("GAME OVER!!!!", 150, 300);
		}
		if(dead == listlength)
		{
			g.setFont(new Font("sansserif", Font.BOLD, 32));
			g.drawString("GAME WON!!!!", 150, 300);
		}
		else
		{
			listlength = 0;
			dead = 0;
		}
	}


	public void update()
	{
		//update all objects in game
		for( Alien go : masterList )
		{
			go.update();
			if(bullet.intersects(go))
			{
				go.kill();
			}
			
			if(go.intersects(ship) && !go.attribute.equalsIgnoreCase("ship"))
			{
				ship.kill();
				System.out.println(go.attribute + " killed you");
			}
			if(go.bullet.intersects(ship))
			{
				ship.kill();
				System.out.println("Shot by " + go.attribute);
			}
			
		}
		
		//check for bullet shot aliens
		
		bullet.update();
		ship.update();
		
		repaint();
	}

	public void keyPressed(KeyEvent k)
	{
		char c = k.getKeyChar();
		
		if( k.getKeyCode() == KeyEvent.VK_RIGHT )
			ship.dx = 5;
		if( k.getKeyCode() == KeyEvent.VK_LEFT )
			ship.dx = -5;
		if(c == ' ')
		{
			bullet.x = ship.x;
			bullet.y = ship.y - 30;
		}
	}

	public void keyReleased(KeyEvent k)
	{
		if( k.getKeyCode() == KeyEvent.VK_LEFT ||  k.getKeyCode() == KeyEvent.VK_RIGHT )
			ship.dx = 0;
	}

	public void keyTyped(KeyEvent k)
	{
	}

}