package game.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.entities.Ship;
import game.helpers.AccumulateInteger;
import game.helpers.Tuple;

public abstract class ShipGroup
{
	protected Map<Class<? extends Ship>, List<Ship>> ships = new HashMap<>();

	public ShipGroup()
	{
	}

	public ShipGroup(ShipGroup group)
	{
		this.ships = group.ships;
	}

	public ShipGroup(Class<? extends Ship> type, int num)
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

	public int getCount(Class<? extends Ship> type)
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
		for (Class<? extends Ship> type : ships.keySet())
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
	public void add(Class<? extends Ship> type, int num)
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
		Class<? extends Ship> type = ship.getClass();
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
			Class<? extends Ship> type = ships.iterator().next().getClass();
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
		Class<? extends Ship> type = ship.getClass();
		List<Ship> l = ships.get(type);

		if (l == null || l.isEmpty())
			throw new Exception("attempted to remove ship that doesn't exist");
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
			List<Ship> sl = this.ships.get(ships.iterator().next().getClass());
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
	public void remove(Class<? extends Ship> type, int num) throws Exception
	{
		for (int i = 0, len = ships.get(type).size(); i < num; i++)
		{
			ships.get(type).remove(len - i - 0);
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
		for (Class<? extends Ship> type : ships.keySet())
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
		for (Class<? extends Ship> type : g.ships.keySet())
		{
			if (getCount(type) < g.getCount(type)) return false;
		}

		return true;
	}

	/**
	 * sums the total damage from the ships in this group into the given map object, arranging it based on strengths vs {@code keys}, otherwise placed
	 * accumulated by {@code Ship.class} must iterate over all ships in the group
	 * 
	 * @param atk
	 */
	protected Map<Class<? extends Ship>, AccumulateInteger> countDamage(Set<Class<? extends Ship>> keys)
	{
		Map<Class<? extends Ship>, AccumulateInteger> atk = new HashMap<>();
		keys.forEach(t -> atk.put(t, new AccumulateInteger()));
		atk.put(Ship.class, new AccumulateInteger());

		for (Ship s : getAll())
		{
			Tuple<Class<? extends Ship>, Integer> str = s.getStrength();
			if (str._1 != null)
			{
				atk.get(str._1).add(str._2);
			}
			atk.get(Ship.class).add(s.attack);
		}
		
		
		return atk;
	}

	/**
	 * get the total damage done by the fleet (not including strengths)
	 * @return
	 */
	protected int getTotalDamage()
	{
		Map<Class<? extends Ship>, AccumulateInteger> atk = countDamage(ships.keySet());
		return atk.get(Ship.class).get();
	}
	
	/**
	 * get the average damage of the fleet
	 * @return
	 */
	protected int getAverageDamage()
	{
		return getTotalDamage() / getCount();
	}

	/**
	 * returns a mapping of each ship type to total type health
	 */
	protected Map<Class<? extends Ship>, AccumulateInteger> countHealth()
	{
		Map<Class<? extends Ship>, AccumulateInteger> hp = new HashMap<>();
		ships.keySet().forEach(t -> hp.put(t, new AccumulateInteger()));

		for (Ship s : getAll())
		{
			hp.get(s.getClass()).add(s.health);
		}
		
		return hp;
	}
	
	/**
	 * returns a mapping of each ship type to maximum health of family
	 */
	protected Map<Class<? extends Ship>, AccumulateInteger> countMaxHealth()
	{
		Map<Class<? extends Ship>, AccumulateInteger> hp = new HashMap<>();
		ships.keySet().forEach(t -> hp.put(t, new AccumulateInteger()));

		for (Class<? extends Ship> t : ships.keySet())
		{
			try
			{
				hp.get(t).add(t.newInstance().maxHealth * getCount(t));
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
	 * @return
	 */
	protected int getTotalHealth()
	{
		Map<Class<? extends Ship>, AccumulateInteger> hp = countHealth();
		int total = 0;
		
		for (AccumulateInteger v : hp.values())
		{
			total += v.get();
		}
		
		return total;
	}
	
	/**
	 * get the average health of the fleet
	 * @return
	 */
	protected int getAverageHealth()
	{
		return getTotalHealth() / getCount();
	}
}
