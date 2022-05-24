package Galaga;

import javax.swing.*;
	import java.awt.*;
import java.io.*;
import java.util.*;
	import java.awt.event.*;
import java.io.File;

import java.io.PrintWriter;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.FileNotFoundException;


public class GalagaPanel extends JPanel implements KeyListener {

	//public static Object State;
	//images
	ImageIcon space, playership, purpleship, pred, bugship, splashScreen, MainMenu, HighScoreScreen;
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

	private int Highscore = 0;
	private String saveDataPath;

	private String fileName = "SaveData";

	//Main Menu controler
	int control = 0;
	int score = 0;

	public static enum STATE{
		MENU, ENDGAME, TUTORIAL, LEVELONE, SCORES
	};
	public static STATE state = STATE.MENU;
	private Menu menu;
	Toolkit t = Toolkit.getDefaultToolkit();

	GalagaPanel() {
		//load images, data, whatever
		space = new ImageIcon("space.gif");
		MainMenu = new ImageIcon("MenuScreen.png");
		HighScoreScreen = new ImageIcon("ScoreScreen.png");

		playership = new ImageIcon("player.png");
		purpleship = new ImageIcon("enemy_0.png");
		pred = new ImageIcon("enemy_1.png");
		bugship = new ImageIcon("enemy_2.png");

		menu = new Menu();
		this.addMouseListener(new MouseInput());
		//load images for the level badges
		lM = t.getImage("badge_1.png");
		l2 = t.getImage("badge_2.png");
		l3 = t.getImage("badge_3.png");
		l4 = t.getImage("badge_4.png");


		//add a ship to the game
		ship = new Ship();
		//int con = Numbers.Numbers(1);
		//System.out.print(con);
		ship.setPicture(playership);
		ship.x = 317;
		ship.y = 480;
		loadHighScore();

		// this one adds the purple aliens at the top
		masterList = new LinkedList<Alien>();
		for (int i = 0; i < 2; i++) {
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
		if (state == STATE.MENU) {
			g.drawImage(MainMenu.getImage(), 0, 0, getWidth(), getHeight(), this);
			menu.render(g);

		}
		else if (state == STATE.TUTORIAL){

		}
		else if (state == STATE.SCORES){
				g.drawImage(HighScoreScreen.getImage(), 0, 0, getWidth(), getHeight(), this);
				Graphics2D g2d = (Graphics2D) g;

				Font fnt0 = new Font("arial", Font.BOLD, 50);
				g.setFont(fnt0);
				g.setColor(Color.white);
				g.drawString("High Score: " + Highscore, 175, 350);


		}
			else if (state == STATE.LEVELONE) {
				g.drawImage(space.getImage(), 0, 0, getWidth(), getHeight(), this);

				//draw all objects in game
				for (Alien go : masterList) {
					go.draw(g, this);
					listlength++;
					go.bullet.draw(g, this);
					if (!go.alive)
						dead++;
				}

				ship.draw(g, this);
				bullet.draw(g, this);


				// draw badge here
				g.drawImage(lM, 10, 10, this);

				g.setFont(new Font("sansseriff", Font.BOLD, 32));
				g.drawString("Score:", 535, 50);

				//setting up the score
				score = dead;
				if (dead >= 25) {
					score = 25;
				}
				String s = Integer.toString(score);
				g.drawString(s, 645, 50);


				if (ship.alive == false) {
					g.setFont(new Font("sansseriff", Font.BOLD, 32));
					g.drawString("You died!", 260, 325);
				}

				levelcount = 1;
				if (score == 3) {
					levelcount += 1;
				}

				if (levelcount == 2) {
					if (!this.spawned) {
						for (int i = 0; i < 3; i++) {
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

				if (score == 7) {
					levelcount = 3;
				}


				if (levelcount == 3) {
					if (!this.spawned2) {
						for (int i = 0; i < 4; i++) {
							Alien a = new Alien();
							a.setPicture(purpleship);
							masterList.add(a);
						}

						Seeker seek = new Seeker();
						seek.setPicture(bugship);
						seek.setPrey(ship);
						masterList.add(seek);

						for (int i = 0; i < 2; i++) {
							Predator p1 = new Predator();
							p1.setPicture(pred);
							p1.setPrey(ship);
							masterList.add(p1);
						}

						spawned2 = true;
					}
					lM = l3;
				}

				if (score == 14) {
					levelcount = 4;
				}

				if (levelcount == 4) {
					if (!this.spawned3) {
						for (int i = 0; i < 6; i++) {
							Alien a = new Alien();
							a.setPicture(purpleship);
							masterList.add(a);
						}

						for (int i = 0; i < 2; i++) {
							Seeker seek = new Seeker();
							seek.setPicture(bugship);
							seek.setPrey(ship);
							masterList.add(seek);
						}

						for (int i = 0; i < 3; i++) {
							Predator p1 = new Predator();
							p1.setPicture(pred);
							p1.setPrey(ship);
							masterList.add(p1);
						}

						spawned3 = true;
					}
					lM = l4;
				}


				// High Score Making
				try{
				saveDataPath = System.getProperty("user.home") + "Galaga Score";
			}
				catch(Exception e){
					e.printStackTrace();
				}


				if (score >= 25) {
					g.setFont(new Font("sansseriff", Font.BOLD, 32));
					g.drawString("You won!", 260, 325);
				} else {
					listlength = 0;
					dead = 0;
				}
			}

		}

	private void createSaveData() {

		try{
			File file = new File(saveDataPath, fileName);

			FileWriter output = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(""+0);
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	private void loadHighScore(){

		try{
			File f = new File(saveDataPath, fileName);
			if(!f.isFile()){
				createSaveData(); // check for previous creation
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			Highscore =  Integer.parseInt(reader.readLine());

			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}



	private void setHighScore(){
		FileWriter output = null;
		try{
			File f = new File(saveDataPath, fileName);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);

			if (score> Highscore){
				writer.write(""+score);
			}
			writer.close();

		}
		catch(Exception e){
			e.printStackTrace();
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// checks the keyboard for input to move
		public void keyPressed (KeyEvent k){
			char c = k.getKeyChar();
			char q = k.getKeyChar();



			if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
				ship.dx = 10;
				if (ship.x > 624) {
					ship.dx = -5;
				}
			}
			if (k.getKeyCode() == KeyEvent.VK_LEFT) {
				ship.dx = -10;
				if (ship.x < 1) {
					ship.dx = 5;
				}
			}

			if (c == ' ') {
				bullet.x = ship.x;
				bullet.y = ship.y - 60;
			}

			if (k.getKeyCode() == KeyEvent.VK_Q){
				GalagaPanel.state = GalagaPanel.STATE.MENU;
			}


		}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
