package game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import game.objects.Destroyer;

public class ConfigurationManager
{
	public static int shipStartCount = 10,
			numPlayers = 14, playerMax = 14,
			gridX = 30, gridY = 30;

	public static double planetDensity = 0.15;
	public static Class<?> defaultShip = Destroyer.class;

	public static final String[] COLORS = {
			"white", "red", "blue", "orange", "teal", "purple",
			"pink", "gray", "yellow", "darkblue", "green",
			"lightgreen", "lightblue", "brown" };


	public static class PlanetName
	{
		public static List<String> pickedNames = new ArrayList<String>();
		public static final String[] NAMES_PRE = { "Bara-", "Qual'", "BanDa-", "Ki'", "Muan'" },
				NAMES = { "Tor", "Zora", "Selenium", "Utrazym", "Tooh", "Hok", "Ytvanix", "Xerxes" },
				NAMES_SUF = { "I", "IX", "III", "Star", "Prime", "Inferior", "Minor", "Superior", "Capitol" };
		public static final double NAMES_PRE_PROB = 0.3, NAMES_SUF_PROB = 0.5;
		
		public static int index = 0;

		public static String getName()
		{
			StringBuilder nameBuilder = new StringBuilder("");
			String name = "";
			
			int np = NAMES_PRE.length, n = NAMES.length, ns = NAMES_SUF.length;
			
			while (pickedNames.contains(name))
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
			}
			
			pickedNames.add(name);
			return name;
		}
	}

}
