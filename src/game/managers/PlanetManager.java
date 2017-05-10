package game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import game.players.Player;
import game.tiles.Planet;
import game.tiles.Space;

public class PlanetManager
{
	// planets and corresponding buttons
	protected Map<Integer, Planet> planets = new HashMap<Integer, Planet>();
	protected Map<Integer, Space> spaces = new HashMap<Integer, Space>();

	protected int sizeh, sizev, x, y, len;
	protected double density;

	protected PlayerManager PM;
	protected TurnManager TM;

	public PlanetManager()
	{
		this.x = ConfigurationManager.gridX;
		this.y = ConfigurationManager.gridY;
		this.density = ConfigurationManager.planetDensity;
		this.len = x * y;
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
	 * puts planets in the grid and associates them with buttons
	 */
	public void spawnPlanets()
	{
		for (int i = 0; i < len; i++)
		{
			// create the necessary elements
			Space s = new Space(i % x, i / x);

			// choose whether to create a planet or empty space
			double prob = ThreadLocalRandom.current().nextDouble();
			if (prob < density)
			{
				Planet p = new Planet(s);
				planets.put(hashLocation(i % x, i / x), p);
			}
			else
			{
				spaces.put(hashLocation(i % x, i / x), s);
			}
		}
	}

	/**
	 * give players one starting planet
	 */
	public void setStartPlanets()
	{
		// make a list of planets to choose from
		int numPlayers = ConfigurationManager.numPlayers;
		Planet[] planetArray = getPlanetArray();

		// make a list denoting each of the planets
		List<Integer> nums = new ArrayList<>();
		IntStream.range(0, planets.size()).forEach(i -> nums.add(i));

		while (numPlayers-- > 0)
		{
			// pick a random value from the planet array
			int r = ThreadLocalRandom.current().nextInt(nums.size());
			int pick = nums.remove(r);
			Planet p = planetArray[pick];

			setPlanetOwner(PM.getPlayer(numPlayers), p);
			p.setProduction(ConfigurationManager.initialProduction);
			p.addShips(ConfigurationManager.defaultShip, ConfigurationManager.shipStartCount);
		}

		nums.forEach(n -> setPlanetOwner(PM.neutral, planetArray[n]));
	}

	/**
	 * transfer ownership of a planet to a given player
	 * 
	 * @param player
	 * @param p
	 */
	public void setPlanetOwner(Player player, Planet p)
	{
		player.addPlanet(p);
	}

	/**
	 * creates a unique integer associated with the provided 2d location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected int hashLocation(int x, int y)
	{
		return x + this.x * y;
	}

	/**
	 * get the array of planets
	 * 
	 * @return
	 */
	public Planet[] getPlanetArray()
	{
		return planets.values().toArray(new Planet[planets.size()]);
	}

	/**
	 * return the width in tiles of the grid
	 * 
	 * @return
	 */
	public int getSizeX()
	{
		return sizeh;
	}

	/**
	 * return the height in tiles of the grid.
	 * 
	 * @return
	 */
	public int getSizeY()
	{
		return sizev;
	}

}
