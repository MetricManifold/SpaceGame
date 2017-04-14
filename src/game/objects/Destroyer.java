package game.objects;

import game.helpers.Displacement;

public class Destroyer extends Ship
{
	public Destroyer(Displacement pos)
	{
		super(pos);
		power = 10;
		armor = 1;
	}
}
