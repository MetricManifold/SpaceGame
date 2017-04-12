package layout;

import java.util.Random;

public class TurnManager
{
	static GridManager g = UIsetup.g;
	static UIsetup u = new UIsetup();
	static BuildManager b = UIsetup.b;

	public boolean FirstTurn = true;
	boolean PlayerTurn = false;
	Random random = new Random();
	int TravelNum = 0;

	boolean P1win = false;
	boolean P2win = false;
	boolean P1lose = false;
	boolean P2lose = false;

	private int[] playercredits = new int[2];
	boolean SendStatus = true;

	int[] Travel = new int[100000];
	int[] SourceShips = new int[100000];
	int[] Destination = new int[100000];
	String[] SentFrom = new String[100000];

	int[] Destroyers = new int[100000];
	int[] Frigates = new int[100000];
	int[] Fighters = new int[100000];

	private int[] planetships = new int[20];
	private int[] planetproduction = new int[20];
	private int[] planetcredits = new int[20];
	private double[] planetstrength = new double[20];
	public String[] planetname = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t" };
	private String[] planetowner = new String[20];

	String news;

	TurnManager()
	{
		for (int x = 0; x < 10000; x++)
		{
			this.Travel[x] = 0;
		}
	}

	public void RandomValues()
	{
		String[] owner = new String[20];
		int[] ships = new int[20];
		int[] production = new int[20];
		double[] strength = new double[20];
		int[] credits = new int[20];

		for (int x = 1; x < 19; x++)
		{
			owner[x] = "neutral";

			ships[x] = 10;

			production[x] = (this.random.nextInt(11) + 5);

			double StrengthNum = this.random.nextInt(601) + 300;
			strength[x] = Round(StrengthNum / 100.0D, 2);

			credits[x] = (this.random.nextInt(751) + 250);
		}

		owner[0] = "Player 1";
		ships[0] = 10;
		production[0] = 10;
		strength[0] = 4.5D;
		credits[0] = 400;

		owner[19] = "Player 2";
		ships[19] = 10;
		production[19] = 10;
		strength[19] = 4.5D;
		credits[19] = 400;

		SetPlanetOwner(owner);
		SetPlanetShips(ships);
		SetPlanetProd(production);
		SetPlanetStrength(strength);
		SetPlanetCredits(credits);

		this.FirstTurn = false;
	}

	public void NextTurn()
	{
		int[] shipnum = GetPlanetShips();
		int[] prodnum = GetPlanetProd();
		int[] adjustships = new int[20];
		String[] planetowner = GetPlanetOwner();
		int[] credits = GetPlayerCredits();
		int[] planetcred = GetPlanetCredits();

		for (int x = 0; x < 20; x++)
		{
			if (!planetowner[x].equals("neutral"))
			{
				shipnum[x] += prodnum[x];

			}
			else
			{
				shipnum[x] += 1;
			}
		}
		SetPlanetShips(adjustships);

		for (int x = 0; x < 20; x++)
		{
			if (planetowner[x].equals("Player 1"))
			{
				credits[0] += planetcred[x];
			}
			else if (planetowner[x].equals("Player 2"))
			{
				credits[1] += planetcred[x];
			}
		}

		SetPlayerCredits(credits);
	}

	public void ShipBattle(int x)
	{
		int[] planetships = GetPlanetShips();
		double[] planetstrength = GetPlanetStrength();
		String[] planetowner = GetPlanetOwner();
		String[] name = GetPlanetName();

		if (this.SentFrom[x].equals(planetowner[this.Destination[x]]))
		{
			planetships[this.Destination[x]] += this.SourceShips[x];
			this.news = ("Planet " + name[this.Destination[x]] + " has recieved " + this.SourceShips[x] + " reinforcements!");

		}
		else
		{
			int attack = this.SourceShips[x] + this.Fighters[x] * 2;
			int attackdefense = (int) ((planetships[this.Destination[x]] - this.Frigates[x] * 10)
					* (1.0D + planetstrength[this.Destination[x]] / 20.0D * Math.pow(0.9D, this.Destroyers[x])));
			int shipdefense = (int) (planetstrength[this.Destination[x]]
					* (b.destroyer[this.Destination[x]] * 0.08D + b.frigate[this.Destination[x]] * 0.04D + b.fighter[this.Destination[x]] * 0.01D));

			planetships[this.Destination[x]] = (attackdefense + shipdefense - attack);

			if (planetships[this.Destination[x]] < 0)
			{
				planetships[this.Destination[x]] = Math.abs(planetships[this.Destination[x]]);
				planetowner[this.Destination[x]] = this.SentFrom[x];
				SetPlanetOwner(planetowner);

				this.news = ("Planet " + name[this.Destination[x]] + " has been taken by " + this.SentFrom[x] + "!");
			}
			else
			{
				this.news = ("Planet " + name[this.Destination[x]] + " has withstood an attack from " + this.SentFrom[x] + "!");
			}

			SetPlanetShips(planetships);
		}
	}

	public void Win()
	{
		int P1count = 0;
		int P2count = 0;

		for (int x = 0; x < 20; x++)
		{
			if (this.planetowner[x] == "Player 1")
			{
				P1count++;
			}
			else if (this.planetowner[x] == "Player 2")
			{
				P2count++;
			}
		}
		if (P1count >= 20)
		{
			this.P1win = true;
		}
		else if (P2count >= 20)
		{
			this.P2win = true;
		}
		if (P1count <= 0)
			this.P1lose = true;
		if (P2count <= 0)
		{
			this.P2lose = true;
		}
	}

	public static double Round(double Rval, int Rpl)
	{
		double p = Math.pow(10.0D, Rpl);
		Rval *= p;
		double tmp = Math.round(Rval);
		return tmp / p;
	}

	public int[] GetPlayerCredits()
	{
		return this.playercredits;
	}

	public String[] GetPlanetOwner()
	{
		return this.planetowner;
	}

	public String[] GetPlanetName()
	{
		return this.planetname;
	}

	public int[] GetPlanetShips()
	{
		return this.planetships;
	}

	public int[] GetPlanetProd()
	{
		return this.planetproduction;
	}

	public double[] GetPlanetStrength()
	{
		return this.planetstrength;
	}

	public int[] GetPlanetCredits()
	{
		return this.planetcredits;
	}

	public boolean GetFirstTurn()
	{
		return this.FirstTurn;
	}

	public void SetPlayerCredits(int[] credits)
	{
		this.playercredits = credits;
	}

	public void SetPlanetOwner(String[] owner)
	{
		this.planetowner = owner;
	}

	public void SetPlanetName(String[] name)
	{
		this.planetname = name;
	}

	public void SetPlanetShips(int[] ships)
	{
		this.planetships = ships;
	}

	public void SetPlanetProd(int[] production)
	{
		this.planetproduction = production;
	}

	public void SetPlanetStrength(double[] strength)
	{
		this.planetstrength = strength;
	}

	public void SetPlanetCredits(int[] credits)
	{
		this.planetcredits = credits;
	}

	public void SetFirstTurn(boolean turn)
	{
		this.FirstTurn = turn;
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\TurnManager.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */