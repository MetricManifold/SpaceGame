package game.entities;

import game.helpers.Tuple;
import game.managers.ConfigManager.ShipType;

public class Fighter extends Ship
{
	public Fighter()
	{
		super();
		attack = 1;
		armor = 0;
		health = 50;
		type = ShipType.FIGHTER;
		
		strength = new Tuple<ShipType, Integer>(ShipType.DESTROYER, 5);
	}
}
