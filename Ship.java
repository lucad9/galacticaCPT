public class Ship extends GameObject{

	int dx; //ship velocity
	
	Ship(){ // initializes the ship
		attribute = "ship";
	}

	public void update() { // collaborates with the GalagaPanel class for user input based on this
		x += dx; // when there's an update, dx is changed based on input (i.e. left arrow = -dx)
	}
}