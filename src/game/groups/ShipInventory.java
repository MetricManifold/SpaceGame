package game.groups;

import java.util.List;

import game.entities.Ship;
import game.managers.ConfigurationManager;

public class ShipInventory extends ShipGroup
{
	
	public ShipInventory()
	{
		super();
	}

	public ShipInventory(Class<? extends Ship> type, int num)
	{
		super(type, num);
	}

	public ShipInventory(List<Ship> list)
	{
		super(list);
	}

	public void add(int production)
	{
		add(ConfigurationManager.defaultShip, production);
	}

	/**
	 * get a fleet of all the ships of the given type from the group
	 * 
	 * @param type
	 * @return
	 */
	public ShipInventory getAll(Class<? extends Ship> type)
	{
		return new ShipInventory(ships.get(type));
	}
	
}
