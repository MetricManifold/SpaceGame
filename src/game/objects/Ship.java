package game.objects;

import game.helpers.Displacement;

public class Ship
{
	final float speed = 1.0f;
	int power;
	int armor;
	
	private Planet destination;
	private Displacement pos;
	
	Ship(Displacement pos)
	{
		this.pos = pos;
		this.destination = null;
	}
	
	public void send(Planet destination)
	{
		this.destination = destination;
	}
	
	public void update()
	{
		if (destination != null)
		{
			if (pos.subtract(destination.pos).length() < speed)
			{
				destination = null;
				pos = destination.pos;
			}
			else
			{
				pos.move(speed);
			}
		}
	}
}
