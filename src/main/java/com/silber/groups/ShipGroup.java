package com.silber.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.silber.entities.Ship;
import com.silber.helpers.AccumulateInteger;
import com.silber.helpers.Tuple;
import com.silber.managers.ConfigManager.ShipType;

public abstract class ShipGroup
{
	protected Map<ShipType, List<Ship>> ships = new HashMap<>();

	public ShipGroup()
	{}

	public ShipGroup(ShipGroup group)
	{
		this.ships = group.ships;
	}

	public ShipGroup(ShipType type, int num)
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

	public int getCount(ShipType type)
	{
		List<Ship> c = ships.get(type);
		if (c == null)
			return 0;
		else return c.size();
	}

	/**
	 * returns the total number of ships owned by the group
	 * 
	 * @return
	 */
	public int getCount()
	{
		int total = 0;
		for (ShipType type : ships.keySet())
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
	public void add(ShipType type, int num)
	{
		if (ships.get(type) == null)
		{
			ships.put(type, new ArrayList<Ship>());
		}

		try
		{
			for (int i = 0; i < num; i++)
			{
				Object s = type.getInstance();
				ships.get(type).add((Ship) s);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * add the given ship to this group
	 * 
	 * @param ship
	 */
	public void add(Ship ship)
	{
		ShipType type = ship.type;
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
			ShipType type = ships.iterator().next().type;
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
	 */
	public void remove(ShipGroup g)
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
	 *            if ship does not exist
	 */
	public void remove(Ship ship)
	{
		List<Ship> l = ships.get(ship.type);
		if (l != null && l.isEmpty()) l.remove(ship);
	}

	/**
	 * removes a list of ships from this group
	 * 
	 * @param ships
	 */
	public void remove(Collection<Ship> ships)
	{
		if (!ships.isEmpty())
		{
			List<Ship> sl = this.ships.get(ships.iterator().next().type);
			for (int i = 0, len = ships.size(); i < len; i++)
			{
				sl.remove(len - i - 1);
			}
		}

	}

	/**
	 * removes a given number of type of ship from the group
	 * 
	 * @param type
	 * @param num
	 */
	public void remove(ShipType type, int num)
	{
		for (int i = 0, len = ships.get(type).size(); i < num; i++)
		{
			ships.get(type).remove(len - i - 1);
		}
	}

	/**
	 * remove all ships from the group
	 */
	public void removeAll()
	{
		ships.clear();
	}

	/**
	 * get the whole list of ships
	 * 
	 * @return
	 */
	public List<Ship> getAll()
	{
		List<Ship> r = new ArrayList<Ship>();
		for (ShipType type : ships.keySet())
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
		for (ShipType type : g.ships.keySet())
		{
			if (getCount(type) < g.getCount(type)) return false;
		}

		return true;
	}

	/**
	 * sums the total damage from the ships in this group into the given map object, arranging it based on strengths vs {@code keys}, otherwise placed
	 * accumulated by {@code ShipType.GENERIC} must iterate over all ships in the group
	 * 
	 * @param atk
	 */
	protected Map<ShipType, AccumulateInteger> countDamage(Set<ShipType> keys)
	{
		Map<ShipType, AccumulateInteger> atk = new HashMap<>();
		keys.forEach(t -> atk.put(t, new AccumulateInteger()));
		atk.put(ShipType.GENERIC, new AccumulateInteger());

		for (Ship s : getAll())
		{
			Tuple<ShipType, Integer> str = s.getStrength();
			if (str._1 != null)
			{
				atk.get(str._1).add(str._2);
			}
			atk.get(ShipType.GENERIC).add(s.attack);
		}

		return atk;
	}

	/**
	 * get the total damage done by the fleet (not including strengths)
	 * 
	 * @return
	 */
	protected int getTotalDamage()
	{
		Map<ShipType, AccumulateInteger> atk = countDamage(ships.keySet());
		return atk.get(ShipType.GENERIC).get();
	}

	/**
	 * get the average damage of the fleet
	 * 
	 * @return
	 */
	protected int getAverageDamage()
	{
		return getTotalDamage() / getCount();
	}

	/**
	 * returns a mapping of each ship type to total type health
	 */
	protected Map<ShipType, AccumulateInteger> countHealth()
	{
		Map<ShipType, AccumulateInteger> hp = new HashMap<>();
		ships.keySet().forEach(t -> hp.put(t, new AccumulateInteger()));

		for (Ship s : getAll())
		{
			hp.get(s.type).add(s.health);
		}

		return hp;
	}

	/**
	 * returns a mapping of each ship type to maximum health of family
	 */
	protected Map<ShipType, AccumulateInteger> countMaxHealth()
	{
		Map<ShipType, AccumulateInteger> hp = new HashMap<>();
		ships.keySet().forEach(t -> hp.put(t, new AccumulateInteger()));

		for (ShipType t : ships.keySet())
		{
			try
			{
				hp.get(t).add(t.getInstance().maxHealth * getCount(t));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return hp;
	}

	/**
	 * get the total health of the fleet
	 * 
	 * @return
	 */
	protected int getTotalHealth()
	{
		Map<ShipType, AccumulateInteger> hp = countHealth();
		int total = 0;

		for (AccumulateInteger v : hp.values())
		{
			total += v.get();
		}

		return total;
	}

	/**
	 * get the average health of the fleet
	 * 
	 * @return
	 */
	protected int getAverageHealth()
	{
		return getTotalHealth() / getCount();
	}
}
