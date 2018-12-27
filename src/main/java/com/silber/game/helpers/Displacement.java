package game.helpers;

import game.ui.PlanetManagerUI;

public class Displacement
{
	private double x, y;

	public Displacement(double x, double y)
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
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public Displacement add(double length)
	{
		double angle = Math.atan2(y, x);
		double x = this.x + length * Math.cos(angle);
		double y = this.y + length * Math.sin(angle);
		
		return new Displacement(x, y);
	}
	
	public Displacement subtract(double length)
	{
		return add(-length);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getWorldX(PlanetManagerUI pg)
	{
		double t = pg.getMargin() + (0.5 * pg.getTileH()) + (pg.getTileH() + pg.getPadH()) * x;
		return t;
	}

	public double getWorldY(PlanetManagerUI pg)
	{
		double t = pg.getMargin() + (0.5 * pg.getTileV()) + (pg.getTileV() + pg.getPadV()) * y; 
		return t;
	}
}










