package com.silber.groups;

import java.util.List;
import com.silber.entities.Ship;
import com.silber.managers.ConfigManager.ShipType;

public class ShipInventory extends ShipGroup
{
	
	public ShipInventory()
	{
		super();
	}
	
	public ShipInventory(ShipInventory s)
	{
		super(s);
	}

	public ShipInventory(ShipType type, int num)
	{
		super(type, num);
	}

	public ShipInventory(List<Ship> list)
	{
		super(list);
	}

	/**
	 * get a fleet of all the ships of the given type from the group
	 * 
	 * @param type
	 * @return
	 */
	public ShipInventory getAll(ShipType type)
	{
		return new ShipInventory(ships.get(type));
	}
	
}
