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
		if (s != null && s instanceof Planet)
		{
			Planet p = (Planet) s;

			if (origin == null)
			{
				origin = p;
			}
			else if (destination == null)
			{
				if (p != origin)
				{
					destination = p;
				}
			}
			else
			{
				origin = p;
				destination = null;
			}
		}
		else
		{
			clearSelection();
		}
	}

	public void clearSelection()
	{
		origin = null;
		destination = null;
	}
}
