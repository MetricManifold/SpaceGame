package game.managers;

public class BuildManager
{
	int[] destroyer = new int[20];
	int[] frigate = new int[20];
	int[] fighter = new int[20];

	boolean[] ProdStatus = new boolean[20];
	boolean[] StreStatus = new boolean[20];

	TurnManager t = new TurnManager();
	int credits;
	String news;
	int[] playercredits = new int[2];

	public void addDestroyer(int planet)
	{
		this.destroyer[planet] += 1;
	}

	public void addFrigate(int planet)
	{
		this.frigate[planet] += 1;
	}

	public void addFighter(int planet)
	{
		this.fighter[planet] += 1;
	}

	public void MakeProd(int planet)
	{
		try
		{
			this.playercredits = this.t.GetPlayerCredits();
			int[] production = this.t.GetPlanetProd();

			if (this.ProdStatus[planet])
			{
				if (this.credits >= 12500)
				{
					production[planet] += 3;
					this.t.SetPlanetProd(production);
					this.ProdStatus[planet] = !this.ProdStatus[planet];

					if (!this.t.PlayerTurn)
					{
						this.playercredits[0] -= 12500;
					}
					else
					{
						this.playercredits[1] -= 12500;
					}
				}
				else
				{
					this.news = "You don't have enough money!";
				}
			}
			else
			{
				this.news = "This planet already has an advanced production facility!";
			}
			this.t.SetPlayerCredits(this.playercredits);
		}
		catch (Exception e1)
		{
			this.news = "Input invalid!";
		}
	}

	public void MakeDefense(int planet)
	{
		try
		{
			this.playercredits = this.t.GetPlayerCredits();
			double[] strength = this.t.GetPlanetStrength();

			if (this.StreStatus[planet])
			{
				if (this.credits >= 9500)
				{
					strength[planet] += 3.0D;
					this.t.SetPlanetStrength(strength);
					this.StreStatus[planet] = !this.StreStatus[planet];

					if (!this.t.PlayerTurn)
					{
						this.playercredits[0] -= 9500;
					}
					else
					{
						this.playercredits[1] -= 9500;
					}
				}
				else
				{
					this.news = "You don't have enough money!";
				}
			}
			else
			{
				this.news = "This planet already has an array of space defenses!";
			}
			this.t.SetPlayerCredits(this.playercredits);
		}
		catch (Exception e)
		{
			this.news = "Input invalid!";
		}
	}

	public void MakeDestroyer(int planet)
	{
		try
		{
			this.playercredits = this.t.GetPlayerCredits();

			if (this.credits >= 1000)
			{
				addDestroyer(planet);

				if (!this.t.PlayerTurn)
				{
					this.playercredits[0] -= 1000;
				}
				else
				{
					this.playercredits[1] -= 1000;
				}
			}
			else
			{
				this.news = "You don't have enough money!";
			}
			this.t.SetPlayerCredits(this.playercredits);
		}
		catch (Exception e)
		{
			this.news = "Input invalid!";
		}
	}

	public void MakeFrigate(int planet)
	{
		try
		{
			this.playercredits = this.t.GetPlayerCredits();

			if (this.credits >= 500)
			{
				addFrigate(planet);

				if (!this.t.PlayerTurn)
				{
					this.playercredits[0] -= 500;
				}
				else
				{
					this.playercredits[1] -= 500;
				}
			}
			else
			{
				this.news = "You don't have enough money!";
			}
			this.t.SetPlayerCredits(this.playercredits);
		}
		catch (Exception e)
		{
			this.news = "Input invalid!";
		}
	}

	public void MakeFighter(int planet)
	{
		try
		{
			this.playercredits = this.t.GetPlayerCredits();

			if (this.credits >= 100)
			{
				addFighter(planet);

				if (!this.t.PlayerTurn)
				{
					this.playercredits[0] -= 100;
				}
				else
				{
					this.playercredits[1] -= 100;
				}
			}
			else
			{
				this.news = "You don't have enough money!";
			}
			this.t.SetPlayerCredits(this.playercredits);
		}
		catch (Exception e)
		{
			this.news = "Input invalid!";
		}
	}
}

/*
 * Location: C:\Users\Zirconix\Desktop\Computer Science\Java Projects\Java
 * Game\bin\!\layout\BuildManager.class Java compiler version: 6 (50.0) JD-Core
 * Version: 0.7.1
 */