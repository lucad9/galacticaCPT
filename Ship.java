
public class Ship extends GameObject
{

	//ship velocity
	int dx;
	
	Ship()
	{
		attribute = "ship";
	}
	public void update()
	{
		x+=dx;
	}

}