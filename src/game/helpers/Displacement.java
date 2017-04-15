package game.helpers;

public class Displacement
{
	private float x, y;

	public Displacement(float x, float y)
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

	public float length()
	{
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public Displacement add(float length)
	{
		double angle = Math.toDegrees(Math.atan2(y, x));

		if (angle < 0)
		{
			angle += 360;
		}

		x += length * Math.cos(angle);
		y -= length * Math.sin(angle);
		
		return new Displacement(x, y);
	}
	
	public Displacement subtract(float length)
	{
		return add(-length);
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
}