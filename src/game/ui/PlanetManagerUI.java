package game.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.managers.*;
import game.groups.Fleet;
import game.helpers.Displacement;
import game.helpers.GfxHelper;
import game.players.Player;
import game.tiles.Planet;
import game.tiles.Space;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PlanetManagerUI extends PlanetManager
{
	// static values for ui
	protected static final int PADH = 5, PADV = 5,
		TILEH = 25, TILEV = 25,
		NUM_P_BG = 10, NUM_B_BG = 4,
		MARGIN = 5, TOPMARGIN = 2;

	protected static double MINI_SCALE = 0.6;
	protected double zoom;

	// planets and corresponding buttons
	protected Map<Space, Label> tiles;
	protected Map<Fleet, Path> paths;

	protected Pane pane, bgPane;
	protected GridPane miniPane;
	protected TilePane tilePane;
	protected TurnManagerUI TMui;

	public PlanetManagerUI(ConfigManager CM)
	{
		super(CM);
		makeUI();
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeUI()
	{

		VBox.setMargin(tilePane, new Insets(TOPMARGIN, 0, 0, 0));
		pane.getChildren().addAll(bgPane, tilePane);

		tilePane.setHgap(getPadH());
		tilePane.setVgap(getPadV());

		tilePane.setPrefTileWidth(getTileH());
		tilePane.setPrefTileHeight(getTileV());

		tilePane.setPadding(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));
		tilePane.setAlignment(Pos.BASELINE_CENTER);

		String bgSelect = String.format("planet-grid%d", ThreadLocalRandom.current().nextInt(NUM_B_BG) + 1);
		bgPane.getStyleClass().addAll("planet-grid", bgSelect);
	}

	/**
	 * Sets the mouse event associated with the tilepane to find all the grid locations
	 */
	@Override
	public void setup(PlayerManager pm, TurnManager tm)
	{
		TMui = (TurnManagerUI) tm;
		super.setup(pm, tm);

		tilePane.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				double sx = (event.getX() - MARGIN) / (getTileH() + getPadH()); // 	normalized selected x
				double sy = (event.getY() - MARGIN) / (getTileV() + getPadV()); // 	normalized selected y
				int ix = (int) sx; //		integer distance x
				int iy = (int) sy; //		integer distance y
				double dx = sx - ix; //		fractional distance x
				double dy = sy - iy; // 	fractional distance y

				// check if selection is before padding
				if (dx < (getTileH() + getPadH()) / getPadH() && dy < (getTileV() + getPadV()) / getPadV())
				{
					Planet p = planets.get(hashLocation(ix, iy));
					handleClickPlanet(p);
				}
			}
		});
	}

	@Override
	public void reset()
	{
		super.reset();
		updateMiniPane();
	}

	@Override
	protected void initialize()
	{
		super.initialize();

		if (tilePane == null)
		{
			bgPane = new Pane();
			pane = new Pane();
			tilePane = new TilePane(Orientation.HORIZONTAL);
		}
		else
		{
			tilePane.getChildren().clear();
		}

		zoom = 1.00;
		paths = new HashMap<Fleet, Path>();
		tiles = new HashMap<Space, Label>();

		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);
		tilePane.setMaxSize(getSizeX(), getSizeY());
		bgPane.setPrefSize(getSizeX(), getSizeY());
		pane.setMaxWidth(getSizeX());
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	@Override
	public void spawnPlanets()
	{
		super.spawnPlanets();
		tilePane.getChildren().clear();

		for (int i = 0; i < len; i++)
		{
			Label l = new Label();
			int hash = hashLocation(i % x, i / x);

			l.setMinSize(getTileH(), getTileV());
			l.setMaxSize(getTileH(), getTileV());
			tilePane.getChildren().add(l);

			Planet p = planets.get(hash);

			// add a circle here to check the positioning given by the constructor

			if (p != null)
			{
				String bgSelect = String.format("planet-button%d", ThreadLocalRandom.current().nextInt(NUM_P_BG) + 1);
				l.getStyleClass().addAll("space-button", bgSelect);

				tiles.put(p, l);
				setEventPlanetHover(p);
			}
			else
			{
				l.getStyleClass().add("space-button");
				tiles.put(spaces.get(hash), l);
			}
		}
	}

	/**
	 * handles the ui change when a planet is clicked
	 * 
	 * @param p
	 */
	public void handleClickPlanet(Planet p)
	{
		Planet o = PM.getCurrentPlayer().getOrigin();
		Planet d = PM.getCurrentPlayer().getDestination();
		PM.getCurrentPlayer().clickTile(p);

		if (p != null)
		{
			if (o == null)
			{
				if (p.getOwner() == PM.getCurrentPlayer())
				{
					tiles.get(p).getStyleClass().add("space-button-origin");
				}
			}
			else if (d == null)
			{
				if (p != o)
				{
					tiles.get(p).getStyleClass().add("space-button-destination");
					TMui.enableSend(true);
				}
			}
			else
			{
				clearSelection(o, d);
				TMui.enableSend(false);
			}
		}
		else
		{
			clearSelection(o, d);
			TMui.enableSend(false);
		}

	}

	/**
	 * clears the player's current selection from the grid
	 * 
	 * @param origin
	 * @param destination
	 */
	public void clearSelection(Planet origin, Planet destination)
	{
		if (origin != null)
		{
			tiles.get(origin).getStyleClass().remove("space-button-origin");
		}
		if (destination != null)
		{
			tiles.get(destination).getStyleClass().remove("space-button-destination");
		}
	}

	/**
	 * transfer ownership of a planet to a given player
	 * 
	 * @param player
	 * @param p
	 */
	@Override
	public void setPlanetOwner(Player player, Planet p)
	{
		// if owner is not null 
		if (p.getOwner() != null)
		{
			tiles.get(p).getStyleClass().remove(p.getOwner().getColor());
		}

		super.setPlanetOwner(player, p);
		tiles.get(p).getStyleClass().add(player.getColor());
	}

	/**
	 * add the tooltip for the given planet
	 * 
	 * @param p
	 */
	public void setEventPlanetHover(Planet p)
	{
		Label l = tiles.get(p);

		l.setOnMouseEntered(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Point2D pnt = l.localToScreen(l.getLayoutBounds().getMaxX(), l.getLayoutBounds().getMinY());
				p.getTooltip().show(l, pnt.getX() + 10, pnt.getY());
				showFleetPaths(p);
			}
		});

		l.setOnMouseExited(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				p.getTooltip().hide();
				hideFleetPaths(p);
			}
		});
	}

	/**
	 * shows lines for active fleets at the given planet
	 * 
	 * @param f
	 * @param o
	 * @param d
	 */
	public void showFleetPaths(Planet p)
	{
		for (Fleet f : TM.getFleets())
		{
			if (f.getDestination() == p && !paths.containsKey(f))
			{
				Displacement d = p.getPosition();
				Displacement e = f.getDisplacement();
				Line l = new Line(d.getWorldX(this), d.getWorldY(this), d.subtract(e).getWorldX(this), d.subtract(e).getWorldY(this));
				Path a = GfxHelper.makeArrowHeadLinear(l, 0);

				a.getElements().add(new MoveTo(l.getStartX(), l.getStartY()));
				a.getElements().add(new LineTo(l.getEndX(), l.getEndY()));
				a.setStrokeWidth(2);
				a.setStroke(Color.WHITE);
				a.setSmooth(true);

				paths.put(f, a);
				pane.getChildren().add(a);
			}
		}
	}

	/**
	 * hide all active fleets for the given planet
	 * 
	 * @param p
	 */
	public void hideFleetPaths(Planet p)
	{
		for (Fleet f : TM.getFleets())
		{
			if (f.getDestination() == p)
			{
				pane.getChildren().remove(paths.get(f));
				paths.remove(f);
			}
		}
	}

	public void setZoom(double newZoom)
	{
		zoom = newZoom;
	}

	public Pane getMiniPane()
	{
		if (miniPane == null)
		{
			miniPane = new GridPane();
			miniPane.getStyleClass().add("mini-grid");
			updateMiniPane();

		}

		return miniPane;
	}

	protected void updateMiniPane()
	{
		if (miniPane == null)
		{
			getMiniPane();
		}

		miniPane.getChildren().clear();

		double pad = MARGIN;
		miniPane.setMaxSize(getSizeX() * MINI_SCALE, getSizeY() * MINI_SCALE);
		miniPane.setMinSize(getSizeX() * MINI_SCALE, getSizeY() * MINI_SCALE);
		miniPane.setHgap(getPadH() * MINI_SCALE);
		miniPane.setVgap(getPadV() * MINI_SCALE);
		miniPane.setPadding(new Insets(pad, pad, pad, pad));

		for (Planet p : planets.values())
		{
			Label l = new Label();
			l.getStyleClass().addAll("space-button", p.getOwner().getColor());
			l.setMinSize(getTileH() * MINI_SCALE, getTileV() * MINI_SCALE);
			l.setMaxSize(getTileH() * MINI_SCALE, getTileV() * MINI_SCALE);

			miniPane.add(l, (int) p.getPosition().getX(), (int) p.getPosition().getY());
		}
	}

	public double getTileH()
	{
		return TILEH * zoom;
	}

	public double getTileV()
	{
		return TILEV * zoom;
	}

	public double getPadH()
	{
		return PADH * zoom;
	}

	public double getPadV()
	{
		return PADV * zoom;
	}

	public double getMargin()
	{
		return MARGIN;
	}

	public double getSizeX()
	{
		return ((TILEH + PADH) * x + MARGIN * 2 - PADH) * zoom;
	}

	public double getSizeY()
	{
		return ((TILEV + PADV) * y + MARGIN * 2 - PADV) * zoom;
	}

	public static void setMiniScale(double miniScale)
	{
		MINI_SCALE = miniScale;
	}

	public static double getMiniScale()
	{
		return MINI_SCALE;
	}

	public Map<Space, Label> getTiles()
	{
		return tiles;
	}

	public Pane getPane()
	{
		return pane;
	}
}
