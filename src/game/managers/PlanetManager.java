package game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import game.objects.Planet;
import game.objects.Space;
import game.players.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class PlanetManager
{
	// static values for ui
	public static final int PADH = 8, PADV = 8,
			TILEH = 20, TILEV = 20,
			NUM_P_BG = 4, NUM_B_BG = 4,
			MARGIN = 5, TOPMARGIN = 2;

	// planets and corresponding buttons
	private Map<Space, Label> tiles = new HashMap<Space, Label>();
	private Map<Integer, Planet> planets = new HashMap<Integer, Planet>();

	private int maxh, maxv, x, y, len;
	private double density;

	public TilePane tilePane = new TilePane(Orientation.HORIZONTAL);

	public PlanetManager()
	{
		// initialize local variables
		this.x = ConfigurationManager.gridX;
		this.y = ConfigurationManager.gridY;
		this.density = ConfigurationManager.planetDensity;

		this.maxh = (TILEH + PADH) * x + MARGIN * 2;
		this.maxv = (TILEV + PADV) * y + MARGIN * 2;

		this.len = x * y;

		makeTilePaneUI(); // 		setup tilepane UI
		makePlanets(); // 			create the planets on this grid

		System.out.println("finished grid");
	}

	/**
	 * Sets the properties related to tilepane UI
	 */
	void makeTilePaneUI()
	{
		VBox.setMargin(tilePane, new Insets(TOPMARGIN, 0, 0, 0));

		tilePane.setHgap(PADH);
		tilePane.setVgap(PADV);

		tilePane.setPrefTileWidth(TILEH);
		tilePane.setPrefTileHeight(TILEV);

		tilePane.setPrefColumns(x);
		tilePane.setPrefRows(y);

		tilePane.setMaxSize(maxh, maxv);
		tilePane.setPadding(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));
		tilePane.setAlignment(Pos.BASELINE_CENTER);

		String bgSelect = "planet-grid" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_B_BG) + 1);
		tilePane.getStyleClass().addAll(bgSelect, "planet-grid");

		System.out.println("created grid ui");
	}

	/**
	 * Sets the mouse event associated with the tilepane to find all the grid locations
	 */
	public void setEvents(PlayerManager pm, TurnManager tm)
	{
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
				if (dx < PADH / (TILEH / PADH) && dy < PADV / (TILEV / PADV))
				{
					Planet p = planets.get(hashLocation(ix, iy));
					Planet o = pm.getCurrentPlayer().getOrigin();
					Planet d = pm.getCurrentPlayer().getDestination();

					pm.getCurrentPlayer().clickTile(p);

					if (p != null)
					{
						if (o == null)
						{
							tiles.get(p).getStyleClass().add("space-button-origin");
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
							tiles.get(p).getStyleClass().add("space-button-origin");
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
			}
		});

		makeStartPlanets(pm);

		System.out.println("set events");
	}

	/**
	 * Clears the current selection from the grid
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

	public void givePlanet(Player player, Planet p)
	{
		player.givePlanet(p);
		p.setOwner(player);
		tiles.get(p).getStyleClass().add(player.getColor());
	}

	/**
	 * puts planets in the grid and associates them with buttons
	 */
	void makePlanets()
	{
		for (int i = 0; i < len; i++)
		{
			// create the necessary elements
			Label l = new Label();
			Space s = new Space(i % x, i / y);

			// modify button to correct size and action
			l.setMinSize(TILEH, TILEV);
			l.setMaxSize(TILEH, TILEV);

			tilePane.getChildren().add(l);

			// choose whether to create a planet or empty space
			double prob = ThreadLocalRandom.current().nextDouble();
			if (prob < density)
			{
				Planet p = new Planet(s);
				String bgSelect = "planet-button" + String.valueOf(ThreadLocalRandom.current().nextInt(NUM_P_BG) + 1);
				l.getStyleClass().addAll(bgSelect, "space-button");
				
				l.setOnMouseEntered(new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event)
					{
						Point2D pnt = l.localToScreen(l.getLayoutBounds().getMaxX(), l.getLayoutBounds().getMaxY());
						p.getTooltip().show(l, pnt.getX(), pnt.getY());
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

				tiles.put(p, l);
				planets.put(hashLocation(i % x, i / y), p);
			}
			else
			{
				l.getStyleClass().add("space-button");
				tiles.put(s, l);
			}
		}

		System.out.println("placed planets in grid");
	}

	void makeStartPlanets(PlayerManager pm)
	{
		// make a list of planets to choose from
		int numPlayers = ConfigurationManager.numPlayers;
		Planet[] planetArray = getPlanetArray();

		// make a list denoting each of the planets
		List<Integer> nums = new ArrayList<Integer>();
		for (Integer i : IntStream.range(0, planets.size()).toArray())
			nums.add(i);

		while (numPlayers-- > 0)
		{
			// pick a random value from the planet array
			int r = ThreadLocalRandom.current().nextInt(nums.size());
			int pick = nums.get(r);

			givePlanet(pm.getPlayer(numPlayers), planetArray[pick]);
			nums.remove(r);
		}

		for (int n : nums)
		{
			givePlanet(pm.neutral, planetArray[n]);
		}
	}

	/**
	 * Creates a unique integer associated with the provided 2d location.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	int hashLocation(int x, int y)
	{
		return x + this.x * y;
	}

	public Planet[] getPlanetArray()
	{
		return planets.values().toArray(new Planet[planets.size()]);
	}

	public int getSizeX()
	{
		return maxh;
	}

	public int getSizeY()
	{
		return maxv;
	}

}
