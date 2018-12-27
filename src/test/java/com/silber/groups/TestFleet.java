package test.groups;

import game.groups.*;
import game.managers.ConfigManager.ShipType;
import game.players.Player;
import junit.framework.TestCase;

public class TestFleet extends TestCase
{
	protected Fleet attacker;
	protected ShipInventory defender;
	protected Player player;
	
	protected void setUp()
	{
		player = new Player(0, "attacking player");
		attacker = new Fleet(player);
		defender = new ShipInventory();
	}
	
	public void testAttack() throws Exception
	{
		attacker.add(ShipType.DESTROYER, 10000);
		defender.add(ShipType.DESTROYER, 10000);
		attacker.attack(defender, 1.12);
		
		boolean success = true;
		
		if (attacker.getCount() != 0) success = false;
		if (defender.getCount() == 0) success = false;
		
		System.out.printf("attacker: %d, defender: %d", attacker.getCount(), defender.getCount());
		
		assertTrue(success);
	}

}
