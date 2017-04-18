package game.entities;

import java.util.HashMap;
import java.util.Map;

public abstract class Ship
{
	protected float speed = (float) 1.0;
	protected int attack = 1, armor = 0, health = 100, maxHealth = 100;

	protected Map<Class<?>, Integer> strengths = new HashMap<Class<?>, Integer>();

	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public int getAttack()
	{
		return attack;
	}

	public void setAttack(int attack)
	{
		this.attack = attack;
	}

	public int getArmor()
	{
		return armor;
	}

	public void setArmor(int armor)
	{
		this.armor = armor;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public void maxHealth()
	{
		health = maxHealth;
	}

	public Map<Class<?>, Integer> getStrengths()
	{
		return strengths;
	}

	public void setStrengths(Map<Class<?>, Integer> strengths)
	{
		this.strengths = strengths;
	}

	/**
	 * subtracts the given value from health and returns true if dead
	 * 
	 * @param sub
	 * @return
	 */
	public void subtractHealth(int sub)
	{
		health -= sub;
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

}
