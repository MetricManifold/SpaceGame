package game.entities;

import game.helpers.Tuple;
import game.managers.ConfigManager.ShipType;

public class Bomber extends Ship
{
	public Bomber()
	{
		super();
		attack = 10;
		armor = 1;
		health = 50;
		type = ShipType.BOMBER;
		
		strength = new Tuple<ShipType, Integer>(ShipType.DESTROYER, 5);
	}
}
