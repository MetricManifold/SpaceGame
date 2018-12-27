package game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import game.managers.PlayerManager.Controller;
import game.players.Player;
import game.tiles.Planet;
import game.tiles.Space;

public class PlanetManager
{
	// planets and corresponding buttons
	protected Map<Integer, Planet> planets;
	protected Map<Integer, Space> spaces;

	protected int x, y, len;
	protected double density;

	protected PlayerManager PM;
	protected TurnManager TM;
	protected ConfigManager CM;

	public PlanetManager(ConfigManager CM)
	{
		this.CM = CM;
		loadConfiguration();
	}

	/**
	 * Sets the mouse event associated with the tilepane to find all the grid locations
	 */
	public void setup(PlayerManager pm, TurnManager tm)
	{
		TM = tm;
		PM = pm;

		spawnPlanets();
		setStartPlanets();
	}

	/**
	 * resets the player manager, initializes then resets the turn manager
	 */
	public void reset()
	{
		loadConfiguration();
		CM.clearPlanetNames();
		spawnPlanets();
		setStartPlanets();
	}

	/**
	 * initialization activity for this manager to set variables and create planets
	 */
	protected void loadConfiguration()
	{
		this.x = CM.gridX;
		this.y = CM.gridY;
		this.density = CM.planetDensity;
		this.len = x * y;
	
	}
	

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	public void spawnPlanets()
	{
		planets = new HashMap<Integer, Planet>();
		spaces = new HashMap<Integer, Space>();

		int startCountSum = 0;
		for (Entry<Player, Integer> n : CM.planetStartCountMap.entrySet())
		{
			startCountSum += n.getValue();
		}
		
		while (planets.size() < startCountSum)
		{
			for (int i = 0; i < len; i++)
			{
				Space s = new Space(i % x, i / x);
				spaces.put(hashLocation(i), s);

				if (ThreadLocalRandom.current().nextDouble() < density)
				{
					Planet p = new Planet(s, CM);
					planets.put(hashLocation(i), p);
				}
				
			}
		}
	}

	/**
	 * give players one starting planet
	 */
	public void setStartPlanets()
	{
		// make a list of planets to choose from
		ArrayList<Planet> planetArray = getPlanetArray();

		for (int i = 0; i < PM.getNumPlayersOfType(Controller.HUMAN); i++)
		{
			Player v = PM.getPlayersOfType(Controller.HUMAN)[i];
			for (int j = 0; j < CM.planetStartCountMap.get(v); j++)
			{
				int r = ThreadLocalRandom.current().nextInt(planetArray.size());
				Planet p = planetArray.remove(r);

				setPlanetOwner(v, p);
				p.setProduction(CM.defaultShip, CM.initialProduction);
				p.addShips(CM.defaultShip, CM.shipStartCount);
			}
		}

		planetArray.forEach(p -> setPlanetOwner(PM.neutral, p));
		if (!CM.neutralShipsVisible) planetArray.forEach(p -> p.setDisplayShips(false));
	}

	/**
	 * transfer ownership of a planet to a given player
	 * 
	 * @param player
	 * @param p
	 */
	public void setPlanetOwner(Player player, Planet p)
	{
		p.setOwner(player);
	}

	/**
	 * creates a unique integer associated with the provided 2d location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected int hashLocation(int index)
	{
		int a = index % x;
		int b = index / x;
		return a + x * b;
	}

	/**
	 * get the array of planets
	 * 
	 * @return
	 */
	public ArrayList<Planet> getPlanetArray()
	{
		return new ArrayList<Planet>(planets.values());
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
