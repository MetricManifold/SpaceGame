package game.groups;

import java.util.List;

import game.entities.Destroyer;
import game.entities.Ship;

public class ShipInventory extends ShipGroup
{
	
	public ShipInventory()
	{
		super();
	}

	public ShipInventory(Class<?> type, int num)
	{
		super(type, num);
	}

	public ShipInventory(List<Ship> list)
	{
		super(list);
	}

	public void add(int production)
	{
		add(Destroyer.class, production);
	}

	/**
	 * get a fleet of all the ships of the given type from the group
	 * 
	 * @param type
	 * @return
	 */
	public ShipInventory getAll(Class<?> type)
	{
		return new ShipInventory(ships.get(type));
	}
	
	/**
	 * return a new fleet formed by removing ships from this group and adding it to the new one
	 * 
	 * @param type
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public ShipInventory take(Class<?> type, int num) throws Exception
	{
		// log message
		System.out.println("taking " + String.valueOf(num) + " ships of type " + type.getName());
		System.out.println("there are " + String.valueOf(getCount(type)) + " ships of that type");
		
		// handle no ships to return
		if (num <= 0) return null;
		remove(type, num);

		// return the assembled fleet
		return new ShipInventory(type, num);
	}
}
