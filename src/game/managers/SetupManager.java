package game.managers;

public class SetupManager
{

	protected PlayerManager PM;
	protected PlanetManager PG;
	protected TurnManager TM;
	protected ConfigManager CM;

	public SetupManager()
	{
		CM = new ConfigManager();

		PM = new PlayerManager(CM);
		PG = new PlanetManager(CM);
		TM = new TurnManager(CM);
	}

	public void setup()
	{
		PG.setup(PM, TM);
		TM.setup(PM, PG);
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

}
