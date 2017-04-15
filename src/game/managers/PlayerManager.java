package game.managers;

import game.objects.Destroyer;
import game.players.Player;

/**
 * Reponsible for managing player actions and updating the current player per turn
 * 
 * @author MR SMITH
 *
 */
public class PlayerManager
{
	private static final int NUM_PLAYERS = 2;

	private Player player;
	private int playerIndex = 0;
	private Player[] players = new Player[NUM_PLAYERS];

	public static Class<?> defaultShip = Destroyer.class;

	public PlayerManager()
	{
		for (int i = 0; i < NUM_PLAYERS; i++)
		{
			players[i] = new Player();
		}

		player = players[playerIndex];
	}

	public void nextPlayer()
	{
		playerIndex = (playerIndex + 1) % NUM_PLAYERS;
		player = players[playerIndex];
	}

	/**
	 * returns the player of the current turn
	 * 
	 * @return
	 */
	public Player getCurrentPlayer()
	{
		return player;
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
		else return num <= p.getOrigin().getShipInventory().getCount(defaultShip);
	}
}
