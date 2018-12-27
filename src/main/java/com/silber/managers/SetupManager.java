package com.silber.managers;

public class SetupManager
{

	protected PlayerManager PM;
	protected PlanetManager PG;
	protected TurnManager TM;
	protected MusicManager MM;
	protected ConfigManager CM;

	public SetupManager()
	{
		CM = new ConfigManager();
	}

	public void setup()
	{
		PM = new PlayerManager(CM);
		PG = new PlanetManager(CM);
		TM = new TurnManager(CM);
		MM = new MusicManager(CM);
		
		PG.setup(PM, TM);
	}
	
	public PlayerManager getPlayerManager()
	{
		return PM;
	}
	
	public PlanetManager getPlanetManager()
	{
		return PG;
	}
	
	public TurnManager getTurnManager()
	{
		return TM;
	}
	
	public ConfigManager getConfigManager()
	{
		return CM;
	}

	public void startGame()
	{
		TM.setup(PM, PG);
		MM.setup();
	}

	public void populatePlayerList()
	{
		
	}
	
}
