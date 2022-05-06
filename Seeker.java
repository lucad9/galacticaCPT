
public class Seeker extends Alien
{
	double t;
	GameObject prey;
	
	
	// seeker alien, moves around in sine wave, shoots bullets that follow you
	
	Seeker(){
		x = dice.nextInt(400);
		y = dice.nextInt(200);
		attribute = "seeker";
		bullet.makeColorGreen();
	}
	
	public void setPrey(GameObject o){
		prey = o;
	}
	
	public void update(){
		if(cnt%2 != 1){
			x += dx;
			y = 120+ (int) (150 *Math.sin(t));
			t+= .2;
			if(x > 625)
				cnt++;
		}
		else{
			x -= dx;
			y = 120+ (int) (150 *Math.cos(t));
			t+= .2;
			if(x < 0)
				cnt++;
		}	
		
		rand = dice.nextInt(2);
		
		if(alive){
			if(rand == 0 && !shot){
				bullet.x = x + width/2;
				bullet.y = y + 5;
				shot = true;
			}
			
			if(shot){
				bullet.y += 5;
				if(prey.x < bullet.x){
					bullet.x -= 4;
				}
				if(prey.x > bullet.x){
					bullet.x += 4;
				}
				if(bullet.y >= 500){
					bullet.y = -5;
					shot = false;
				}
			}
		}

		else{
			bullet.y = -15;
		}
	}
}
