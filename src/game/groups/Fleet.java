package game.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.entities.Ship;
import game.helpers.AccumulateInteger;
import game.helpers.Displacement;
import game.helpers.PointerDoubleMatrix;
import game.helpers.Pointer;
import game.managers.ConfigurationManager;
import game.managers.PlanetManager;
import game.players.Player;
import game.tiles.Planet;

public class Fleet extends ShipGroup
{
	private Displacement path = null;
	private Planet destination = null;
	private float speed = Integer.MAX_VALUE;
	private Player owner;

	public Fleet(Player owner)
	{
		this.owner = owner;
	}

	public Fleet(ShipGroup group, Player owner)
	{
		super(group);
		this.owner = owner;
	}

	public Fleet(Class<? extends Ship> type, int num, Player owner)
	{
		super(type, num);
		this.owner = owner;
	}

	public Fleet(Ship ship, Player owner)
	{
		super(ship);
		this.owner = owner;
	}

	public Fleet(Collection<Ship> ships, Player owner)
	{
		super(ships);
		this.owner = owner;
	}

	/**
	 * update the status of this fleet, including position if moving
	 * 
	 * @param pm
	 * @throws Exception
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void update(PlanetManager pm) throws Exception
	{
		if (path != null)
		{
			if (path.subtract(speed).length() < speed)
			{
				if (owner != destination.getOwner())
				{
					attack(destination);

					if (getCount() > 0)
					{
						for (Ship f : getAll())
						{
							f.heal();
						}

						pm.setPlanetOwner(owner, destination);
						destination.addShips(this);
						removeAll();
					}
				}
				else
				{
					destination.addShips(this);
				}

				path = null;
				destination = null;
			}
			else
			{
				path = path.subtract(speed);
			}
		}
	}

	/**
	 * direct this fleet to move to a destination from the origin
	 * 
	 * @param origin
	 * @param dest
	 */
	public void send(Planet origin, Planet dest)
	{
		// set the path and destination
		this.path = dest.getPosition().subtract(origin.getPosition());
		this.destination = dest;

		// log message
		System.out.format("send ship on path x%.1f,y%.1f\n", path.getX(), path.getY());

		for (List<Ship> l : ships.values())
		{
			for (Ship s : l)
			{
				if (s.speed < speed) speed = s.speed;
			}
		}
	}

	/**
	 * makes this fleet attack another, modifying both of them. Loop iterates for each type of ship
	 * 
	 * @param defender
	 * @param defenderBonus
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void attack(ShipGroup defender, double defenderBonus) throws Exception
	{
		Set<Class<? extends Ship>> allTypes = new HashSet<>(ships.keySet());
		allTypes.addAll(defender.ships.keySet());
		List<Class<? extends Ship>> allTypesList = new ArrayList<>(allTypes);
		int tn = allTypesList.size();
		
		int iniHpA = getTotalHealth();
		int iniHpB = defender.getTotalHealth();
		
		int dmgA = -getTotalDamage();
		int dmgB = (int) (-defender.getTotalDamage() * defenderBonus);
		int avgDmgA = dmgA / defender.ships.keySet().size();
		int avgDmgB =  dmgB / ships.keySet().size();
		
		Map<Class<? extends Ship>, AccumulateInteger> maxHpA = countMaxHealth();
		Map<Class<? extends Ship>, AccumulateInteger> maxHpB = defender.countMaxHealth();

		// damage and strength map
		PointerDoubleMatrix d_p = new PointerDoubleMatrix(tn, tn, new Pointer<Double>(0d));
		PointerDoubleMatrix m_p = new PointerDoubleMatrix(tn, tn, new Pointer<Double>(0d));
		
		PointerDoubleMatrix h_a_max = new PointerDoubleMatrix(tn, tn, new Pointer<Double>(0d));
		PointerDoubleMatrix h_b_max = new PointerDoubleMatrix(tn, tn, new Pointer<Double>(0d));

		// matrix for damages
		PointerDoubleMatrix d_a = new PointerDoubleMatrix(1, tn, new Pointer<Double>(0d));
		PointerDoubleMatrix d_b = new PointerDoubleMatrix(1, tn, new Pointer<Double>(0d));

		// matrix for healths		
		PointerDoubleMatrix h_a = new PointerDoubleMatrix(1, tn, new Pointer<Double>(0d));
		PointerDoubleMatrix h_b = new PointerDoubleMatrix(1, tn, new Pointer<Double>(0d));

		for (int i = 0; i < tn; i++)
		{
			Class<? extends Ship> t = allTypesList.get(i);
			Ship s = t.newInstance();

			h_a.set(0, i, getCount(t) * s.health);
			h_b.set(0, i, defender.getCount(t) * s.health);
			d_p.set(i, i, -s.getStrength()._2);
			
			h_a_max.set(i, i, 1 / maxHpA.get(t).get());
			h_b_max.set(i, i, 1 / maxHpB.get(t).get());

			if (defender.ships.containsKey(t))
			{
				d_a.set(0, i, avgDmgA);
			}
			else
			{
				d_a.set(0, i, 0);
			}

			if (ships.containsKey(t))
			{
				d_b.set(0, i, avgDmgB);
			}
			else
			{
				d_b.set(0, i, 0);
			}

			for (int j = 0; j < tn; j++)
			{
				int val = (s.getStrength()._1 == s.getStrength()._1) ? 1 : 0;
				m_p.set(i, j, val);
			}
		}
		
		PointerDoubleMatrix h_a0 = new PointerDoubleMatrix(h_a);
		PointerDoubleMatrix h_b0 = new PointerDoubleMatrix(h_b);

		PointerDoubleMatrix check = new PointerDoubleMatrix(1, tn, new Pointer<Double>(0d));
		while (!h_a.equals(check) && !h_b.equals(check))
		{

			int newHpA = 0, newHpB = 0;
			for (int i = 0; i < tn; i++)
			{
				if (h_a.get(0, i).v < 0) h_a.set(0, i, 0);
				if (h_b.get(0, i).v < 0) h_b.set(0, i, 0);

				newHpA += h_a.get(0, i).v;
				newHpB += h_b.get(0, i).v;
			}

			h_a = h_a.add(d_a.mul(newHpA / iniHpA).add(d_p.mul(m_p)));
			h_b = h_b.add(d_b.mul(newHpB / iniHpB).add(d_p.mul(m_p)));
		}
		
		for (int i = 0; i < tn; i++)
		{
			double curHp = h_a.get(0, i).v;
			
		}
		

	}

	public void attack(Planet p) throws Exception
	{
		attack(p.getShipInventory(), ConfigurationManager.planetDefenderBonus);
	}

}
