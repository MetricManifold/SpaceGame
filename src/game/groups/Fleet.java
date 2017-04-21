package game.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.entities.Ship;
import game.helpers.AccumulateInteger;
import game.helpers.AccumulateValue;
import game.helpers.Displacement;
import game.helpers.Tuple;
import game.managers.ConfigurationManager;
import game.managers.PlanetManager;
import game.players.Player;
import game.tiles.Planet;

public class Fleet extends ShipGroup
{
	private Displacement path = null;
	private Planet destination = null;
	private float speed = Integer.MAX_VALUE;
	private Player owner;

	public Fleet(Player owner)
	{
		this.owner = owner;
	}

	public Fleet(ShipGroup group, Player owner)
	{
		super(group);
		this.owner = owner;
	}

	public Fleet(Class<? extends Ship> type, int num, Player owner)
	{
		super(type, num);
		this.owner = owner;
	}

	public Fleet(Ship ship, Player owner)
	{
		super(ship);
		this.owner = owner;
	}

	public Fleet(Collection<Ship> ships, Player owner)
	{
		super(ships);
		this.owner = owner;
	}

	/**
	 * update the status of this fleet, including position if moving
	 * 
	 * @param pm
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void update(PlanetManager pm)
	{
		if (path != null)
		{
			if (path.subtract(speed).length() < speed)
			{
				if (owner != destination.getOwner())
				{
					attack(destination);

					if (getCount() > 0)
					{
						for (Ship f : getAll())
						{
							f.heal();
						}

						pm.setPlanetOwner(owner, destination);
						destination.addShips(this);
						removeAll();
					}
				}
				else
				{
					destination.addShips(this);
				}

				path = null;
				destination = null;
			}
			else
			{
				path = path.subtract(speed);
			}
		}
	}

	/**
	 * direct this fleet to move to a destination from the origin
	 * 
	 * @param origin
	 * @param dest
	 */
	public void send(Planet origin, Planet dest)
	{
		// set the path and destination
		this.path = dest.getPosition().subtract(origin.getPosition());
		this.destination = dest;

		// log message
		System.out.format("send ship on path x%.1f,y%.1f\n", path.getX(), path.getY());

		for (List<Ship> l : ships.values())
		{
			for (Ship s : l)
			{
				if (s.speed < speed) speed = s.speed;
			}
		}
	}

	/**
	 * makes this fleet attack another, modifying both of them. Loop iterates for each type of ship
	 * 
	 * @param defender
	 * @param defenderBonus
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void attack(ShipGroup defender, double defenderBonus)
	{
		// get the set of all types in this conflict
		Set<Class<? extends Ship>> types = new HashSet<Class<? extends Ship>>(ships.keySet());
		types.addAll(defender.ships.keySet());

		// make variables for the damage accumulation
		Map<Class<? extends Ship>, List<Ship>> attackers = ships;
		Map<Class<? extends Ship>, List<Ship>> defenders = defender.ships;
		Map<Class<? extends Ship>, AccumulateInteger> atk = new HashMap<>();
		Map<Class<? extends Ship>, AccumulateInteger> dfn = new HashMap<>();
		attackers.keySet().forEach(t -> dfn.put(t, new AccumulateInteger()));
		defenders.keySet().forEach(t -> atk.put(t, new AccumulateInteger()));

		dfn.put(Ship.class, new AccumulateInteger());
		atk.put(Ship.class, new AccumulateInteger());

		// create a mapping of types to total damage against that type

		for (Ship s : getAll())
		{
			Class<? extends Ship> str = s.getFirstStrengthFrom(defenders.keySet());
			if (str != null)
			{
				atk.get(str).add(s.attack + s.getBonus(str));
			}
			else
			{
				atk.get(Ship.class).add(s.attack);
			}
		}

		for (Ship s : defender.getAll())
		{
			Class<? extends Ship> str = s.getFirstStrengthFrom(attackers.keySet());
			if (str != null)
			{
				dfn.get(str).add((int) ((s.attack + s.getBonus(str)) * defenderBonus));
			}
			else
			{
				dfn.get(Ship.class).add((int) (s.attack * defenderBonus));
			}
		}

		while (defender.getCount() > 0 && getCount() > 0)
		{

			// remove all the keys that do no damage

			for (Class<? extends Ship> t : dfn.keySet())
			{
				if (dfn.get(t).get() == 0)
				{
					dfn.remove(t);
				}
			}

			for (Class<? extends Ship> t : atk.keySet())
			{
				if (atk.get(t).get() == 0)
				{
					atk.remove(t);
				}
			}

			// iterate over every type of ship
			for (Class<? extends Ship> t : types)
			{
				System.out.format("attack power at %d, defense power at %d", atk.get(t).get(), dfn.get(t).get());

				/*
				 * defenders
				 */

				// get the damage against this type and all the attacking ships
				int dmg = (dfn.containsKey(t)) ? dfn.get(t).get() : dfn.get(Ship.class).get();

				Iterator<Ship> it = attackers.get(t).iterator();
				while (it.hasNext() && dmg >= 0)
				{
					Ship s = it.next();
					dmg -= s.health;

					if (dmg >= 0)
					{
						// remove the ship from the list and subtract its damage
						it.remove();
						Class<? extends Ship> str = s.getFirstStrengthFrom(defenders.keySet());
						atk.get(str).sub(s.attack + s.getBonus(str));
					}
					else
					{
						// leave remaining health and exit
						s.health = -dmg;
					}
				}
				
				/*
				 * attackers
				 */

				t = (atk.containsKey(t)) ? t : Ship.class;

				// get the damage against this type and all the defending ships
				int dmg = (atk.containsKey(t)) ? atk.get(t).get() : atk.get(Ship.class).get();

				Iterator<Ship> it = defenders.get(t).iterator();
				while (it.hasNext() && dmg >= 0)
				{
					Ship s = it.next();
					dmg -= s.health;

					if (dmg >= 0)
					{
						// remove the ship from the list and subtract its damage
						it.remove();
						Class<? extends Ship> str = s.getFirstStrengthFrom(attackers.keySet());
						dfn.get(str).sub(s.attack + s.getBonus(str));
					}
					else
					{
						// leave remaining health and exit
						s.health = -dmg;
					}
				}
			}

		}
	}

	public void attack(Planet p)
	{
		attack(p.getShipInventory(), ConfigurationManager.planetDefenderBonus);
	}

}
