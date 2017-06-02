package game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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
		reloadConfiguration();

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

	public void reloadConfiguration()
	{
		loadConfiguration();
		for (Planet p : planets.values())
		{
			if (p.getOwner().getController() == Controller.NEUTRAL)
			{
				p.setDisplayShips(CM.neutralShipsVisible);
				p.setDefenderBonus(CM.planetDefenderBonus);
			}
			else
			{
				p.setProduction(CM.defaultShip, CM.initialProduction);
				p.getShipInventory().removeAll();
				p.addShips(CM.defaultShip, CM.shipStartCount);
				p.setDefaultShip(CM.defaultShip);
			}
		}
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	public void spawnPlanets()
	{
		planets = new HashMap<Integer, Planet>();
		spaces = new HashMap<Integer, Space>();

		do
		{
			for (int i = 0; i < len; i++)
			{
				// create the necessary elements
				Space s = new Space(i % x, i / x);

				// choose whether to create a planet or empty space
				double prob = ThreadLocalRandom.current().nextDouble();
				if (prob < density)
				{
					Planet p = new Planet(s, CM);
					planets.put(hashLocation(i), p);
				}
				else
				{
					spaces.put(hashLocation(i), s);
				}
			}
		} while (planets.size() < PM.numPlayers);
	}

	/**
	 * give players one starting planet
	 */
	public void setStartPlanets()
	{
		// make a list of planets to choose from
		Planet[] planetArray = getPlanetArray();

		// make a list denoting each of the planets
		List<Integer> nums = new ArrayList<>();
		IntStream.range(0, planets.size()).forEach(i -> nums.add(i));

		for (int i = 0; i < PM.getNumPlayersOfType(Controller.HUMAN); i++)
		{
			Player v = PM.getPlayersOfType(Controller.HUMAN)[i];
			for (int j = 0; j < CM.planetStartCountMap.get(v); j++)
			{
				int r = ThreadLocalRandom.current().nextInt(nums.size());
				int pick = nums.remove(r);
				Planet p = planetArray[pick];

				setPlanetOwner(v, p);
				p.setProduction(CM.defaultShip, CM.initialProduction);
				p.addShips(CM.defaultShip, CM.shipStartCount);
			}
		}

		nums.forEach(n -> setPlanetOwner(PM.neutral, planetArray[n]));
		if (!CM.neutralShipsVisible) nums.forEach(n -> planetArray[n].setDisplayShips(false));
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
	public Planet[] getPlanetArray()
	{
		return planets.values().toArray(new Planet[planets.size()]);
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
