package game.entities;

import game.helpers.Tuple;

public class Fighter extends Ship
{
	public Fighter()
	{
		super();
		attack = 1;
		armor = 0;
		health = 50;
		
		strength = new Tuple<Class<? extends Ship>, Integer>(Bomber.class, 5);
	}
}
