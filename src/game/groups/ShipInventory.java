package game.groups;

import game.entities.Destroyer;

public class ShipInventory extends ShipGroup
{
	
	public ShipInventory()
	{
		super();
	}

	public void add(int production)
	{
		add(Destroyer.class, production);
	}

}
