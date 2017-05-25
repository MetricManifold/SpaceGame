package game.tiles;

import java.util.concurrent.ThreadLocalRandom;

import game.entities.Ship;
import game.groups.Fleet;
import game.groups.ShipInventory;
import game.helpers.Displacement;
import game.managers.ConfigurationManager;
import game.players.Player;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Planet extends Space
{
	// production per turn and ships
	private int production;
	private ShipInventory ships = new ShipInventory();
	private String name = ConfigurationManager.PlanetName.getName();
	private Player owner = null;

	public Planet(Displacement pos)
	{
		this(pos, ThreadLocalRandom.current().nextInt(5, ConfigurationManager.maxProduction));
	}

	public Planet(int x, int y, int production, String name, Player owner)
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
	}

	/**
	 * give ships to this planet
	 * 
	 * @param type
	 *            type of ship to add
	 * @param num
	 *            number of ships
	 */
	public void addShips(Class<? extends Ship> type, int num)
	{
		ships.add(type, num);
	}

	public void produceShips()
	{
		ships.add(production);
	}

	/**
	 * updates the tooltip for this planet
	 */
	@Override
	public Tooltip getTooltip()
	{
		String strNumShips = (owner.getNum() == 0 && !ConfigurationManager.neutralShipsVisible) ? "?" : String.valueOf(getShipCount());
		String strStats = String.format("Name:\t%s\nShips:\t%s\nIndustry:\t%d\nOwner:\t", getName(), strNumShips, getProduction());

		// create text
		Text stats = new Text(strStats);
		Text name = new Text(owner.getName());
		stats.setFill(Color.WHITE);
		name.getStyleClass().add(owner.getColor());

		TextFlow txt = new TextFlow(stats, name);
		txt.setPrefHeight(0);

		tooltip.setGraphic(txt);
		return tooltip;
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

	public int getShipCount()
	{
		return  ships.getCount(ConfigurationManager.defaultShip);
	}

	/**
	 * set the owner of this planet to the specified player
	 * 
	 * @param owner
	 */
	public void setOwner(Player owner)
	{
		this.owner = owner;
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
	}

}
