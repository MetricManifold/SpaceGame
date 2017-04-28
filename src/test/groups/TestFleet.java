package test.groups;

import game.entities.Destroyer;
import game.groups.*;
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
		attacker.add(Destroyer.class, 100);
		defender.add(Destroyer.class, 100);
		
		attacker.attack(defender, 1.05);
	}

}
