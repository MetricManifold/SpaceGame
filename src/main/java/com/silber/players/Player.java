package com.silber.players;

import java.util.HashSet;
import java.util.Set;

import com.silber.managers.PlanetManager;
import com.silber.managers.PlayerManager;
import com.silber.managers.TurnManager;
import com.silber.managers.PlayerManager.Controller;
import com.silber.tiles.Planet;
import com.silber.tiles.Space;

public class Player
{
	private Planet origin, destination;
	private Set<Planet> planets = new HashSet<Planet>();
	private int num;
	private String color, name;
	private boolean alive = true;
	private Controller controller;

	protected Player(int num, String color, Controller controller)
	{
		this.num = num;
		this.color = color;
		this.controller = controller;
		this.name = "Player " + Integer.valueOf(num);

		origin = null;
		destination = null;
	}

	public Player(int num, String color)
	{
		this(num, color, Controller.HUMAN);
	}

	public Controller getController()
	{
		return controller;
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
	 * return the list of owned planets
	 * 
	 * @return
	 */
	public Set<Planet> getPlanetSet()
	{
		return new HashSet<>(planets);
	}

	/**
	 * add a planet to this player's planet set
	 * 
	 * @param p
	 */
	public void addPlanet(Planet p)
	{
		planets.add(p);
		alive = true;
	}

	/**
	 * removes a planet from this player's planet set
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
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
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

	/**
	 * updates the state of this player
	 * 
	 * @param pg
	 * @param tm
	 * @param pm
	 */
	public void update(PlanetManager pg, TurnManager tm, PlayerManager pm)
	{}
}
