package game.groups;

import game.entities.Destroyer;
import game.managers.ConfigurationManager;
import game.tiles.Planet;

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
