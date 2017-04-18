package game.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.entities.Ship;

public class ShipGroup
{
	protected Map<Class<?>, List<Ship>> ships = new HashMap<Class<?>, List<Ship>>();

	public ShipGroup()
	{
	}

	public ShipGroup(ShipGroup group)
	{
		this.ships = group.ships;
	}

	public ShipGroup(Class<?> type, int num)
	{
		add(type, num);
	}

	public ShipGroup(Ship ship)
	{
		add(ship);
	}

	public ShipGroup(Collection<Ship> ships)
	{
		add(ships);
	}

	public int getCount(Class<?> type)
	{
		List<Ship> c = ships.get(type);

		if (c == null)
			return 0;
		else
			return c.size();
	}
	
	public int getCount()
	{
		int total = 0;
		for (Class<?> type : ships.keySet())
		{
			total += getCount(type);
		}
		
		return total;
	}

	/**
	 * add given number of ship type to this group
	 * 
	 * @param type
	 * @param num
	 */
	public void add(Class<?> type, int num)
	{
		if (ships.get(type) == null)
		{
			ships.put(type, new ArrayList<Ship>());
		}

		try
		{
			for (int i = 0; i < num; i++)
			{
				Object s = type.getConstructor().newInstance();
				ships.get(type).add((Ship) s);
			}
		}
		catch (Exception e)
		{
		}

	}

	/**
	 * add the given ship to this group
	 * 
	 * @param ship
	 */
	public void add(Ship ship)
	{
		Class<?> type = ship.getClass();
		if (ships.get(type) == null)
		{
			ships.put(type, Arrays.asList(ship));
		}
		else
		{
			ships.get(type).add(ship);
		}
	}

	/**
	 * add a list of ships to this group
	 * 
	 * @param ships
	 */
	public void add(Collection<Ship> ships)
	{
		if (ships.size() > 0)
		{
			Class<?> type = ships.toArray()[0].getClass();
			if (this.ships.get(type) == null)
			{
				this.ships.put(type, new ArrayList<>(ships));
			}
			else
			{
				this.ships.get(type).addAll(ships);
			}
		}
	}

	/**
	 * add another shipgroup to this group
	 * 
	 * @param g
	 */
	public void add(ShipGroup g)
	{
		for (List<Ship> l : g.ships.values())
		{
			add(l);
		}
	}

	/**
	 * remove a given ship from this group
	 * 
	 * @param ship
	 * @throws Exception
	 *             if ship does not exist
	 */
	public void remove(Ship ship) throws Exception
	{
		Class<?> type = ship.getClass();
		List<Ship> l = ships.get(type);

		if (l == null || l.isEmpty())
		{
			throw new Exception("attempted to remove ship that doesn't exist");
		}
		else
		{
			l.remove(ship);
		}
	}

	/**
	 * removes a list of ships from this group
	 * 
	 * @param ships
	 * @throws Exception
	 */
	public void remove(Collection<Ship> ships) throws Exception
	{
		Class<?> type = ships.toArray()[0].getClass();
		boolean result = this.ships.get(type).removeAll(ships);

		if (!result)
		{
			throw new Exception("attempted to remove ship that doesn't exist");
		}
	}

	/**
	 * removes a given number of type of ship from the group
	 * 
	 * @param type
	 * @param num
	 * @throws Exception
	 */
	public void remove(Class<?> type, int num) throws Exception
	{
		for (int i = 0; i < num; i++)
		{
			ships.get(type).remove(0);
		}
	}

	/**
	 * remove all ships from the group
	 */
	public void removeAll()
	{
		ships = new HashMap<Class<?>, List<Ship>>();
	}

	/**
	 * get a fleet of all the ships of the given type from the group
	 * 
	 * @param type
	 * @return
	 */
	public ShipGroup getAll(Class<?> type)
	{
		return new ShipGroup(ships.get(type));
	}
	
	/**
	 * get the whole list of ships
	 * @return
	 */
	public List<Ship> getAll()
	{
		return Arrays.asList(ships.values().toArray(new Ship[]{}));
	}
	
	/**
	 * return a new fleet formed by removing ships from this group and adding it to the new one
	 * 
	 * @param type
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public ShipGroup take(Class<?> type, int num) throws Exception
	{
		List<Ship> sendShips = new ArrayList<Ship>();
		int track = 0;

		// handle no ships to return
		if (num <= 0) return null;

		// iterate through the list to mark all the ships to move
		for (Ship s : ships.get(type))
		{
			if (track++ > num) break;
			sendShips.add(s);
		}

		// if too few ships were found, throw an error
		if (track != num) throw new Exception("not enough ships");

		// remove the ships and add them to the new fleet
		for (Ship s : sendShips)
		{
			remove(s);
		}
		
		// return the assembled fleet
		return new ShipGroup(sendShips);
	}

}
