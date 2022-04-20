
public class Predator extends Alien
{
	GameObject prey;
	
	//Predator, follows you
	Predator()
	{
		x = dice.nextInt(400);
		y = dice.nextInt(200);
		attribute = "Predator";
	}
	
	public void setPrey(GameObject o)
	{
		prey = o;
	}
	
	public void update()
	{
		if(prey.x < x)
		{
			x -= 5;
		}
		if(prey.x > x)
		{
			x += 5;
		}
		if(prey.y > y)
		{
			y += 3;
		}
		if(prey.y < y)
		{
			y -= 3;
		}
			
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
				bullet.y += 5;
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
