package game.tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.groups.Fleet;
import game.groups.ShipInventory;
import game.helpers.Displacement;
import game.managers.ConfigManager;
import game.managers.ConfigManager.ShipType;
import game.players.Player;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Planet extends Space
{
	// production per turn and ships
	protected Map<ShipType, Integer> production = new HashMap<>();
	protected ShipInventory ships = new ShipInventory();
	protected String name;
	protected Player owner = null;
	protected boolean displayShips = true;
	protected double defenderBonus;
	protected ShipType defaultShip;

	public Planet(Displacement pos, ConfigManager CM)
	{
		super(pos);
		this.name = CM.getPlanetName();
		this.defaultShip = CM.defaultShip;
		this.defenderBonus = CM.planetDefenderBonus;
		
		production.put(defaultShip, ThreadLocalRandom.current().nextInt(CM.minProduction, CM.maxProduction));
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
	public Map<ShipType, Integer> getProduction()
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
	public void addShips(ShipType type, int num)
	{
		ships.add(type, num);
	}

	/**
	 * produces the given number of ships based on the production value
	 * 
	 * @param type
	 */
	public void produceShips(ShipType type)
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
		String strStats = String.format("Name:\t%s\nShips:\t%s\nIndustry:\t%d\nOwner:\t", getName(), strNumShips, getProduction().get(defaultShip));

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
		if (this.owner != null)
		{
			this.owner.removePlanet(this);
		}
		
		this.owner = owner;
		owner.addPlanet(this);
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
	public void setProduction(ShipType type, int production)
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
	
	public void setDefenderBonus(double newDefenderBonus)
	{
		defenderBonus = newDefenderBonus;
	}
	
	public void setDefaultShip(ShipType newDefaultShip)
	{
		defaultShip = newDefaultShip;
	}
	
	public ShipType getDefaultShip()
	{
		return defaultShip;
	}

}
