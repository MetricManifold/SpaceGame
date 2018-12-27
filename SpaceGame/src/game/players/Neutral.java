package game.players;

import game.managers.PlanetManager;
import game.managers.PlayerManager;
import game.managers.PlayerManager.Controller;
import game.managers.TurnManager;

public class Neutral extends Player
{

	public Neutral(int num, String color)
	{
		super(num, color, Controller.NEUTRAL);
	}
	
	@Override
	public void update(PlanetManager pg, TurnManager tm, PlayerManager pm)
	{
		tm.nextTurn();
	}

}
