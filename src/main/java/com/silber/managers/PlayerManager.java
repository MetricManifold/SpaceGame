package com.silber.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.silber.groups.ShipGroup;
import com.silber.players.Neutral;
import com.silber.players.Player;

/**
 * Responsible for managing player actions and updating the current player per turn
 * 
 * @author MR SMITH
 *
 */
public class PlayerManager
{
	private int playerIndex;
	private Player[] players;
	public final Player neutral;
	public int numPlayers;

	protected ConfigManager CM;

	public enum Controller
	{
		AI("AI"), HUMAN("Human"), NEUTRAL("Neutral"), HOSTILE("Hostile");

		String name;

		Controller(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	};

	public PlayerManager(ConfigManager CM)
	{
		this.CM = CM;
		neutral = new Neutral(0, CM.COLORS[0]);
		neutral.setName(Controller.NEUTRAL.toString());

		makePlayerList();
	}

	/**
	 * setup activity for this manager
	 */
	protected void makePlayerList()
	{
		numPlayers = CM.numHumanPlayers + 1;

		players = new Player[numPlayers];
		players[0] = neutral;
		playerIndex = 1;

		for (int i = 1; i < numPlayers; i++)
		{
			players[i] = new Player(i, CM.COLORS[i]);
			CM.planetStartCountMap.put(players[i], CM.planetStartCount);
		}
	}

	/**
	 * move player index to the next human player
	 */
	public void nextPlayer()
	{
		int numAlive = 0;
		Player pAlive = null;

		// check victory conditions
		for (Player p : players)
		{
			if (p.isAlive())
			{
				numAlive++;
				pAlive = p;
			}
		}

		if (numAlive == 1)
		{
			victory(pAlive);
		}

		do
		{
			playerIndex = playerIndex % numPlayers;
		} while (!players[playerIndex].isAlive() && getNumPlayersOfType(Controller.HUMAN) > 0);
	}

	public void victory(Player p)
	{

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

	public void setCurrentPlayer(int playerIndex)
	{
		if (playerIndex >= numPlayers)
		{
			this.playerIndex = numPlayers - 1;
		}
		else
		{
			this.playerIndex = playerIndex;
		}
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

	public Player[] getPlayersOfType(Controller c)
	{
		List<Player> list = new ArrayList<>(Arrays.asList(players));
		list.removeIf(p -> p.getController() != c);
		return list.toArray(new Player[] {});
	}

	public Player[] getPlayers()
	{
		return players;
	}
}
