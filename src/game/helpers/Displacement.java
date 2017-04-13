package game.helpers;

public class Displacement
{
	public final int x, y;
	
	public Displacement(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Displacement add(Displacement d)
	{
		return new Displacement(this.x + d.x, this.y + d.y);
	}
	
	public Displacement subtract(Displacement d)
	{
		return new Displacement(this.x - d.x, this.y - d.y);
	}
	
	public double length()
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
}
