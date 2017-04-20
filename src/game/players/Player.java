package game.players;

import java.util.ArrayList;
import java.util.List;

import game.tiles.Planet;
import game.tiles.Space;

public class Player
{
	private Planet origin, destination;
	private List<Planet> planets = new ArrayList<Planet>();
	private int num;
	private String color;
	private boolean alive = true;

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
				if (p.getOwner() == this)
				{
					origin = p;
				}
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
				origin = null;
				destination = null;
			}
		}
		else
		{
			clearSelection();
		}
	}

	/**
	 * clear the planets selected by this player
	 */
	public void clearSelection()
	{
		origin = null;
		destination = null;
	}

	/**
	 * give a planet to this player's control
	 * 
	 * @param p
	 */
	public void addPlanet(Planet p)
	{
		p.setOwner(this);
		planets.add(p);
	}
	
	public List<Planet> getPlanets()
	{
		return planets;
	}

	/**
	 * removes a planet from this player's control
	 * 
	 * @param p
	 */
	public void removePlanet(Planet p)
	{
		planets.remove(p);
		if (planets.isEmpty()) alive = false;
	}

	/**
	 * get the player's player index
	 * 
	 * @return
	 */
	public int getNum()
	{
		return num;
	}

	/**
	 * return the css style associated with this player color
	 * 
	 * @return
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * set the name of the css style associated with this player color
	 * 
	 * @param color
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * return the name of this player
	 * 
	 * @return
	 */
	public String getName()
	{
		return "Player " + Integer.valueOf(num);
	}

	/**
	 * return whether the player is still in the game.
	 * 
	 * @return
	 */
	public boolean isAlive()
	{
		return alive;
	}
}
