package test.groups;

import game.entities.Destroyer;
import game.groups.*;
import game.managers.PlayerManager.Controller;
import game.players.Player;
import junit.framework.TestCase;

public class TestFleet extends TestCase
{
	protected Fleet attacker;
	protected ShipInventory defender;
	protected Player player;
	
	protected void setUp()
	{
		player = new Player(0, "attacking player", Controller.AI);
		attacker = new Fleet(player);
		defender = new ShipInventory();
	}
	
	public void testAttack() throws Exception
	{
		attacker.add(Destroyer.class, 10000);
		defender.add(Destroyer.class, 10000);
		attacker.attack(defender, 1.12);
		
		boolean success = true;
		
		if (attacker.getCount() != 0) success = false;
		if (defender.getCount() == 0) success = false;
		
		System.out.printf("attacker: %d, defender: %d", attacker.getCount(), defender.getCount());
		
		assertTrue(success);
	}

}
