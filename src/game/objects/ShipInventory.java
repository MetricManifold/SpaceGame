package game.objects;

import game.managers.ConfigurationManager;

public class ShipInventory extends ShipGroup
{
	public ShipInventory(Planet owner)
	{
		super();
	}

	public void add(int production)
	{
		add(Destroyer.class, production);
	}

	public void getCount()
	{
		getCount(ConfigurationManager.defaultShip);
	}

}
