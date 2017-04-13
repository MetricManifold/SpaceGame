package game.objects;

import javafx.scene.control.Button;

public class Planet
{
	public final Button button = new Button();
	
	private int x, y, production;
	private ShipInventory ships = new ShipInventory();
	
	public Planet(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getProduction()
	{
		return production;
	}
	
	public ShipInventory getShipInventory()
	{
		return ships;
	}
	
}
