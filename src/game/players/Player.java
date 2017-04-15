package game.players;

import game.objects.Planet;
import game.objects.Space;

public class Player
{
	private Planet origin, destination;

	public Player()
	{
		origin = null;
		destination = null;
	}

	public void setDestination(Planet destination)
	{
		this.destination = destination;
	}

	public Planet getDestination()
	{
		return destination;
	}

	public void setOrigin(Planet origin)
	{
		this.origin = origin;
	}

	public Planet getOrigin()
	{
		return origin;
	}

	public void clickTile(Space s)
	{
		if (s instanceof Planet)
		{
			if (origin == null)
			{
				origin = (Planet) s;
			}
			else if (destination == null)
			{
				destination = (Planet) s;
			}
			else
			{
				origin = (Planet) s;
				destination = null;
			}
		}
		else
		{
			origin = null;
			destination = null;
		}
	}

	public void clearSelection()
	{
		origin = null;
		destination = null;
	}
}
