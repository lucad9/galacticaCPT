	import javax.swing.*;
	import java.awt.*;
	import java.util.*;
	import java.awt.event.*;

public class GalagaPanel extends JPanel implements KeyListener{
/* the "extends took a really long time to figure out, but from (extending) JPanel we are implements (using) KeyListener
   once we learned about this, we used extends a couple more times in this file
 */

	// creating variables images
	ImageIcon space, playership, purpleship, pred, bugship; // ships
	Image l2, l3, l4, lM; // level badges

	LinkedList<Alien> masterList; // list of all aliens in game

	Ship ship; // imports ship class
	Bullet bullet; // imports bullet class
	
	int dead, listlength, levelcount; // creates variables to track actions
	boolean spawned = false; // level 2 logic
	boolean spawned2 = false; // level 3 logic
	boolean spawned3 = false; // level 4 logic

	Toolkit t=Toolkit.getDefaultToolkit(); // imports the default graphics toolkit

	GalagaPanel(){ // creates a class to be used in the main.java file
		space = new ImageIcon("space.gif"); // sets the space variable to the background gif
		playership = new ImageIcon("player.png"); // sets the player icon to the ship
		purpleship = new ImageIcon("enemy_0.png"); // generic Alien sprite
		pred = new ImageIcon("enemy_1.png"); // generic Predator sprite
		bugship = new ImageIcon("enemy_2.png"); // generic Seeker sprite

		// load images for the level badges
		lM = t.getImage("badge_1.png"); // level 1 badge that is able to be rewritten by the other badges`
		l2 = t.getImage("badge_2.png");
		l3 = t.getImage("badge_3.png");
		l4 = t.getImage("badge_4.png");

		ship = new Ship(); // new ship from the ship class
		ship.setPicture(playership); // uses a method within the ship class to set the image to the ship
		ship.x = 317; // sets ship pos to the middle of the screen
		ship.y = 480; // sets ship pos to the bottom of the screen


		// this one adds the purple aliens at the top
		masterList = new LinkedList<Alien>(); // makes a linkedlist that adds aliens, but also connects back to the masterlist
		for(int i=0; i<2; i++){ // adds two aliens
			Alien a = new Alien(); // alien class called
			a.setPicture(purpleship); // sets the picture to the main alien sprite
			masterList.add(a); // adds each alien to the masterclass
		}

		Predator p1 = new Predator(); // adds a predator
		p1.setPicture(pred); // sets the picture to the predator sprite
		p1.setPrey(ship); // makes the prey the ship, meaning its values will be equal to the player's
		masterList.add(p1); // adds to masterlist

		bullet = new Bullet(); // imports bullets for the user

		UpdateThread ut = new UpdateThread(this); // imports the updatethread which makes the graphics update
		ut.start(); // uses the updatethread class to update the game's graphics

		addKeyListener(this); // imports the key listener
		setFocusable(true); // jpanel method that makes the game focusable
	}


	public void paintComponent(Graphics g) { // uses methods from graphics ; contains most of the code for the levels n stuff
		g.drawImage(space.getImage(),0,0,getWidth(),getHeight(),this); // draws the gif across the screen

		for(Alien go:masterList){ // for each alien in the masterlist
			go.draw(g,this); // draw the alien at its coordinates
			listlength++; // add value to listlength int
			go.bullet.draw(g, this); // draw bullets
			if(!go.alive) // if the alien dies
				dead++; // add value to the death count
		}

		ship.draw(g, this); // draw the user ship
		bullet.draw(g, this); // draw the user bullet


		// draw badge here
		g.drawImage(lM, 10, 10, this);

		// setting up the score
		g.setFont(new Font("sansseriff", Font.BOLD, 32)); // set font
		g.drawString("Score:", 535, 50); // draw score string
		int score = dead; // makes score = dead each time the game updates
		if(dead >= 25){ // if there are 25 dead aliens (basically if the game has been won)
			score = 25; // keeps score at 25 cuz if this isnt here then the score increases infinitely
		}
		String s = Integer.toString(score); // changes the score to a string so it can be printed
		g.drawString(s, 645, 50); // print the score next to the "score" string


		if(ship.alive == false) { // when ship dies
			g.setFont(new Font("sansseriff", Font.BOLD, 32)); // set font
			g.drawString("You died!", 260, 325); // print on screen that you died
		}

		musicPlayer player = new musicPlayer();
		
		levelcount = 1; // starts the level count value
		
		player.play("darudeSandstorm.wav");
		
		if(score == 3){ // if 3 aliens are dead (level 1 beaten)
			levelcount +=1; // next level
		}

		if(levelcount == 2){ // if user beat 3 aliens / cleared level 1
			player.play("fleetingFrozenHeart.wav");
			if (!this.spawned) { // the code would spawn an infinite amount of aliens if this failsafe wasn't here
				for(int i=0;i<3;i++){ // spawns 3 aliens
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				Seeker seek = new Seeker(); // spawns a seeker that has bullets that chase the player
				seek.setPicture(bugship);
				seek.setPrey(ship); // sets the bullet prey to the ship's coordinates
				masterList.add(seek);

				spawned = true; // closes the failsafe
			}
			lM = l2; // overwrites the level badge to level 2
		}

		if(score == 7){ // if the player beat level 2
			levelcount = 3; // advance to level 3
		}

		if(levelcount == 3){ // if its level 3
			player.play("hereToYou.wav");
			if (!this.spawned2) { // new failsafe instead of overwriting the old one
				for(int i=0;i<4;i++){ // spawns 4 purple ones
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				Seeker seek = new Seeker(); // spawns a seeker
				seek.setPicture(bugship);
				seek.setPrey(ship);
				masterList.add(seek);

				for(int i=0;i<2;i++) { // spawns 2 predators
					Predator p1 = new Predator();
					p1.setPicture(pred);
					p1.setPrey(ship);
					masterList.add(p1);
				}

				spawned2 = true; // new failsafe is stopped
			}
			lM = l3; // level badge = level 3
		}

		if(score == 14){ // if the player beats level 3
			levelcount = 4; // start level 4
		}

		if(levelcount == 4){ // if its level 4
			player.play("epicFinalLevelMusic.wav");
			if (!this.spawned3) { // the next and final failsafe so a bunch of aliens don't spawn infinitely
				for(int i=0;i<6;i++){ // spawns 6 (SIX!!!) of the purple aliens
					Alien a = new Alien();
					a.setPicture(purpleship);
					masterList.add(a);
				}

				for(int i=0;i<2;i++) { // spawns 2 seekers
					Seeker seek = new Seeker();
					seek.setPicture(bugship);
					seek.setPrey(ship);
					masterList.add(seek);
				}

				for(int i=0;i<3;i++) { // spawns 3 predators
					Predator p1 = new Predator();
					p1.setPicture(pred);
					p1.setPrey(ship);
					masterList.add(p1);
				}

				spawned3 = true; // switches on the failsafe so it doesnt spawn infinitely again
			}
			lM = l4; // level badge = level 4
		}

		if(score >= 25){ // if all the aliens are killed (25 aliens in the game)
			g.setFont(new Font("sansseriff", Font.BOLD, 32));
			g.drawString("You won!",260,325); // prints "you won!" in the middle of your screen
		}

		else{ // prevents the game from literally going haywire
			listlength = 0; // keeps listlength at zero if an alien is killed (it'll increase infinitely otherwise)
			dead = 0; // same as above, different variable
		}
	}


	public void update(){ // updates each frame of the game and checks each alien and the ship for change
		for(Alien go : masterList){ // repeats continually for each alien in the list
			go.update(); // updates the masterlist continually
			if(bullet.intersects(go)){ // if a bullet hits an alien
				go.kill(); // Kill it!
			}
			
			if(go.intersects(ship) && !go.attribute.equalsIgnoreCase("ship")){ // if an alien hits the ship (predators specifically)
				ship.kill(); // Kill it!
			}

			if(go.bullet.intersects(ship)){ // if an alien bullet from the masterlist hits the ship
				ship.kill(); // Kill it!
			}
		}

		bullet.update(); // updates the bullet and checks if it hit an alien
		ship.update(); // updates the ship and checks if an alien bullet / alien has hit it
		
		repaint(); // repaints (refreshes the visuals of) the game
	}

	// checks the keyboard for input to move
	public void keyPressed(KeyEvent k) { // more keylistener things used for user input
		char c = k.getKeyChar(); // used to check if the spacebar is pressed later

		if(k.getKeyCode() == KeyEvent.VK_RIGHT){ // if the right arrow is pressed
			ship.dx = 10; // move the ship right continually
			if(ship.x > 624){ // if the ship reaches the right border
				ship.dx = -5; // move the ship left
			}
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){ // if the left arrow is pressed
			ship.dx = -10; // move the ship left continually
			if(ship.x < 1){ // if the ship reaches the left border
				ship.dx = 5; // move the ship right
			}
		}

		if(c == ' '){ // if the spacebar is pressed then it shoots a bullet
			bullet.x = ship.x; // brings the ship bullet to the ship
			bullet.y = ship.y - 60; // shoots the bullet forward
		}

		if(k.getKeyCode() == KeyEvent.VK_ESCAPE){ // quites the game if esc is pressed
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent k){ // used to "listen" for keys being released
		if(k.getKeyCode() == KeyEvent.VK_LEFT ||  k.getKeyCode() == KeyEvent.VK_RIGHT) // if these movement keys are released
			ship.dx = 0; // stop moving the ship
	}
	public void keyTyped(KeyEvent k) // listens for the keys being typed so they don't kill IntelliJ
	{
	}
}
