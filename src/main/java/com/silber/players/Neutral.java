package com.silber.players;

import com.silber.managers.PlanetManager;
import com.silber.managers.PlayerManager;
import com.silber.managers.PlayerManager.Controller;
import com.silber.managers.TurnManager;

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
