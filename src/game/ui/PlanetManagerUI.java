package game.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import game.managers.*;
import game.groups.Fleet;
import game.helpers.Displacement;
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

public class PlanetManagerUI extends PlanetManager
{
	// static values for ui
	protected static final int PADH = 5, PADV = 5,
		TILEH = 25, TILEV = 25,
		NUM_P_BG = 10, NUM_B_BG = 4,
		MARGIN = 5, TOPMARGIN = 2;

	// planets and corresponding buttons
	protected Map<Space, Label> tiles;
	protected Map<Fleet, Line> lines;

	protected Pane pane;
	protected TilePane tilePane;
	protected TurnManagerUI TMui;

	public PlanetManagerUI(ConfigManager cm)
	{
		super(cm);

		lines = new HashMap<Fleet, Line>();
		tiles = new HashMap<Space, Label>();
		pane = new Pane();
		tilePane = new TilePane(Orientation.HORIZONTAL);

		this.sizeh = (TILEH + PADH) * x + MARGIN * 2 - PADH;
		this.sizev = (TILEV + PADV) * y + MARGIN * 2 - PADV;

		makeUI();
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeUI()
	{
		VBox.setMargin(tilePane, new Insets(TOPMARGIN, 0, 0, 0));
		pane.getChildren().add(tilePane);

		tilePane.setHgap(PADH);
		tilePane.setVgap(PADV);

		tilePane.setPrefTileWidth(TILEH);
		tilePane.setPrefTileHeight(TILEV);

		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);

		tilePane.setMaxSize(sizeh, sizev);
		tilePane.setPadding(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));
		tilePane.setAlignment(Pos.BASELINE_CENTER);

		String bgSelect = String.format("planet-grid%d", ThreadLocalRandom.current().nextInt(NUM_B_BG) + 1);
		tilePane.getStyleClass().addAll("planet-grid", bgSelect);
	}

	/**
	 * Sets the mouse event associated with the tilepane to find all the grid locations
	 */
	public void setup(PlayerManager pm, TurnManagerUI tm)
	{
		super.setup(pm, tm);
		TMui = tm;

		tilePane.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				double sx = (event.getX() - MARGIN) / (TILEH + PADH); // 	normalized selected x
				double sy = (event.getY() - MARGIN) / (TILEV + PADV); // 	normalized selected y
				int ix = (int) sx; //										integer distance x
				int iy = (int) sy; //										integer distance y
				double dx = sx - ix; //										fractional distance x
				double dy = sy - iy; // 									fractional distance y

				// check if selection is before padding
				if (dx < (TILEH + PADH) / PADH && dy < (TILEV + PADV) / PADV)
				{
					Planet p = planets.get(hashLocation(ix, iy));
					handleClickPlanet(p);
				}
			}
		});
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	@Override
	public void spawnPlanets()
	{
		super.spawnPlanets();

		for (int i = 0; i < len; i++)
		{
			Label l = new Label();
			int hash = hashLocation(i % x, i / x);

			l.setMinSize(TILEH, TILEV);
			l.setMaxSize(TILEH, TILEV);
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
				Point2D pnt = l.localToScreen(l.getLayoutBounds().getMaxX(), l.getLayoutBounds().getMaxY());
				p.getTooltip().show(l, pnt.getX() + 10, pnt.getY());
				addFleetPaths(p);
			}
		});

		l.setOnMouseExited(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				p.getTooltip().hide();
				removeFleetPaths(p);
			}
		});
	}

	/**
	 * shows lines for active fleets
	 * 
	 * @param f
	 * @param o
	 * @param d
	 */
	public void addFleetPaths(Planet p)
	{
		for (Fleet f : TM.getFleets())
		{
			if (f.getDestination() == p && !lines.containsKey(f))
			{
				Displacement d = p.getPosition();
				Displacement e = f.getDisplacement();
				Line l = new Line(d.getWorldX(), d.getWorldY(), d.getWorldX() - e.getWorldX(), d.getWorldY() - e.getWorldY());
				l.setStrokeWidth(2);
				l.setStroke(Color.YELLOW);
				l.setOpacity(0.8);
				l.getStrokeDashArray().addAll(1d, 5d);

				System.out.printf("%f, %f\n", p.getPosition().getX(), p.getPosition().getY());
				System.out.printf("%f, %f\n", p.getPosition().getWorldX(), p.getPosition().getWorldY());

				lines.put(f, l);
				pane.getChildren().add(l);
			}
		}
	}

	public void removeFleetPaths(Planet p)
	{
		for (Fleet f : TM.getFleets())
		{
			if (f.getDestination() == p)
			{
				pane.getChildren().remove(lines.get(f));
				lines.remove(f);
			}
		}
	}

	public Pane getMiniPane()
	{
		GridPane miniTilePane = new GridPane();
		miniTilePane.setStyle("-fx-background-color: black;");
		
		for (Planet p : planets.values())
		{
			Label l = new Label();
			l.getStyleClass().add(tiles.get(p).getStyle());
			miniTilePane.add(l, (int) p.getPosition().getX(), (int) p.getPosition().getY());
		}

		return miniTilePane;
	}

	public static int getTileH()
	{
		return TILEH;
	}

	public static int getTileV()
	{
		return TILEV;
	}

	public static int getPadH()
	{
		return PADH;
	}

	public static int getPadV()
	{
		return PADV;
	}

	public static int getMargin()
	{
		return MARGIN;
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
