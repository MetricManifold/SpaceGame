package game.objects;

import java.util.concurrent.ThreadLocalRandom;
import game.helpers.Displacement;
import game.managers.ConfigurationManager;
import game.players.Player;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
	 * 
	 * @return
	 */
	public int getProduction()
	{
		return production;
	}

	/**
	 * Return the ship inventory for this planet
	 * 
	 * @return
	 */
	public ShipInventory getShipInventory()
	{
		return ships;
	}

	/**
	 * add a fleet of ships to this planet
	 * 
	 * @param f
	 */
	public void addShips(Fleet f)
	{
		ships.add(f);
		updateToolTip();
	}

	/**
	 * give ships to this planet
	 * 
	 * @param type
	 *            type of ship to add
	 * @param num
	 *            number of ships
	 */
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
		String strNumShips = (owner.getNum() != 0) ? "Ships:\t" + ships.getCount(ConfigurationManager.defaultShip) + "\n" : "";

		// create text
		Text stats = new Text("Name:\t" + getName() + "\n" +
				strNumShips +
				"Industry:\t" + getProduction() + "\n" +
				"Owner:\t");
		Text name = new Text(owner.getName());

		// format text
		stats.setFill(Color.WHITE);
		name.getStyleClass().add(owner.getColor());
		TextFlow txt = new TextFlow(stats, name);
		txt.setPrefHeight(0);

		tooltip.setGraphic(txt);
	}

	/**
	 * get the name of this planet
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * set the owner of this planet to the specified player
	 * 
	 * @param owner
	 */
	public void setOwner(Player owner)
	{
		this.owner = owner;
		updateToolTip();
	}

	/**
	 * return the player who owns the planet
	 * 
	 * @return
	 */
	public Player getOwner()
	{
		return owner;
	}

	/**
	 * change the production of the planet
	 * 
	 * @param production
	 */
	public void setProduction(int production)
	{
		this.production = production;
		updateToolTip();
	}

}
