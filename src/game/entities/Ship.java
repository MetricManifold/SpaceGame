package game.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Ship
{
	protected float speed = 1.0f;
	protected int attack = 1, armor = 0, health = 100, maxHealth = 100;

	protected Map<Class<? extends Ship>, Integer> strengths = new HashMap<>();

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

	public Map<Class<? extends Ship>, Integer> getStrengths()
	{
		return strengths;
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
	
	public int getBonus(Class<? extends Ship> type)
	{
		if (strengths.containsKey(type))
		{
			return strengths.get(type);
		}
		else
		{
			return 0;
		}
	}
	
	public Class<? extends Ship> getFirstStrengthFrom(Set<Class<? extends Ship>> cs)
	{
		if (cs.retainAll(strengths.keySet()))
		{
			return cs.iterator().next();
		}
		else
		{
			return null;
		}
	}

}
