package game.objects;

public class ShipInventory extends ShipGroup
{
	private Planet owner;

	public ShipInventory(Planet owner)
	{
		super();
		this.owner = owner;
	}

	public void add(int production)
	{
		add(Destroyer.class, production);
	}
	
	public Planet getOwner()
	{
		return owner;
	}
	
}
