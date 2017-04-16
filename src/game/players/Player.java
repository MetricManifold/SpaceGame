package game.players;

import java.util.ArrayList;
import java.util.List;

import game.objects.Planet;
import game.objects.Space;

public class Player
{
	private Planet origin, destination;
	private List<Planet> planets = new ArrayList<Planet>();
	private int num;
	private String color;
	
	public Player(int num, String color)
	{
		this.num = num;
		this.color = color;
		
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
	
	public void givePlanet(Planet p)
	{
		planets.add(p);
	}
	
	public void removePlanet(Planet p)
	{
		planets.remove(p);
	}
	
	public int getNum()	
	{
		return num;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getName()
	{
		return "Player " + Integer.valueOf(num);
	}
}
