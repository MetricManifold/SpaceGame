package game.tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.entities.Ship;
import game.groups.Fleet;
import game.groups.ShipInventory;
import game.helpers.Displacement;
import game.managers.ConfigManager;
import game.players.Player;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Planet extends Space
{
	// production per turn and ships
	private Map<Class<? extends Ship>, Integer> production;
	private ShipInventory ships;
	private String name;
	private Player owner;
	boolean displayShips = true;
	private double defenderBonus;

	public Planet(Displacement pos, ConfigManager cm)
	{
		super(pos);
		this.name = cm.getPlanetName();
		this.production = new HashMap<>();
		this.ships = new ShipInventory();
		this.owner = null;
		
		production.put(cm.defaultShip, ThreadLocalRandom.current().nextInt(cm.minProduction, cm.maxProduction));
	}

	public Planet(Space s, ConfigManager cm)
	{
		this(s.pos, cm);
	}

	/**
	 * Return the production capacity for this planet
	 * 
	 * @return
	 */
	public Map<Class<? extends Ship>, Integer> getProduction()
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

	/**
	 * produces the given number of ships based on the production value
	 * 
	 * @param type
	 */
	public void produceShips(Class<? extends Ship> type)
	{
		ships.add(type, production.get(type));
	}

	/**
	 * updates the tooltip for this planet
	 */
	@Override
	public Tooltip getTooltip()
	{
		String strNumShips = (owner.getNum() == 0 && !displayShips) ? "?" : String.valueOf(getShipCount());
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
		return ships.getCount();
	}

	/**
	 * set the owner of this planet to the specified player
	 * 
	 * @param owner
	 */
	public void setOwner(Player owner)
	{
		if (owner != null)
		{
			owner.removePlanet(this);
		}
		
		this.owner = owner;
		owner.getPlanetList().add(this);
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
	public void setProduction(Class<? extends Ship> type, int production)
	{
		this.production.put(type, production);
	}

	public void setDisplayShips(boolean value)
	{
		displayShips = value;
	}

	public double getDefenderBonus()
	{
		return defenderBonus;
	}

}
