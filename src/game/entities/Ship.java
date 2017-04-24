package game.entities;

import game.helpers.Tuple;

public abstract class Ship
{
	public float speed = 1.0f;
	public int attack = 1, armor = 0, health = 100, maxHealth = 100;
	protected Tuple<Class<? extends Ship>, Integer> strength;

	public Tuple<Class<? extends Ship>, Integer> getStrength()
	{
		return strength;
	}

	/**
	 * get whether or not health has been depleted
	 * 
	 * @return
	 */
	public boolean isDead()
	{
		return health <= 0;
	}
	
	public void heal()
	{
		health = maxHealth;
	}
}
