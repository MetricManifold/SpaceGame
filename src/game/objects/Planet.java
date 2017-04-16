package game.objects;

import java.util.concurrent.ThreadLocalRandom;
import game.helpers.Displacement;
import game.managers.ConfigurationManager;
import game.players.Player;

public class Planet extends Space
{
	// production range of the planet
	private static final int PRODMAX = 30, PRODMIN = 5;

	// production per turn and ships
	private int production;
	private ShipInventory ships = new ShipInventory(this);
	private String name = ConfigurationManager.PlanetName.getName();
	private Player owner = null;

	public Planet(Displacement pos)
	{
		super(pos);
		production = ThreadLocalRandom.current().nextInt(PRODMIN, PRODMAX + 1);
	}

	public Planet(int x, int y, int production, String name)
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
	
	public void addShips(Fleet f)
	{
		ships.add(f);
		updateToolTip();
	}
	
	public void addShips(Class<?> type, int num)
	{
		ships.add(type, num);
		updateToolTip();
	}
	
	public void produceShips()
	{
		ships.add(production);
		updateToolTip();
	}

	@Override
	public void updateToolTip()
	{
		String txt = "Name:\t" + getName() + "\n" +
				"Ships:\t" + ships.getCount(ConfigurationManager.defaultShip) + "\n" +
				"Industry:\t" + getProduction() + "\n" + 
				"Owner:\t" + owner.getName() + "\n";
		tooltip.setText(txt);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setOwner(Player owner)
	{
		this.owner = owner;
		updateToolTip();
	}
	
	public Player getOwner()
	{
		return owner;
	}

}
