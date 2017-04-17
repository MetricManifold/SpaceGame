package game.objects;

import java.util.HashMap;
import java.util.Map;

public abstract class Ship
{
	protected float speed = 1.0f;
	protected int attack = 1;
	protected int armor = 0;
	protected int health = 100;

	protected Map<Class<?>, Integer> strengths = new HashMap<Class<?>, Integer>();
}
