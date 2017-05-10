package game.helpers;

import game.ui.PlanetManagerUI;

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
		double angle = Math.atan2(y, x);
		float x = (float) (this.x + length * Math.cos(angle));
		float y = (float) (this.y + length * Math.sin(angle));
		
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

	public double getWorldX()
	{
		double t = PlanetManagerUI.getMargin() + (0.5 * PlanetManagerUI.getTileH()) + (PlanetManagerUI.getTileH() + PlanetManagerUI.getPadH()) * x;
		return t;
	}

	public double getWorldY()
	{
		double t = PlanetManagerUI.getMargin() + (0.5 * PlanetManagerUI.getTileV()) + (PlanetManagerUI.getTileV() + PlanetManagerUI.getPadV()) * y; 
		return t;
	}
}










