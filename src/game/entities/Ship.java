package game.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Ship
{
	public float speed = 1.0f;
	public int attack = 1, armor = 0, health = 100, maxHealth = 100;
	protected Map<Class<? extends Ship>, Integer> strengths = new HashMap<>();

	public Map<Class<? extends Ship>, Integer> getStrengths()
	{
		return strengths;
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

	/**
	 * returns the bonus associated with the type of ship
	 * 
	 * @param type
	 * @return
	 */
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

	/**
	 * 
	 * @param cs
	 * @return
	 */
	public Class<? extends Ship> getFirstStrengthFrom(Set<Class<? extends Ship>> cs)
	{
		if (cs.retainAll(strengths.keySet()) && !cs.isEmpty())
		{
			return cs.iterator().next();
		}
		else
		{
			return null;
		}
	}

	public void heal()
	{
		health = maxHealth;
	}
}
