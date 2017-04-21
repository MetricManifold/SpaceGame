package game.entities;


public class Fighter extends Ship
{
	public Fighter()
	{
		super();
		attack = 1;
		armor = 0;
		health = 50;
		
		strengths.put(Bomber.class, 5);
	}
}
