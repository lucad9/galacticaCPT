
public class CircleAlien extends Alien
{

	double t;

	public void update()
	{
		x = 100+ (int) (100 * Math.sin(t));
		y = 100+ (int) (100 *Math.cos(t));
		t += .2;
		
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