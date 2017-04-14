package game.objects;

import java.util.concurrent.ThreadLocalRandom;
import game.helpers.Displacement;

public class Planet extends Space
{
	// production range of the planet
	private static final int PRODMAX = 30, PRODMIN = 5;

	// production per turn and ships
	private int production;
	private ShipInventory ships = new ShipInventory(this);

	public Planet(Displacement pos)
	{
		super(pos);
		production = ThreadLocalRandom.current().nextInt(PRODMIN, PRODMAX + 1);
	}

	public Planet(int x, int y, int production)
	{
		this(new Displacement(x, y), production);
	}

	public Planet(Displacement pos, int production)
	{
		super(pos);
		this.production = production;
	}

	public Planet(Planet p)
	{
		super(p);
		this.production = p.production;
		this.ships = p.ships;
	}
	
	public Planet(Space s)
	{
		this(s.pos);
	}

	/**
	 * Return the production capacity for this planet
	 * @return
	 */
	public int getProduction()
	{
		return production;
	}

	/**
	 * Return the ship inventory for this planet
	 * @return
	 */
	public ShipInventory getShipInventory()
	{
		return ships;
	}
	
	
	public void updateShipInventory()
	{
		ships.add(production);
	}

}
