package game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import game.entities.Bomber;
import game.entities.Destroyer;
import game.entities.Fighter;
import game.entities.Ship;

public class ConfigManager
{
	public interface Type<T>
	{
		@Override
		public String toString();

		public T getInstance();
	}

	public enum ShipType implements Type<Ship>
	{
		GENERIC(), DESTROYER(Destroyer.class), FIGHTER(Fighter.class), BOMBER(Bomber.class);

		public Class<? extends Ship> type;
		public String name;
		private Ship instance = null;

		ShipType()
		{
			type = null;
			name = null;
		}

		ShipType(Class<? extends Ship> type)
		{
			this.type = type;

			if (type == Destroyer.class)
				name = "Destroyer";
			else if (type == Fighter.class)
				name = "Fighter";
			else if (type == Bomber.class)
				name = "Bomber";
		}

		@Override
		public String toString()
		{
			return name;
		}

		public Ship getInstance()
		{
			if (instance == null)
			{
				try
				{
					if (type == null)
					{
						instance = null;
					}
					else
					{
						instance = type.newInstance();
					}
				}
				catch (InstantiationException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
			return instance;
		}

	};

	public int shipStartCount = 10,
		initialProduction = 10, 
		maxProduction = 30, 
		minProduction = 5,
		cmaxProduction = 9999, 
		cmaxShipStartCount = 9999999,
		numHumanPlayers = 1, 
		maxHumanPlayers = 14,
		gridX = 15, gridY = 15,
		minGridX = 4, minGridY = 4,
		maxGridX = 30, maxGridY = 30,
		defaultGridX = 20, defaultGridY = 20;

	public double planetDensity = 0.15,
		neutralProdModifier = 0.70,
		cminNeutralProdModifier = 0.00, cmaxNeutralProdModifier = 2.00,
		planetDefenderBonus = 1.10,
		cminPlanetDefenderBonus = 0.00, cmaxPlanetDefenderBonus = 2.00;

	public boolean neutralShipsVisible = false;
	public ShipType defaultShip = ShipType.DESTROYER;

	public final String[] COLORS = {
		"white", "red", "blue", "orange", "teal", "purple",
		"pink", "gray", "yellow", "darkblue", "green",
		"lightgreen", "lightblue", "brown" };

	public final String[] NAMES_PRE = { "Bara-", "Qual'", "Ban'Da-", "Ki'", "Muan'",
		"El ", "Governorate of ", "Em ", "Fex-", "Ruins of " },
		NAMES = { "Tor", "Zora", "Selenium", "Utrazym", "Tooh", "Hok", "Ytvanix", "Xerxes",
			"Rossya", "Beluvky", "Keztelim", "Ferenz", "Zork", "Zelenium", "Hercules",
			"Uttica", "Calaman", "Kataman", "Waldin", "Soris", "Xirix", "Ethnor", "Lesnos",
			"Irvel", "Ulnabazimda", "Goron", "Gez" },
		NAMES_SUF = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "XI", "Star", "Prime",
			"Inferior", "Minor", "Superior", "Capitol", "Alliance", "Prison", "Colony",
			"Resort" };
	public final double NAMES_PRE_PROB = 0.15, NAMES_SUF_PROB = 0.5;

	public List<String> pickedNames = new ArrayList<String>();

	public String getPlanetName()
	{
		StringBuilder nameBuilder = new StringBuilder("");
		String name = "";

		int np = NAMES_PRE.length, n = NAMES.length, ns = NAMES_SUF.length;

		do
		{
			nameBuilder = new StringBuilder("");

			int pickName = ThreadLocalRandom.current().nextInt(n);
			boolean doPreName = ThreadLocalRandom.current().nextDouble() < NAMES_PRE_PROB;
			boolean doSufName = ThreadLocalRandom.current().nextDouble() < NAMES_SUF_PROB;

			if (doPreName)
			{
				int pickPreName = ThreadLocalRandom.current().nextInt(np);
				nameBuilder.append(NAMES_PRE[pickPreName]);

			}

			nameBuilder.append(NAMES[pickName]);

			if (doSufName)
			{
				int pickSufName = ThreadLocalRandom.current().nextInt(ns);
				nameBuilder.append(" ");
				nameBuilder.append(NAMES_SUF[pickSufName]);
			}

			name = nameBuilder.toString();
		} while (pickedNames.contains(name));

		pickedNames.add(name);
		return name;
	}
	
	public void clearNames()
	{
		pickedNames.clear();
	}

}
