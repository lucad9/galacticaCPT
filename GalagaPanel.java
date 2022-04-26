import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GalagaPanel extends JPanel implements KeyListener{

	//images
	ImageIcon space, playership, purpleship, pred, bugship;

	//list of all objects in game
	LinkedList<Alien> masterList;

	//ship
	Ship ship;
	Bullet bullet;
	
	int dead, listlength;
	
	GalagaPanel(){
		//load images, data, whatever
		space = new ImageIcon("space.gif");
		playership = new ImageIcon("player.png");
		purpleship = new ImageIcon("enemy_0.png");
		pred = new ImageIcon("enemy_1.png");
		bugship = new ImageIcon("enemy_2.png");


		//add a ship to the game
		ship = new Ship();
		ship.setPicture(playership);
		ship.x = 200;
		ship.y = 400;


		// this one adds the purple aliens at the top
		masterList = new LinkedList<Alien>();
		for(int i=0; i<3; i++){
			Alien a = new Alien();
			a.setPicture(purpleship);
			masterList.add(a);
		}


		Predator p1 = new Predator();
		p1.setPicture(pred);
		p1.setPrey(ship);
		masterList.add(p1);


		// this one adds the small funky looking bug alien
		Seeker seek = new Seeker();
		seek.setPicture(bugship);
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


	public void paintComponent(Graphics g) {
		//clear screen
		g.drawImage(space.getImage(),0,0,getWidth(),getHeight(),this);

		//draw all objects in game
		for( Alien go : masterList ) {
			go.draw(g,this);
			listlength++;
			go.bullet.draw(g, this);
			if(!go.alive)
				dead++;
		}

		ship.draw(g, this);
		bullet.draw(g, this);

		if(ship.alive == false) {
			g.setFont(new Font("sansseriff", Font.BOLD, 32));
			g.drawString("You died!", 150, 300);
		}

		if(dead == listlength){
			g.setFont(new Font("sansseriff", Font.BOLD, 32));
			g.drawString("You won!", 150, 300);
		}

		else{
			listlength = 0;
			dead = 0;
		}
	}


	public void update(){
		//update all objects in game
		for(Alien go : masterList){
			go.update();
			if(bullet.intersects(go)){
				go.kill();
			}
			
			if(go.intersects(ship) && !go.attribute.equalsIgnoreCase("ship")){
				ship.kill();
			}

			if(go.bullet.intersects(ship)){
				ship.kill();
			}
			
		}
		
		//check for bullet shot aliens
		
		bullet.update();
		ship.update();
		
		repaint();
	}

	// checks the keyboard for input to move
	public void keyPressed(KeyEvent k) {
		char c = k.getKeyChar();

		if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			ship.dx = 10;
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){
			ship.dx = -10;
		}

		if(c == ' '){
			bullet.x = ship.x;
			bullet.y = ship.y - 60;
		}

		if(k.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_LEFT ||  k.getKeyCode() == KeyEvent.VK_RIGHT)
			ship.dx = 0;
	}

	public void keyTyped(KeyEvent k)
	{
	}
}