package game.groups;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import game.entities.Ship;
import game.helpers.AccumulateInteger;
import game.helpers.Displacement;
import game.helpers.Pointer;
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

		// a denotes attacker, b denotes defender
		Pointer<Integer> c_a = new Pointer<>(getCount());
		Pointer<Integer> c_b = new Pointer<>(defender.getCount());
		Pointer<Integer> d_a = new Pointer<>(0);
		Pointer<Integer> d_b = new Pointer<>(0);
		Map<Class<? extends Ship>, Pointer<Integer>> d_a_str = new HashMap<>();
		Map<Class<? extends Ship>, Pointer<Integer>> d_b_str = new HashMap<>();
		Map<Class<? extends Ship>, Pointer<Integer>> h_a_str = new HashMap<>();
		Map<Class<? extends Ship>, Pointer<Integer>> h_b_str = new HashMap<>();

		// get the total attack of the fleet
		getAll().forEach(s -> d_a.v += s.attack);
		defender.getAll().forEach(s -> d_b.v += (int) (s.attack * defenderBonus));

		// create a damage map to pointer by type
		ships.keySet().forEach(k -> {
			d_a_str.put(k, new Pointer<Integer>(0));
			ships.get(k).forEach(s -> d_a_str.get(k).v += s.attack);
		});
		defender.ships.keySet().forEach(k -> {
			d_b_str.put(k, new Pointer<Integer>(0));
			ships.get(k).forEach(s -> d_b_str.get(k).v += s.attack);
		});

		// create a health map to pointer by type
		ships.keySet().forEach(k -> {
			h_a_str.put(k, new Pointer<Integer>(0));
			ships.get(k).forEach(s -> h_a_str.get(k).v += s.health);
		});
		defender.ships.keySet().forEach(k -> {
			h_b_str.put(k, new Pointer<Integer>(0));
			ships.get(k).forEach(s -> h_b_str.get(k).v += s.health);
		});


	}

	public void attack(Planet p)
	{
		attack(p.getShipInventory(), ConfigurationManager.planetDefenderBonus);
	}

}
