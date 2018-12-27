package game.entities;

import game.helpers.Tuple;

public class Bomber extends Ship
{
	public Bomber()
	{
		super();
		attack = 10;
		armor = 1;
		health = 50;
		
		strength = new Tuple<Class<? extends Ship>, Integer>(Destroyer.class, 5);
	}
}
