package game.objects;

import java.util.Collection;
import java.util.List;
import game.helpers.Displacement;
import game.managers.ConfigurationManager;

public class Fleet extends ShipGroup
{
	private Displacement path = null;
	private Planet destination = null;
	private float speed = Integer.MAX_VALUE;

	public Fleet()
	{
	}

	public Fleet(ShipGroup group)
	{
		super(group);
	}

	public Fleet(Class<?> type, int num)
	{
		super(type, num);
	}

	public Fleet(Ship ship)
	{
		super(ship);
	}

	public Fleet(Collection<Ship> ships)
	{
		super(ships);
	}

	public void update()
	{
		if (path != null)
		{
			if (path.subtract(speed).length() < speed)
			{
				attack(destination);
				
				path = null;
				destination = null;
			}
			else
			{
				path.add(speed);
			}
		}
	}

	public void send(Planet origin, Planet dest)
	{
		this.path = dest.pos.subtract(origin.pos);
		this.destination = dest;

		for (List<Ship> l : ships.values())
		{
			for (Ship s : l)
			{
				if (s.speed < speed) speed = s.speed;
			}
		}
	}

	public void attack(ShipGroup defender, double defenderBonus)
	{

	}

	public void attack(Planet p)
	{
		attack(p.getShipInventory(), ConfigurationManager.planetDefenderBonus);
	}

}
