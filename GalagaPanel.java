	import javax.swing.*;
	import java.awt.*;
	import java.io.*;
	import java.util.*;
	import java.awt.event.*;

	// high score imports


	public class GalagaPanel extends JPanel implements KeyListener {
/* the "extends took a really long time to figure out, but from (extending) JPanel we are implements (using) KeyListener
   once we learned about this, we used extends a couple more times in this file
 */

		// creating variables images
		ImageIcon space, playership, purpleship, pred, bugship, important, splashScreen, MainMenu, HighScoreScreen, TutorialScreen; // ships and screens
		Image l2, l3, l4, lM; // level badges
		
		//Controld whether a new background music will play.
		boolean truth = false;
		
            //Creates an object for background music from the musicPlayer Class and an object for playing
            //sound clips from the Clip Player Class.
            musicPlayer vibePlayer = new musicPlayer();
            clipPlayer shortPlayer = new clipPlayer();

            //File paths for background music.
            String audioFilePath1 = "C:Music\\amogus.wav";
            String audioFilePath2 = "C:Music\\fleetingFrozenHeart.wav";
            String audioFilePath3 = "C:Music\\darudeSandstorm.wav";
            String audioFilePath4 = "C:Music\\hereToYou.wav";
            String audioFilePath5 = "C:Music\\epicFinalLevelMusic.wav";

            //File paths for clips
            String clipFilePath1 = "C:Clips\\starWarsBlasterSound.wav";
            String clipFilePath2 = "C:Clips\\deathSound.wav";


		LinkedList<Alien> masterList; // list of all aliens in game

		Ship ship; // imports ship class
		Bullet bullet; // imports bullet class

		int dead, listlength, levelcount; // creates variables to track actions
		boolean spawnedx = false; // level 1 logic
		boolean spawned = false; // level 2 logic
		boolean spawned2 = false; // level 3 logic
		boolean spawned3 = false; // level 4 logic

		//initializing menu states to switch between
		public static enum STATE {
			MENU, ENDGAME, TUTORIAL, LEVELONE, SCORES, SPLASHSCREEN, NEW
		}

		;
		//setting starting state to splashscreen
		public static STATE state = STATE.SPLASHSCREEN;
		private Menu menu; //importing menu code
		private String saveDataPath; // storage file for high score
		private String fileName = "SaveData"; // file name of highscore file
		private int Highscore = 0; // tracks highscore
		int score = 0; // might need to remove // tracks score for highscore purposes
		int AliveControler = 1;

		Toolkit t = Toolkit.getDefaultToolkit(); // imports the default graphics toolkit


		GalagaPanel() { // creates a class to be used in the main.java file
			//load images, data, whatever
			space = new ImageIcon("space.gif");
			MainMenu = new ImageIcon("MenuScreen.png");
			HighScoreScreen = new ImageIcon("ScoreScreen.png");
			splashScreen = new ImageIcon("splash.jpg");
			playership = new ImageIcon("player.png");
			purpleship = new ImageIcon("enemy_0.png");
			pred = new ImageIcon("enemy_1.png");
			bugship = new ImageIcon("enemy_2.png");
			//need to add valid tutorial
			TutorialScreen = new ImageIcon("tUTORITIAL (1).jpg");
			menu = new Menu();
			this.addMouseListener(new MouseInput());
			//load images for the level badges
			lM = t.getImage("badge_1.png");
			l2 = t.getImage("badge_2.png");
			l3 = t.getImage("badge_3.png");
			l4 = t.getImage("badge_4.png");
			important = new ImageIcon("important_file.png");


			ship = new Ship(); // new ship from the ship class
			ship.setPicture(playership); // uses a method within the ship class to set the image to the ship
			ship.x = 317; // sets ship pos to the middle of the screen
			ship.y = 480; // sets ship pos to the bottom of the screen

			masterList = new LinkedList<Alien>(); // makes a linkedlist that adds aliens, but also connects back to the masterlist

			bullet = new Bullet(); // imports bullets for the user

			UpdateThread ut = new UpdateThread(this); // imports the updatethread which makes the graphics update
			ut.start(); // uses the updatethread class to update the game's graphics

			addKeyListener(this); // imports the key listener
			setFocusable(true); // jpanel method that makes the game focusable
		}

		public void createSaveData() { //creating data file for highscore and writing method for highscore

			try{
				File file = new File(saveDataPath, fileName); //creating new file

				FileWriter output = new FileWriter(file); // assigning new file to output
				BufferedWriter writer = new BufferedWriter(output); // creating file write
				writer.write(""+0); // writing to file
				writer.close();
			}
			catch(Exception e){ //exception handeling
				e.printStackTrace();
			}

		}
		public void paintComponent(Graphics g) { // uses methods from graphics ; contains most of the code for the levels n stuff
			//clear screen
			if (state == STATE.SPLASHSCREEN) { //if the state is splashscreen renders and paints splashscreen
				g.drawImage(splashScreen.getImage(), 0, 0, getWidth(), getHeight(), this);

				Graphics2D g2d = (Graphics2D) g;//rendering graphics

				//font that says press q
				Font fnt0 = new Font("arial", Font.BOLD, 20);
				g.setFont(fnt0);
				g.setColor(Color.white);
				g.drawString("Press 'Q' ", 297, 350); // goes to menu if q is pressed


			} else if (state == STATE.MENU) { //if the state is menu paints menu screen
				g.drawImage(MainMenu.getImage(), 0, 0, getWidth(), getHeight(), this);

			} else if (state == STATE.SCORES) { // display scores if on score page

				try{
					File f = new File(saveDataPath, fileName); // assigns variable name f to new file
					if(!f.isFile()){ // if the file doesnt exist
						createSaveData(); // check for previous creation and run save data code
					}

					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f))); // create file reade
					Highscore =  Integer.parseInt(reader.readLine()); //reads current highscore

					reader.close();//close file reader
				}
				catch(Exception e){//eceptions statement
					e.printStackTrace();
				}


				//Draws and renders highscore onto page
				g.drawImage(HighScoreScreen.getImage(), 0, 0, getWidth(), getHeight(), this);
				Graphics2D g2d = (Graphics2D) g;

				//paints/ renders score fons
				Font fnt0 = new Font("arial", Font.BOLD, 50);
				g.setFont(fnt0);
				g.setColor(Color.white);
				g.drawString("High Score: " + Highscore, 175, 350);//prints the highscore


			} else if (state == STATE.TUTORIAL) { //put in Christians tutorial
				g.drawImage(TutorialScreen.getImage(), 0, 0, getWidth(), getHeight(), this);
				//needs valid tutorial jpg to work
			} else if (state == STATE.LEVELONE) {

				levelcount = 0;

				g.drawImage(space.getImage(), 0, 0, getWidth(), getHeight(), this); // draws the gif across the screen


				for (Alien go : masterList) { // for each alien in the masterlist
					go.draw(g, this); // draw the alien at its coordinates
					listlength++; // add value to listlength int
					go.bullet.draw(g, this); // draw bullets
					if (!go.alive) // if the alien dies
						dead++; // add value to the death count
				}

				if (AliveControler == 2){
					ship.draw(g, this); // draw the user ship
					bullet.draw(g, this); // draw the user bullet
				}
				ship.draw(g, this); // draw the user ship
				bullet.draw(g, this); // draw the user bullet


				// draw badge here
				g.drawImage(lM, 10, 10, this);


				// setting up the score
				g.setFont(new Font("sansseriff", Font.BOLD, 32)); // set font
				g.drawString("Score:", 535, 50); // draw score string
				int score = dead; // makes score = dead each time the game updates
				if (dead >= 25) { // if there are 25 dead aliens (basically if the game has been won)
					score = 25; // keeps score at 25 cuz if this isnt here then the score increases infinitely
				}
				String s = Integer.toString(score); // changes the score to a string so it can be printed
				g.drawString(s, 645, 50); // print the score next to the "score" string

				if (score > Highscore) { // if the user score is greater tjhan highscore
					FileWriter output  = null; // loads file writer
					try {
						File f = new File(saveDataPath, fileName); //creates new fie
						output = new FileWriter(f); //creates file
						BufferedWriter writer = new BufferedWriter(output); //loading writer filefor writing

							writer.write("" + score); //replaces old highscore with new highscore

						writer.close();//close wrtiter class

					} catch (Exception e) {//catching exception
						e.printStackTrace();
					}
				}
				// setting up high score
				if (score == 25) {
					g.setFont(new Font("sansseriff", Font.BOLD, 42)); // set font
					g.drawString("HIGHSCORE", 200, 100); // draw high score string
					g.drawString(s, 320, 150); // print the high score next to the "high score" string
				}

				if (ship.alive == false) { // when ship dies
					g.setFont(new Font("sansseriff", Font.BOLD, 32)); // set font
					g.drawString("You died! Press \"ESC\" and play again!", 50, 325); // print on screen that you died
				}

				levelcount = 1; // starts the level count value

				if (levelcount == 1) {
					
					if(truth == false){
					vibePlayer.play(audioFilePath2);
						truth = true;
					}
					
					if (!this.spawnedx) {
						for (int i = 0; i < 2; i++) { // adds two aliens once in levelone state
							Alien a = new Alien(); // alien class called
							a.setPicture(purpleship); // sets the picture to the main alien sprite
							masterList.add(a); // adds each alien to the masterclass
						}

						Predator p1 = new Predator(); // adds a predator
						p1.setPicture(pred); // sets the picture to the predator sprite
						p1.setPrey(ship); // makes the prey the ship, meaning its values will be equal to the player's
						masterList.add(p1); // adds to masterlist

						spawnedx = true;// makes sure aliens dont repeat
					}
				}

				if (score == 3) { // if 3 aliens are dead (level 1 beaten)
					levelcount += 1; // next level
					truth = false;
				}

				if (levelcount == 2) { // if user beat 3 aliens / cleared level 1
					
					if(truth == false){
					vibePlayer.play(audioFilePath3);
						truth = true;
					}
					
					if (!this.spawned) { // the code would spawn an infinite amount of aliens if this failsafe wasn't here
						for (int i = 0; i < 3; i++) { // spawns 3 aliens
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

				if (score == 7) { // if the player beat level 2
					levelcount = 3; // advance to level 3
					truth = false;
				}

				if (levelcount == 3) { // if its level 3
					
					if(truth == false){
					vibePlayer.play(audioFilePath4);
					truth = true;
					}
					
					if (!this.spawned2) { // new failsafe instead of overwriting the old one
						for (int i = 0; i < 4; i++) { // spawns 4 purple ones
							Alien a = new Alien();
							a.setPicture(purpleship);
							masterList.add(a);
						}

						Seeker seek = new Seeker(); // spawns a seeker
						seek.setPicture(bugship);
						seek.setPrey(ship);
						masterList.add(seek);

						for (int i = 0; i < 2; i++) { // spawns 2 predators
							Predator p1 = new Predator();
							p1.setPicture(pred);
							p1.setPrey(ship);
							masterList.add(p1);
						}

						spawned2 = true; // new failsafe is stopped
					}
					lM = l3; // level badge = level 3
				}

				if (score == 14) { // if the player beats level 3
					levelcount = 4; // start level 4
					truth = false;
				}

				if (levelcount == 4) { // if its level 4
					
					if(truth == false){
					vibePlayer.play(audioFilePath5);
						truth = true;
					}
					
					if (!this.spawned3) { // the next and final failsafe so a bunch of aliens don't spawn infinitely
						for (int i = 0; i < 6; i++) { // spawns 6 (SIX!!!) of the purple aliens
							Alien a = new Alien();
							a.setPicture(purpleship);
							masterList.add(a);
						}

						for (int i = 0; i < 2; i++) { // spawns 2 seekers
							Seeker seek = new Seeker();
							seek.setPicture(bugship);
							seek.setPrey(ship);
							masterList.add(seek);
						}

						for (int i = 0; i < 3; i++) { // spawns 3 predators
							Predator p1 = new Predator();
							p1.setPicture(pred);
							p1.setPrey(ship);
							masterList.add(p1);
						}

						spawned3 = true; // switches on the failsafe so it doesnt spawn infinitely again
					}
					lM = l4; // level badge = level 4
				}


				if (score >= 25) { // if all the aliens are killed (25 aliens in the game)
					g.setFont(new Font("sansseriff", Font.BOLD, 32));
					g.drawString("You won!", 260, 325); // prints "you won!" in the middle of your screen
				} else { // prevents the game from literally going haywire
					listlength = 0; // keeps listlength at zero if an alien is killed (it'll increase infinitely otherwise)
					dead = 0; // same as above, different variable
				}


			}

		}




		public void update () {
			//update all objects in game
			for (Alien go : masterList) {
				go.update();
				if (bullet.intersects(go)) {
					go.kill();
				}

				if (go.intersects(ship) && !go.attribute.equalsIgnoreCase("ship")) {
					ship.kill();
				}

				if (go.bullet.intersects(ship)) {
					ship.kill();
				}

			}

			//check for bullet shot aliens

			bullet.update();
			ship.update();

			repaint();
		}




	// checks the keyboard for input to move
	public void keyPressed(KeyEvent k) { // more keylistener things used for user input
		char c = k.getKeyChar(); // used to check if the spacebar is pressed later
		char q = k.getKeyChar(); // usde to bring user to main menu / escape
		char p = k.getKeyChar(); // used to play new round

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

		if (k.getKeyCode() == KeyEvent.VK_Q) { // if 'q' is pressed brings user to main menu nomatter what
			GalagaPanel.state = GalagaPanel.STATE.MENU;

		}
		if (k.getKeyCode() == KeyEvent.VK_P){
			ship.alive = true;
			levelcount = 1;
			AliveControler = 2;
			state = STATE.NEW;
			ship.setPicture(important);
			ship.x = 317; // sets ship pos to the middle of the screen
			ship.y = 480; // sets ship pos to the bottom of the screen
			score = 0;
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
