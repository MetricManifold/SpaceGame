package game.tiles;

import game.helpers.Displacement;
import javafx.scene.control.Tooltip;

public class Space
{
	protected Displacement pos;
	protected final Tooltip tooltip;

	public Space(int x, int y)
	{
		this(new Displacement(x, y));
	}

	public Space(Displacement pos)
	{
		this.pos = pos;
		this.tooltip = new Tooltip();
	}

	public Space(Space p)
	{
		this.pos = p.pos;
		this.tooltip = p.tooltip;
	}

	/**
	 * Compute the distance to another space tile
	 * @param p
	 * @return
	 */
	public double distanceTo(Space p)
	{
		return p.pos.subtract(pos).length();
	}
	
	/**
	 * Return the tooltip for this space tile
	 * @return
	 */
	public Tooltip getTooltip()
	{
		return tooltip;
	}
	
	public Displacement getPosition()
	{
		return pos;
	}
}
