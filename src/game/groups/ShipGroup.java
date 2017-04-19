package game.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.entities.Ship;

public abstract class ShipGroup
{
	protected Map<Class<?>, List<Ship>> ships = new HashMap<Class<?>, List<Ship>>();

	public ShipGroup()
	{}

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

	/**
	 * returns the total number of ships owned by the group
	 * 
	 * @return
	 */
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
		{}

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
	 * remove the given equivalent shipgroup from this group
	 * 
	 * @param g
	 * @throws Exception
	 */
	public void remove(ShipGroup g) throws Exception
	{
		for (List<Ship> l : g.ships.values())
		{
			remove(l);
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

		if (l == null || l.isEmpty()) throw new Exception("attempted to remove ship that doesn't exist");
		else l.remove(ship);
	}

	/**
	 * removes a list of ships from this group
	 * 
	 * @param ships
	 * @throws Exception
	 */
	public void remove(Collection<Ship> ships) throws Exception
	{
		if (!ships.isEmpty())
		{
			List<Ship> sl = this.ships.get(ships.toArray()[0].getClass());
			for (int i = 0, len = ships.size(); i < len; i++)
			{
				sl.remove(0);
			}
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
	 * get the whole list of ships
	 * 
	 * @return
	 */
	public List<Ship> getAll()
	{
		List<Ship> r = new ArrayList<Ship>();
		for (Class<?> type : ships.keySet())
		{
			r.addAll(ships.get(type));
		}

		return r;
	}

	/**
	 * check if this group contains the complete subgroup
	 * 
	 * @param g
	 * @return
	 */
	public boolean contains(ShipGroup g)
	{
		for (Class<?> type : g.ships.keySet())
		{
			if (getCount(type) < g.getCount(type)) return false;
		}

		return true;
	}

}
