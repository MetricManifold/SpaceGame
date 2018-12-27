package com.silber.entities;

import com.silber.helpers.Tuple;
import com.silber.managers.ConfigManager.ShipType;

public abstract class Ship
{
	public ShipType type;
	public float speed = 1.0f;
	public int attack = 1, armor = 0, health = 100, maxHealth = 100;
	protected Tuple<ShipType, Integer> strength = new Tuple<>(null, 0);

	public Tuple<ShipType, Integer> getStrength()
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
