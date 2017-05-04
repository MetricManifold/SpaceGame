package game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class PlanetManager
{
	// static values for ui
	private static final int PADH = 5, PADV = 5,
		TILEH = 25, TILEV = 25,
		NUM_P_BG = 4, NUM_B_BG = 4,
		MARGIN = 5, TOPMARGIN = 2;

	// planets and corresponding buttons
	private Map<Space, Label> tiles = new HashMap<Space, Label>();
	private Map<Integer, Planet> planets = new HashMap<Integer, Planet>();

	private int sizeh, sizev, defSizeh, defSizev, x, y, len;
	private double density;

	private StackPane pane = new StackPane();
	private TilePane tilePane = new TilePane(Orientation.HORIZONTAL);

	public PlanetManager()
	{
		// initialize local variables
		this.x = ConfigurationManager.gridX;
		this.y = ConfigurationManager.gridY;
		this.density = ConfigurationManager.planetDensity;

		this.defSizeh = (TILEH + PADH) * ConfigurationManager.defGridX + MARGIN * 2 - PADH;
		this.defSizev = (TILEH + PADH) * ConfigurationManager.defGridX + MARGIN * 2 - PADH;
		this.sizeh = (TILEH + PADH) * x + MARGIN * 2 - PADH;
		this.sizev = (TILEV + PADV) * y + MARGIN * 2 - PADV;
		this.len = x * y;

		makeTilePaneUI(); // 		setup tilepane UI
		spawnPlanets(); // 			create the planets on this grid

		System.out.println("finished grid");
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeTilePaneUI()
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
	public void setEvents(PlayerManager pm, TurnManager tm)
	{
		setStartPlanets(pm);

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
					handleClickPlanet(p, pm, tm);
				}
			}
		});
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	void spawnPlanets()
	{
		for (int i = 0; i < len; i++)
		{
			// create the necessary elements
			Label l = new Label();
			Space s = new Space(i % x, i / x);

			// modify button to correct size and action
			l.setMinSize(TILEH, TILEV);
			l.setMaxSize(TILEH, TILEV);

			tilePane.getChildren().add(l);

			// choose whether to create a planet or empty space
			double prob = ThreadLocalRandom.current().nextDouble();
			if (prob < density)
			{
				Planet p = new Planet(s);

				// set styles
				String bgSelect = String.format("planet-button%d", ThreadLocalRandom.current().nextInt(NUM_P_BG) + 1);
				l.getStyleClass().addAll("space-button", bgSelect);

				// add the planet to the grid and planets list
				tiles.put(p, l);
				planets.put(hashLocation(i % x, i / x), p);

				setPlanetTooltip(p);
			}
			else
			{
				l.getStyleClass().add("space-button");
				tiles.put(s, l);
			}
		}
	}

	/**
	 * give players one starting planet
	 * 
	 * @param pm
	 */
	void setStartPlanets(PlayerManager pm)
	{
		// make a list of planets to choose from
		int numPlayers = ConfigurationManager.numPlayers;
		Planet[] planetArray = getPlanetArray();

		// make a list denoting each of the planets
		List<Integer> nums = new ArrayList<>();
		IntStream.range(0, planets.size()).forEach(i -> nums.add(i));

		while (numPlayers-- > 0)
		{
			// pick a random value from the planet array
			int r = ThreadLocalRandom.current().nextInt(nums.size());
			int pick = nums.remove(r);
			Planet p = planetArray[pick];

			setPlanetOwner(pm.getPlayer(numPlayers), p);
			p.setProduction(10);
			p.produceShips();
		}

		nums.forEach(n -> setPlanetOwner(pm.neutral, planetArray[n]));
	}

	/**
	 * handles the ui change when a planet is clicked
	 * 
	 * @param p
	 * @param pm
	 * @param tm
	 */
	public void handleClickPlanet(Planet p, PlayerManager pm, TurnManager tm)
	{
		Planet o = pm.getCurrentPlayer().getOrigin();
		Planet d = pm.getCurrentPlayer().getDestination();
		pm.getCurrentPlayer().clickTile(p);

		if (p != null)
		{
			if (o == null)
			{
				if (p.getOwner() == pm.getCurrentPlayer())
				{
					tiles.get(p).getStyleClass().add("space-button-origin");
				}
			}
			else if (d == null)
			{
				if (p != o)
				{
					tiles.get(p).getStyleClass().add("space-button-destination");
					tm.enableSend(true);
				}
			}
			else
			{
				clearSelection(o, d);
				tm.enableSend(false);
			}
		}
		else
		{
			clearSelection(o, d);
			tm.enableSend(false);
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
	public void setPlanetOwner(Player player, Planet p)
	{
		// if owner is not null 
		if (p.getOwner() != null)
		{
			tiles.get(p).getStyleClass().remove(p.getOwner().getColor());
		}

		player.addPlanet(p);
		tiles.get(p).getStyleClass().add(player.getColor());
	}

	/**
	 * add the tooltip for the given planet
	 * 
	 * @param p
	 */
	public void setPlanetTooltip(Planet p)
	{
		Label l = tiles.get(p);
		
		l.setOnMouseEntered(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Point2D pnt = l.localToScreen(l.getLayoutBounds().getMaxX(), l.getLayoutBounds().getMaxY());
				p.getTooltip().show(l, pnt.getX() + 10, pnt.getY());
			}
		});
		l.setOnMouseExited(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				p.getTooltip().hide();
			}
		});
	}

	/**
	 * creates a unique integer associated with the provided 2d location
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	int hashLocation(int x, int y)
	{
		return x + this.x * y;
	}

	/**
	 * get the array of planets
	 * 
	 * @return
	 */
	public Planet[] getPlanetArray()
	{
		return planets.values().toArray(new Planet[planets.size()]);
	}

	/**
	 * return the width in tiles of the grid
	 * 
	 * @return
	 */
	public int getSizeX()
	{
		return sizeh;
	}

	/**
	 * return the height in tiles of the grid.
	 * 
	 * @return
	 */
	public int getSizeY()
	{
		return sizev;
	}
	
	public int getDefSizeX()
	{
		return defSizeh;
	}
	
	public int getDefSizeY()
	{
		return defSizev;
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
