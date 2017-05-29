package game.managers;

import game.groups.ShipGroup;
import game.players.Player;

/**
 * Responsible for managing player actions and updating the current player per turn
 * 
 * @author MR SMITH
 *
 */
public class PlayerManager
{
	private int playerIndex = 1;
	private Player[] players;
	public final Player neutral;
	
	protected ConfigManager CM;

	public enum Controller
	{
		AI, HUMAN, NEUTRAL, HOSTILE
	};

	public PlayerManager(ConfigManager CM)
	{
		this.CM = CM;
		int numPlayers = CM.numPlayers;

		players = new Player[numPlayers];
		neutral = new Player(0, CM.COLORS[0], Controller.NEUTRAL);

		for (int i = 0; i < numPlayers; i++)
		{
			players[i] = new Player(i, CM.COLORS[i], Controller.HUMAN);
		}
	}

	public void nextPlayer()
	{
		do
		{
			playerIndex = playerIndex % (CM.numPlayers - 1) + 1;
		} while (!players[playerIndex].isAlive() && getNumPlayersOfType(Controller.HUMAN) > 0);
	}

	/**
	 * returns the player of the current turn
	 * 
	 * @return
	 */
	public Player getCurrentPlayer()
	{
		return players[playerIndex];
	}

	/**
	 * compares given fleet to available inventory at planet
	 * 
	 * @param num
	 * @return
	 */
	public boolean canSend(Player p, ShipGroup f)
	{
		if (p.getOrigin() == null) return false;
		return p.getOrigin().getShipInventory().contains(f);
	}

	/**
	 * Returns the player at the current index
	 * 
	 * @param index
	 * @return
	 */
	public Player getPlayer(int index)
	{
		return players[index];
	}
	
	public int getNumPlayersOfType(Controller c)
	{
		int num = 0;
		for (Player p : players)
		{
			if (p.getController() == c)
			{
				num++;
			}
		}
		
		return num;
	}
}
