public class Predator extends Alien{

	GameObject prey; // creates a variable to use in the rest of the code
	
	// predator, follows the player's movement to crash into them
	Predator(){
		x = dice.nextInt(400); // randomizes spawn location of the predator
		y = dice.nextInt(100); // ^^^
		attribute = "predator"; // for printing purposes
	}
	
	public void setPrey(GameObject o){ // imports values of the player so the preds can chase it
		prey = o; // creates a local variable to use for the rest of the class
	}
	
	public void update(){  // updates the location of the preds and bullets
			if (prey.x < x) { // if player is more left than pred : remove 5px right from pred x
				x -= 5;
			}
			if (prey.x > x) { // if player is more right than pred : add 5px right to pred x
				x += 5;
			}
			if (prey.y > y) { // if the prey is below the player, for some reason : add 3px to the pred y
				y += 3;
			}
			if (prey.y < y) { // if the prey is above the player : remove 3px from the pred y
				y -= 3;
			}

			if (alive) {  // repeats bullet and movement logic while its alive (not while its dead though)
				if (rand == 0 && !shot) { // repeats as long as a shot hasnt been taken
					bullet.x = x + width / 2; // brings the bullet to where the predator is
					bullet.y = y + 5; // sets bullet to just below it, as if it was shot
					shot = true;
				}

				if (shot) { // repeats until bullet.y is above or equal to 500, then the condition changes
					bullet.y += 5; // adds 5 until bullet.y is greater/equal to 500
					if (bullet.y >= 500) { // if bullet reaches 501px down the screen:
						bullet.y = -5; // brings the bullet off-screen to wait for next shot
						shot = false; // sets shot to false and repeats the above conditional
					}
				}
			} else { // if the pred is dead, then the bullet is away
				bullet.y = -15;
			}

	}
}
