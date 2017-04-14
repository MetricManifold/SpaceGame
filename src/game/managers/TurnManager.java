package game.managers;

import java.util.ArrayList;
import java.util.List;
import game.objects.Planet;
import game.objects.Ship;
import game.ui.PlanetGrid;

public class TurnManager
{
	private final PlanetGrid pg;
	private List<Ship> movingShips = new ArrayList<Ship>();

	public TurnManager(PlanetGrid pg)
	{
		this.pg = pg;
	}

	private void updateInventories()
	{
		for (Planet p : pg.planets)
		{
			p.updateShipInventory();
		}
	}

	private void moveShips()
	{

	}

	public void nextTurn()
	{
		updateInventories();
	}
}
