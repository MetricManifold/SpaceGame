package game.groups;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.entities.Ship;
import game.helpers.Displacement;
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

	public Fleet(Class<?> type, int num, Player owner)
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
	public void update(PlanetManager pm) throws InstantiationException, IllegalAccessException
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
							f.maxHealth();
						}
						pm.givePlanet(owner, destination);
						destination.addShips(this);
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
		System.out.format("x%.1f, y%.1f\n", path.getX(), path.getY());

		for (List<Ship> l : ships.values())
		{
			for (Ship s : l)
			{
				if (s.getSpeed() < speed) speed = s.getSpeed();
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
	public void attack(ShipGroup defender, double defenderBonus) throws InstantiationException, IllegalAccessException
	{
		Set<Class<?>> types = new HashSet<Class<?>>(ships.keySet());
		types.addAll(defender.ships.keySet());

		while (defender.getCount() > 0 && getCount() > 0)
		{
			for (Class<?> type : types)
			{
				// get the map of strengths for the current class of ship, 
				Map<Class<?>, Integer> strengths = ((Ship) type.newInstance()).getStrengths();

				// evaluate defenders
				if (defender.ships.containsKey(type))
				{
					// filter strengths based on attacker makeup
					Set<Class<?>> strSet = new HashSet<Class<?>>(strengths.keySet());
					strSet.retainAll(ships.keySet());
					Class<?>[] strAll = strSet.toArray(new Class<?>[] {});

					// get the list of attackers
					List<Ship> attackers = getAll();

					// for each of the defenders ships of the current type, damage an attacker
					for (Ship s : defender.ships.get(type))
					{
						// check whether to apply strength
						if (strSet.isEmpty() || !ships.containsKey(strAll[0]))
						{
							Ship atk = attackers.get(0);
							atk.subtractHealth((int) (s.getAttack() * defenderBonus));

							// if the attacker is dead, remove it from the ships
							if (atk.isDead())
							{
								ships.get(atk.getClass()).remove(atk);
							}
						}
						else
						{
							// get list of weak ships and last one in that list.
							List<Ship> atl = ships.get(strAll[0]);
							Ship atk = atl.get(atl.size() - 1);

							atk.subtractHealth((int) ((s.getAttack() + s.getStrengths().get(strAll[0])) * defenderBonus));
							if (atk.isDead())
							{
								atl.remove(atl.size() - 1);
							}
						}
					}
				}

				// evaluate attackers
				if (ships.containsKey(type))
				{
					// filter strengths based on defender makeup
					Set<Class<?>> strSet = new HashSet<Class<?>>(strengths.keySet());
					strSet.retainAll(defender.ships.keySet());
					Class<?>[] strAll = strSet.toArray(new Class<?>[] {});

					// get the list of defenders
					List<Ship> defenders = defender.getAll();

					// for each of the defenders ships of the current type, damage an attacker
					for (Ship s : ships.get(type))
					{
						// check whether to apply strength
						if (strSet.isEmpty() || !defender.ships.containsKey(strAll[0]))
						{
							Ship dfn = defenders.get(0);
							dfn.subtractHealth((int) (s.getAttack()));

							if (dfn.isDead())
							{
								defender.ships.get(dfn.getClass()).remove(dfn);
							}
						}
						else
						{
							// get list of weak ships and last one in that list
							List<Ship> dfl = defender.ships.get(strAll[0]);
							Ship dfn = dfl.get(dfl.size() - 1);

							dfn.subtractHealth((int) (s.getAttack()));
							if (dfn.isDead())
							{
								dfl.remove(dfl.size() - 1);
							}
						}
					}
				}
			}
		}
	}

	public void attack(Planet p) throws InstantiationException, IllegalAccessException
	{
		attack(p.getShipInventory(), ConfigurationManager.planetDefenderBonus);
	}

}
