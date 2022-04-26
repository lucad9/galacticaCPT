import java.util.*;

public class Alien extends GameObject
{
	Random dice;
	int cnt = 1;
	int dx;
	int rand = 0;
	boolean shot;
	
	Bullet bullet;
	
	Alien(){
		dice = new Random();
		x = dice.nextInt(400);
		y = dice.nextInt(250);
		dx = dice.nextInt(20) + 10;
		attribute = "alien";
		bullet = new Bullet();
		bullet.makeColorBlue();
		bullet.y = -10;
		shot = false;
	}

	public void update(){		
		if(cnt%2 != 1)
		{
			x += dx;
			if(x > 500)
				cnt++;
		}
		else
		{
			x -= dx;
			if(x < 0)
				cnt++;
		}	
		
		rand = dice.nextInt(2);
		
		if(alive)
		{
			if(rand == 0 && !shot)
			{
				bullet.x = x + width/2;
				bullet.y = y + 5;
				shot = true;
			}
			
			if(shot)
			{
				bullet.y += 10;
				if(bullet.y >= 500)
				{
					bullet.y = -5;
					shot = false;
				}
			}
		}
		else
		{
			bullet.y = -15;
		}
		
	}
}