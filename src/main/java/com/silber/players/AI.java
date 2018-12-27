package game.players;

import game.managers.PlanetManager;
import game.managers.PlayerManager;
import game.managers.PlayerManager.Controller;
import game.managers.TurnManager;

public class AI extends Player
{

	public AI(int num, String color)
	{
		super(num, color, Controller.AI);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(PlanetManager pg, TurnManager tm, PlayerManager pm)
	{
		tm.nextTurn();
	}

}
