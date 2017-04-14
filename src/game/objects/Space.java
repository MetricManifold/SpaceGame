package game.objects;

import game.helpers.Displacement;

public class Space
{
	protected Displacement pos;

	public Space(int x, int y)
	{
		this(new Displacement(x, y));
	}

	public Space(Displacement pos)
	{
		this.pos = pos;
	}

	public Space(Space p)
	{
		this.pos = p.pos;
	}

	public double distanceTo(Space p)
	{
		return p.pos.subtract(pos).length();
	}

}
