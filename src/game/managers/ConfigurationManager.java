package game.managers;

import game.objects.Destroyer;

public class ConfigurationManager
{
	public static int shipStartCount = 10,
			numPlayers = 3, playerMax = 14, 
			gridX = 30, gridY = 30;
	
	public static double planetDensity = 0.15;
	public static Class<?> defaultShip = Destroyer.class;
	
	public static final String[] COLORS = {
			"white", "blue", "red", "orange", "teal", "purple", 
			"pink", "gray", "yellow", "dark-blue", "green", 
			"light-green", "light-blue", "brown"};
	
}
