package game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import game.entities.Destroyer;
import game.entities.Ship;

public class ConfigurationManager
{
	public static int shipStartCount = 10,
		numPlayers = 2, playerMax = 14,
		gridX = 10, gridY = 25;

	public static double planetDensity = 0.15,
		neutralProdModifier = 0.30,
		planetDefenderBonus = 1.08;
	public static Class<? extends Ship> defaultShip = Destroyer.class;

	public static final String[] COLORS = {
		"white", "red", "blue", "orange", "teal", "purple",
		"pink", "gray", "yellow", "darkblue", "green",
		"lightgreen", "lightblue", "brown" };

	public static class PlanetName
	{
		public static List<String> pickedNames = new ArrayList<String>();
		public static final String[] NAMES_PRE = { "Bara-", "Qual'", "Ban'Da-", "Ki'", "Muan'",
				"El ", "Governorate of ", "Em ", "Fex-", "Ruins of "},
			NAMES = { "Tor", "Zora", "Selenium", "Utrazym", "Tooh", "Hok", "Ytvanix", "Xerxes",
				"Rossya", "Beluvky", "Keztelim", "Ferenz", "Zork", "Zelenium", "Hercules",
				"Uttica", "Calaman", "Kataman", "Waldin", "Soris", "Xirix", "Ethnor", "Lesnos",
				"Irvel", "Ulnabazimda", "Goron", "Gez" },
			NAMES_SUF = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "XI", "Star", "Prime",
				"Inferior", "Minor", "Superior", "Capitol", "Alliance", "Prison", "Colony",
				"Resort"};
		public static final double NAMES_PRE_PROB = 0.15, NAMES_SUF_PROB = 0.5;

		public static int index = 0;

		public static String getName()
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
	}

}
