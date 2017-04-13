package game.objects;

import java.util.concurrent.ThreadLocalRandom;
import game.helpers.Displacement;

public class Planet
{
	private static final int PRODMAX = 30, PRODMIN = 5;

	private int production;
	private ShipInventory ships = new ShipInventory();
	private Displacement pos;

	public Planet(int x, int y)
	{
		this(new Displacement(x, y));
	}

	public Planet(Displacement pos)
	{
		this.pos = pos;
		production = ThreadLocalRandom.current().nextInt(PRODMIN, PRODMAX + 1);
	}

	public Planet(int x, int y, int production)
	{
		this(new Displacement(x, y), production);
	}

	public Planet(Displacement pos, int production)
	{
		this.pos = pos;
		this.production = production;
	}

	public int getProduction()
	{
		return production;
	}

	public ShipInventory getShipInventory()
	{
		return ships;
	}

	public double distanceTo(Planet p)
	{
		return p.pos.subtract(pos).length();
	}

}
