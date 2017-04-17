package game.managers;

import game.players.Player;

/**
 * Reponsible for managing player actions and updating the current player per turn
 * 
 * @author MR SMITH
 *
 */
public class PlayerManager
{
	private int playerIndex = 1;
	private Player[] players;
	public final Player neutral;

	public PlayerManager()
	{
		int numPlayers = ConfigurationManager.numPlayers;

		players = new Player[numPlayers];
		neutral = new Player(0, ConfigurationManager.COLORS[0]);

		for (int i = 0; i < numPlayers; i++)
		{
			players[i] = new Player(i, ConfigurationManager.COLORS[i]);
		}
	}

	public void nextPlayer()
	{
		do
		{
			playerIndex = playerIndex % (ConfigurationManager.numPlayers - 1) + 1;
		} while (!players[playerIndex].isAlive());
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
	 * returns whether or not the origin planet
	 * 
	 * @param num
	 * @return
	 */
	public boolean canSend(Player p, int num)
	{
		if (p.getOrigin() == null) return false;
		else return num <= p.getOrigin().getShipInventory().getCount(ConfigurationManager.defaultShip);
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
}
