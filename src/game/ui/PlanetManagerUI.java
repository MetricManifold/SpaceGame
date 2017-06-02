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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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

	protected double zoom = 1.00;

	// planets and corresponding buttons
	protected Map<Space, Label> tiles;
	protected Map<Fleet, Path> paths;

	protected Pane pane, bgPane;
	protected TilePane tilePane, miniPane;
	protected TurnManagerUI TMui;

	public PlanetManagerUI(ConfigManager CM)
	{
		super(CM);

		bgPane = new Pane();
		pane = new Pane();
		tilePane = new TilePane(Orientation.HORIZONTAL);

		makeUI();
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeUI()
	{
		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);
		tilePane.setMaxSize(getSizeX(), getSizeY());
		bgPane.setPrefSize(getSizeX(), getSizeY());
		pane.setMaxWidth(getSizeX());

		VBox.setMargin(tilePane, new Insets(TOPMARGIN, 0, 0, 0));
		pane.getChildren().addAll(bgPane, tilePane);
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
		redrawGrid();

		tilePane.setOnMouseClicked(e -> {
			double sx = (e.getX() - getMargin()) / (getTileH() + getPadH()); // 	normalized selected x
			double sy = (e.getY() - getMargin()) / (getTileV() + getPadV()); // 	normalized selected y
			int ix = (int) sx; //		integer distance x
			int iy = (int) sy; //		integer distance y
			double dx = sx - ix; //		fractional distance x
			double dy = sy - iy; // 	fractional distance y

			// check if selection is before padding
			if (dx < (getTileH() + getPadH()) / getPadH() && dy < (getTileV() + getPadV()) / getPadV())
			{
				Planet p = planets.get(hashLocation(iy * x + ix));
				handleClickPlanet(p);
			}
		});
		tilePane.setOnMouseDragged(e -> handleDrag(e));
		tilePane.setOnScroll(e -> handleZoom(e.getDeltaY()));
		WindowManager.getScene().setOnKeyReleased(e -> handleZoom(e.getCode()));
	}

	@Override
	public void reset()
	{
		super.reset();
		redrawGrid();
	}

	@Override
	protected void loadConfiguration()
	{
		super.loadConfiguration();

		paths = new HashMap<Fleet, Path>();
		tiles = new HashMap<Space, Label>();
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
			int hash = hashLocation(i);
			tilePane.getChildren().add(l);
			Planet p = planets.get(hash);

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

	public void redrawGrid()
	{
		tilePane.setPadding(new Insets(getMargin(), getMargin(), getMargin(), getMargin()));
		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);
		tilePane.setMaxSize(getSizeX(), getSizeY());
		tilePane.setMinSize(getSizeX(), getSizeY());
		tilePane.setHgap(getPadH());
		tilePane.setVgap(getPadV());

		bgPane.setPrefSize(getSizeX(), getSizeY());
		pane.setMaxWidth(getSizeX());

		for (Planet p : planets.values())
		{
			setPlanetOwner(p.getOwner(), p);
		}
		
		for (Label l : tiles.values())
		{
			l.setMinSize(getTileH(), getTileV());
			l.setMaxSize(getTileH(), getTileV());
		}
		
		updateMiniPane();
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
	 * @param p
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
		redrawGrid();
	}

	public double getZoom()
	{
		return zoom;
	}

	public void handleZoom(KeyCode k)
	{
		if (k == KeyCode.MINUS && zoom > 0.5)
		{
			zoom -= 0.1;
			redrawGrid();
		}
		else if (k == KeyCode.EQUALS && zoom < 1.5)
		{
			zoom += 0.1;
			redrawGrid();
		}
	}

	public void handleZoom(Double delta)
	{
		if (delta < 0 && zoom > 0.5)
		{
			zoom -= 0.1;
			redrawGrid();
		}
		else if (delta > 0 && zoom < 1.5)
		{
			zoom += 0.1;
			redrawGrid();
		}
	}

	public void handleDrag(MouseEvent e)
	{

	}

	public Pane getMiniPane()
	{
		if (miniPane == null)
		{
			miniPane = new TilePane();
			miniPane.getStyleClass().add("mini-grid");
			updateMiniPane();

		}

		return miniPane;
	}

	private void updateMiniPane()
	{
		if (miniPane == null)
		{
			getMiniPane();
		}

		miniPane.getChildren().clear();

		double pad = getMargin();
		miniPane.setMaxSize(getSizeX(), getSizeY());
		miniPane.setMinSize(getSizeX(), getSizeY());
		miniPane.setHgap(getPadH());
		miniPane.setVgap(getPadV());
		miniPane.setPrefColumns(x);
		miniPane.setPrefRows(y);
		miniPane.setPadding(new Insets(pad, pad, pad, pad));

		for (int i = 0; i < len; i++)
		{
			Label l = new Label();
			l.setMinSize(getTileH(), getTileV());
			l.setMaxSize(getTileH(), getTileV());

			int hash = hashLocation(i);
			miniPane.getChildren().add(l);
			Planet p = planets.get(hash);

			if (p != null)
			{
				l.getStyleClass().addAll("space-button", p.getOwner().getColor());
			}
			else
			{
				l.getStyleClass().add("space-button");
			}
		}
	}

	public int getTileH()
	{
		return (int) (TILEH * zoom);
	}

	public int getTileV()
	{
		return (int) (TILEV * zoom);
	}

	public int getPadH()
	{
		return (int) (PADH * zoom);
	}

	public int getPadV()
	{
		return (int) (PADV * zoom);
	}

	public int getMargin()
	{
		return (int) (MARGIN * zoom);
	}

	public int getSizeX()
	{
		return (getTileH() + getPadH()) * x + getMargin() * 2 - getPadH();
	}

	public int getSizeY()
	{
		return (getTileV() + getPadV()) * y + getMargin() * 2 - getPadV();
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
