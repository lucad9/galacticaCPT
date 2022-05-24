import java.util.*;

public class Alien extends GameObject{
	Random dice;
	int cnt = 1; // move left-right
	int dx; // velocity
	int rand = 0;
	boolean shot;
	
	Bullet bullet;
	
	Alien(){
		dice = new Random();
		x = dice.nextInt(400); // randomizes spawn across the screen
		y = dice.nextInt(250); // randomizes spawn at the top of the screen
		dx = dice.nextInt(20) + 10; // randomizes velocity
		attribute = "alien";
		bullet = new Bullet(); // uses the bullet class to add a bullet to each alien
		bullet.makeColorBlue();
		bullet.y = -10; // places it offscreen so its not seen
		shot = false;
	}

	public void update(){		
		if(cnt%2 != 1){ // while the alien has not hit the right side
			x += dx; // move right
			if(x > 625) // if alien hits the right side
				cnt++; // make alien move left
		}

		else{ // while the alien has not hit the left side
			x -= dx; // move left
			if(x < 0) // if alien hits the left side
				cnt++; // make alien move right
		}	
		
		rand = dice.nextInt(2); // picks logic to see if it shoots or not
		
		if(alive){ // repeats while alive
			if(rand == 0 && !shot){
				bullet.x = x + width/2; // moves bullet to the alien's location
				bullet.y = y + 5; // bullet is put in front of the alien
				shot = true; // allows bullet logic
			}
			
			if(shot){
				bullet.y += 10; // moves bullet downwards
				if(bullet.y >= 500){ // when the bullet reaches 500px down
					bullet.y = -5; // make it invisible
					shot = false; // allows the loop to repeat
				}
			}
		}

		else{
			bullet.y = -15; // moves the bullet off screen while the enemy is dead
		}
		
	}
}