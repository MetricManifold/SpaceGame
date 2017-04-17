package game.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ShipGroup
{
	protected List<Ship> ships = new ArrayList<Ship>();
	protected Map<Class<?>, Integer> count = new HashMap<Class<?>, Integer>();

	public ShipGroup()
	{}

	public ShipGroup(List<Ship> ships)
	{
		add(ships);
	}

	public ShipGroup(Ship ship)
	{
		add(ship);
	}
	
	public int getCount(Class<?> type)
	{
		Integer c = count.get(type);
		
		if (c == null) return 0;
		else return c;
	}

	public void add(Class<?> type, int num)
	{
		try
		{
			for (int i = 0; i < num; i++)
			{
				add((Ship) type.newInstance());
			}
		}
		catch (Exception e)
		{
			System.out.println("unable to add ship");
		}
	}

	public void add(Ship ship)
	{
		Class<?> cs = ship.getClass();
		Integer n = count.get(cs);

		if (n == null) count.put(cs, 1);
		else count.put(cs, n + 1);

		ships.add(ship);
	}

	public void add(List<Ship> ships)
	{
		for (Ship s : ships)
		{
			add(s);
		}
	}
	
	public void add(ShipGroup g)
	{
		for (Ship s : g.ships)
		{
			ships.add(s);
		}
	}

	public void remove(Ship ship)
	{
		Class<?> cs = ship.getClass();
		Integer n = count.get(cs);

		if (n == null || n == 0)
		{
			System.out.println("attempted to remove ship that doesn't exist");
		}
		else
		{
			ships.remove(ship);
			count.put(cs, n - 1);
		}
	}

	public void remove(List<Ship> ships)
	{
		for (Ship s : ships)
		{
			remove(s);
		}
	}
	

	public Fleet take(Class<?> type, int num) throws Exception
	{
		List<Ship> sendShips = new ArrayList<Ship>();
		Fleet f = new Fleet();
		int track = 0;
		
		// handle no ships to return
		if (num <= 0) return f;
		
		// iterate through the list to mark all the ships to move
		for (Iterator<Ship> it = ships.iterator(); it.hasNext() && track < num;)
		{
			Ship s = it.next();
			if (s.getClass() == type)
			{
				sendShips.add(s);
				track++;
			}
		}
		
		// if too few ships were found, throw an error
		if (track != num) throw new Exception("not enough ships");
		
		// remove the ships and add them to the new fleet
		for (Ship s : sendShips)
		{
			remove(s);
		}
		
		// return the assembled fleet
		return f;
	}

}
