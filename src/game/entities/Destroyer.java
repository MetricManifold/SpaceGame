package game.entities;

import game.managers.ConfigManager.ShipType;

public class Destroyer extends Ship
{
	public Destroyer()
	{
		super();
		attack = 10;
		armor = 1;
		health = 200;
		type = ShipType.DESTROYER;
	}
}
