package game.managers;

import java.util.ArrayList;
import game.groups.Fleet;
import game.players.Player;
import game.tiles.Planet;

/**
 * Responsible for managing turns including updating planet production, fleets sent to other planets Also contains the graphical element
 * 
 * @author MR SMITH
 *
 */
public class TurnManager
{
	protected PlanetManager PG;
	protected PlayerManager PM;
	protected ConfigManager CM;
	protected ArrayList<Fleet> fleets;

	public TurnManager(ConfigManager CM)
	{
		this.CM = CM;
		initialize();
	}

	/**
	 * set the events when interacting with the turnbar
	 * 
	 * @param pm
	 * @param pg
	 */
	public void setup(PlayerManager pm, PlanetManager pg)
	{
		PM = pm;
		PG = pg;
	}
	
	public void reset()
	{
		initialize();
	}
	
	protected void initialize()
	{
		fleets = new ArrayList<Fleet>();
	}

	/**
	 * activate the next turn
	 * 
	 */
	public void nextTurn()
	{
		PM.nextPlayer();

		// produce ships if all players have made their turn
		if (PM.getPlayer(1) == PM.getCurrentPlayer())
		{
			for (Planet p : PG.getPlanetArray())
			{
				// if owner is neutral, produce 75% total ships
				if (p.getOwner() == PM.neutral)
				{
					p.addShips(CM.defaultShip, (int) (p.getProduction().get(CM.defaultShip) * CM.neutralProdModifier));
				}
				else
				{
					p.produceShips(CM.defaultShip);
				}
			}

			for (Fleet f : fleets)
			{
				try
				{
					f.update(PG);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			fleets.removeIf(f -> f.getCount() == 0);
		}

		PM.getCurrentPlayer().update(PG, this, PM);
	}

	/**
	 * return a copy of the set containing the fleets
	 * 
	 * @return
	 */
	public ArrayList<Fleet> getFleets()
	{
		return new ArrayList<>(fleets);
	}


	/**
	 * send the given number of ships
	 * 
	 * @param num
	 */
	public void sendShips(int num)
	{
		Player p = PM.getCurrentPlayer();
		Fleet f = new Fleet(CM.defaultShip, num, p);

		if (PM.canSend(p, f))
		{
			try
			{
				Planet o = p.getOrigin();
				Planet d = p.getDestination();

				o.getShipInventory().remove(CM.defaultShip, num);
				f.send(o, d);
				fleets.add(f);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
