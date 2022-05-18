	import javax.swing.*;
	import java.awt.*;
	import java.util.*;
	import java.awt.event.*;

public class GalagaPanel extends JPanel implements KeyListener{

	//images
	ImageIcon space, playership, purpleship, pred, bugship;
	Image l2, l3, l4, lM;

	//list of all objects in game
	LinkedList<Alien> masterList;

	//ship
	Ship ship;
	Bullet bullet;
	
	int dead, listlength, levelcount;
	boolean spawned = false;
	boolean spawned2 = false;
	boolean spawned3 = false;

	Toolkit t=Toolkit.getDefaultToolkit();

	GalagaPanel(){
		//load images, data, whatever
		space = new ImageIcon("space.gif");
		playership = new ImageIcon("player.png");
		purpleship = new ImageIcon("enemy_0.png");
		pred = new ImageIcon("enemy_1.png");
		bugship = new ImageIcon("enemy_2.png");

		//load images for the level badges
		lM = t.getImage("badge_1.png");
		l2 = t.getImage("badge_2.png");
		l3 = t.getImage("badge_3.png");
		l4 = t.getImage("badge_4.png");


		//add a ship to the game
		ship = new Ship();
		ship.setPicture(playership);
		ship.x = 317;
		ship.y = 480;


		// this one adds the purple aliens at the top
		masterList = new LinkedList<Alien>();
		for(int i=0; i<2; i++){
			Alien a = new Alien();
			a.setPicture(purpleship);
			masterList.add(a);
		}


		Predator p1 = new Predator();
		p1.setPicture(pred);
		p1.setPrey(ship);
		masterList.add(p1);

		
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



		// draw badge here
		g.drawImage(lM, 10, 10, this);

		g.setFont(new Font("sansseriff", Font.BOLD, 32));
		g.drawString("Score:", 535, 50);

		//setting up the score
		int score = dead;
		if(dead >= 25){
			score = 25;
		}
		String s = Integer.toString(score);
		g.drawString(s, 645, 50);


		if(ship.alive == false) {
			g.setFont(new Font("sansseriff", Font.BOLD, 32));
			g.drawString("You died!", 260, 325);
		}

		levelcount = 1;
		if(score == 3){
			levelcount +=1;
		}

		if(levelcount == 2){
			if (!this.spawned) {
				for(int i=0;i<3;i++){
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				//spawns the annoying bug one
				Seeker seek = new Seeker();
				seek.setPicture(bugship);
				seek.setPrey(ship);
				masterList.add(seek);

				spawned = true;
			}
			lM = l2;
		}

		if(score == 7){
			levelcount = 3;
		}


		if(levelcount == 3){
			if (!this.spawned2) {
				for(int i=0;i<4;i++){
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				Seeker seek = new Seeker();
				seek.setPicture(bugship);
				seek.setPrey(ship);
				masterList.add(seek);

				for(int i=0;i<2;i++) {
					Predator p1 = new Predator();
					p1.setPicture(pred);
					p1.setPrey(ship);
					masterList.add(p1);
				}

				spawned2 = true;
			}
			lM = l3;
		}

		if(score == 14){
			levelcount = 4;
		}

		if(levelcount == 4){
			if (!this.spawned3) {
				for(int i=0;i<6;i++){
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				for(int i=0;i<2;i++) {
					Seeker seek = new Seeker();
					seek.setPicture(bugship);
					seek.setPrey(ship);
					masterList.add(seek);
				}

				for(int i=0;i<3;i++) {
					Predator p1 = new Predator();
					p1.setPicture(pred);
					p1.setPrey(ship);
					masterList.add(p1);
				}

				spawned3 = true;
			}
			lM = l4;
		}

		if(score >= 25){
			g.setFont(new Font("sansseriff", Font.BOLD, 32));
			g.drawString("You won!",260,325);
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
			if(ship.x > 624){
				ship.dx = -5;
			}
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){
			ship.dx = -10;
			if(ship.x < 1){
				ship.dx = 5;
			}
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
	public void keyTyped(KeyEvent k) // our code would not work without this. cannot figure out why
	{
	}
}