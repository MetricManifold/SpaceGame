package game.helpers;

import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class GfxHelper
{
	public static Path makeArrowHeadLinear(Line c, float t)
	{
		double scale = 30d;
		
		Point2D ori = evalLinear(c, t);
		Point2D tan = evalLinearDt(c, t).normalize().multiply(scale);
		Path arrow = makeArrow(ori, tan);

		return arrow;
	}

	public static Path makeArrowHeadCubic(CubicCurve c, float t)
	{
		double size = Math.max(c.getBoundsInLocal().getWidth(), c.getBoundsInLocal().getHeight());
		double scale = size / 4d;

		Point2D ori = evalCubic(c, t);
		Point2D tan = evalCubicDt(c, t).normalize().multiply(scale);
		Path arrow = makeArrow(ori, tan);

		return arrow;
	}
	
	private static Path makeArrow(Point2D ori, Point2D tan)
	{
		Path arrow = new Path();
		arrow.getElements().add(new MoveTo(ori.getX() + 0.2 * tan.getX() - 0.2 * tan.getY(), ori.getY() + 0.2 * tan.getY() + 0.2 * tan.getX()));
		arrow.getElements().add(new LineTo(ori.getX(), ori.getY()));
		arrow.getElements().add(new LineTo(ori.getX() + 0.2 * tan.getX() + 0.2 * tan.getY(), ori.getY() + 0.2 * tan.getY() - 0.2 * tan.getX()));
		
		return arrow;
	}

	/**
	 * Evaluate the cubic curve at a parameter 0 <= t <= 1, returns a Point2D
	 * 
	 * @param c
	 *            the CubicCurve
	 * @param t
	 *            param between 0 and 1
	 * @return a Point2D
	 */
	private static Point2D evalCubic(CubicCurve c, float t)
	{
		Point2D p = new Point2D(Math.pow(1 - t, 3) * c.getStartX() +
			3 * t * Math.pow(1 - t, 2) * c.getControlX1() +
			3 * (1 - t) * t * t * c.getControlX2() +
			Math.pow(t, 3) * c.getEndX(),
			Math.pow(1 - t, 3) * c.getStartY() +
				3 * t * Math.pow(1 - t, 2) * c.getControlY1() +
				3 * (1 - t) * t * t * c.getControlY2() +
				Math.pow(t, 3) * c.getEndY());
		return p;
	}

	/**
	 * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
	 * 
	 * @param c
	 *            the CubicCurve
	 * @param t
	 *            param between 0 and 1
	 * @return a Point2D
	 */
	private static Point2D evalCubicDt(CubicCurve c, float t)
	{
		Point2D p = new Point2D(-3 * Math.pow(1 - t, 2) * c.getStartX() +
			3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlX1() +
			3 * ((1 - t) * 2 * t - t * t) * c.getControlX2() +
			3 * Math.pow(t, 2) * c.getEndX(),
			-3 * Math.pow(1 - t, 2) * c.getStartY() +
				3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlY1() +
				3 * ((1 - t) * 2 * t - t * t) * c.getControlY2() +
				3 * Math.pow(t, 2) * c.getEndY());
		return p;
	}

	/**
	 * Evaluate the line at a parameter 0 <= t <= 1, returns Point2D
	 * 
	 * @param c
	 * @param t
	 * @return
	 */
	private static Point2D evalLinear(Line c, float t)
	{
		Point2D p = new Point2D((c.getEndX() - c.getStartX()) * t + c.getStartX(), (c.getEndY() - c.getStartY()) * t + c.getStartY());
		return p;
	}

	/**
	 * Evaluate the tangent of the line at a parameter 0 <= t <= 1, returns a Point2D
	 * 
	 * @param c
	 * @param t
	 * @return
	 */
	private static Point2D evalLinearDt(Line c, float t)
	{
		Point2D p = new Point2D((c.getEndY() - c.getStartY()) * t + c.getStartY(), ((c.getEndX() - c.getStartX()) * t + c.getStartX()));
		return p;
	}
}
