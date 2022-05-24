
public class Seeker extends Alien{
	double t;
	GameObject prey; // imports the ship

	Seeker(){
		x = dice.nextInt(400); // randomizes spawn
		y = dice.nextInt(200);
		attribute = "seeker"; // for printing
		bullet.makeColorGreen(); // makes the bullets green so you can spot them
	}
	
	public void setPrey(GameObject ship){
		prey = ship; // makes the bullets target the ship
	}
	
	public void update(){ // alien logic
		if(cnt%2 != 1){ // while it hasnt reached the right side
			x += dx; // move right
			y = 120+(int)(80*Math.sin(t)); // move along a sine wave
			t += .2; // increases the sine
			if(x > 625) // when it reaches the right side
				cnt++; // move left
		}

		else{
			x -= dx; // move left
			y = 120+(int)(80*Math.cos(t)); // move along a sine wave still
			t += .2; // increases the sine
			if(x < 0) // when it reaches the left side
				cnt++; // move right
		}	
		
		rand = dice.nextInt(2); // does it shoot?
		
		if(alive){ // if its alive
			if(rand == 0 && !shot){ // figure out of the dice rolled a zero and if the bullet hasn't recently been shot
				bullet.x = x + width/2; // set bullet to the bug pos
				bullet.y = y + 5; // set bullet to the bug pos
				shot = true; // sets bullet to true
			}
			
			if(shot){ // if it rolls a zero
				bullet.y += 5; // move the bullet downwards
				if(prey.x < bullet.x){ // if the ship x is less than the bullet x (further left)
					bullet.x -= 4; // move the bullet left
				}
				if(prey.x > bullet.x){ // if the ship x is more than the bullet x (further right)
					bullet.x += 4; // move the bullet right
				}
				if(bullet.y >= 500){ // when the bullet reaches the bottom of the screen
					bullet.y = -5; // moves the bullet back offscreen
					shot = false; // sets shot to false so it can shoot again
				}
			}
		}

		else{ // if the alien dies
			bullet.y = -15; // just move the bullet off-screen even if it's shot
		}
	}
}
