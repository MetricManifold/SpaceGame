package game.objects;

import java.util.ArrayList;
import java.util.List;

public class ShipInventory
{
	private Planet owner;
	private List<Ship> ships = new ArrayList<Ship>();
	
	public ShipInventory(Planet owner)
	{
		this.owner = owner;
	}
	
	public void add(int production)
	{
		for (int i = 0; i < production; i++)
		{
			ships.add(new Destroyer(owner.pos));
		}
	}

}
