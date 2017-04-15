package game.objects;

import game.helpers.Displacement;

public class Fleet extends ShipGroup
{
	private Displacement path = null;
	private float speed = Integer.MAX_VALUE;

	public void update()
	{
		if (path != null)
		{
			if (path.subtract(speed).length() < speed)
			{
				path = null;
			}
			else
			{
				path.add(speed);
			}
		}
	}
	
	public void send(Displacement path)
	{
		this.path = path;

		for (Ship s : ships)
		{
			if (s.speed < speed) speed = s.speed;
		}
	}
	
	public void send(Planet origin, Planet dest)
	{
		send(dest.pos.subtract(origin.pos));
	}
	
}
